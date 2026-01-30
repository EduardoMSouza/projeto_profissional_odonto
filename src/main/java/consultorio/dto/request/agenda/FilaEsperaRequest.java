package consultorio.dto.request.agenda;

import consultorio.domain.agenda.FilaEspera;
import consultorio.domain.agenda.TipoProcedimento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilaEsperaRequest {

    @NotNull(message = "ID do paciente é obrigatório")
    @Positive(message = "ID do paciente deve ser positivo")
    private Long pacienteId;

    @NotBlank(message = "Nome do paciente é obrigatório")
    private String pacienteNome;

    private Long dentistaId;

    private String dentistaNome;

    private TipoProcedimento tipoProcedimento;

    private LocalDate dataPreferencial;

    private LocalTime horaInicioPreferencial;

    private LocalTime horaFimPreferencial;

    private FilaEspera.PeriodoPreferencial periodoPreferencial;

    private FilaEspera.StatusFila status;

    private String observacoes;

    @PositiveOrZero(message = "Prioridade deve ser positiva ou zero")
    private Integer prioridade;

    private Boolean aceitaQualquerHorario;

    private Boolean aceitaQualquerDentista;

    private String criadoPor;
}