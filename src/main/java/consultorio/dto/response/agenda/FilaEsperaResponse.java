package consultorio.dto.response.agenda;

import consultorio.domain.agenda.FilaEspera;
import consultorio.domain.agenda.TipoProcedimento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilaEsperaResponse {

    private Long id;
    private Long pacienteId;
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
    private Integer prioridade;
    private Boolean aceitaQualquerHorario;
    private Boolean aceitaQualquerDentista;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
    private String criadoPor;
    private Long agendamentoId;
    private LocalDateTime convertidoEm;
    private Boolean notificado;
    private LocalDateTime notificadoEm;
    private Integer tentativasContato;
    private LocalDateTime ultimaTentativaContato;
    private Boolean ativa;
    private Boolean expirado;
}