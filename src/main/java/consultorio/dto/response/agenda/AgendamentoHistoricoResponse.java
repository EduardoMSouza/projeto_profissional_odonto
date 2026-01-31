package consultorio.dto.response.agenda;

import consultorio.domain.agenda.AgendamentoHistorico;
import consultorio.domain.agenda.StatusAgendamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgendamentoHistoricoResponse {

    private Long id;
    private Long agendamentoId;
    private AgendamentoHistorico.TipoAcao acao;
    private StatusAgendamento statusAnterior;
    private StatusAgendamento statusNovo;
    private String usuarioResponsavel;
    private String descricao;
    private String detalhes;
    private LocalDateTime dataHora;
    private String ipOrigem;
}