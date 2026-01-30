package consultorio.dto.agendamento;

import consultorio.domain.agenda.StatusAgendamento;
import consultorio.domain.agenda.TipoProcedimento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgendamentoRequest {

    @NotNull(message = "ID do paciente é obrigatório")
    @Positive(message = "ID do paciente deve ser positivo")
    private Long pacienteId;

    @NotBlank(message = "Nome do paciente é obrigatório")
    private String pacienteNome;

    @NotNull(message = "ID do dentista é obrigatório")
    @Positive(message = "ID do dentista deve ser positivo")
    private Long dentistaId;

    @NotBlank(message = "Nome do dentista é obrigatório")
    private String dentistaNome;

    @NotNull(message = "Data da consulta é obrigatória")
    private LocalDate dataConsulta;

    @NotNull(message = "Hora de início é obrigatória")
    private LocalTime horaInicio;

    @NotNull(message = "Hora de fim é obrigatória")
    private LocalTime horaFim;

    private StatusAgendamento status;

    private TipoProcedimento tipoProcedimento;

    private String observacoes;

    private Boolean ativo;

    private String criadoPor;
}