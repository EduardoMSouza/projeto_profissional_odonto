package consultorio.dto.response.agenda;

import consultorio.domain.agenda.StatusAgendamento;
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
public class AgendamentoResponse {

    private Long id;
    private Long pacienteId;
    private String pacienteNome;
    private Long dentistaId;
    private String dentistaNome;
    private LocalDate dataConsulta;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private StatusAgendamento status;
    private TipoProcedimento tipoProcedimento;
    private String observacoes;
    private Boolean ativo;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
    private String criadoPor;
    private String atualizadoPor;
    private String canceladoPor;
    private LocalDateTime canceladoEm;
    private String motivoCancelamento;
    private LocalDateTime confirmadoEm;
    private Boolean lembreteEnviado;
    private LocalDateTime lembreteEnviadoEm;
    private Long duracaoMinutos;
    private Boolean consultaPassada;
    private Boolean consultaHoje;
}