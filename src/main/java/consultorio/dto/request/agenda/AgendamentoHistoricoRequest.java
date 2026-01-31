package consultorio.dto.request.agenda;

import consultorio.domain.agenda.AgendamentoHistorico;
import consultorio.domain.agenda.StatusAgendamento;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgendamentoHistoricoRequest {

    @NotNull(message = "ID do agendamento é obrigatório")
    private Long agendamentoId;

    @NotNull(message = "Tipo de ação é obrigatório")
    private AgendamentoHistorico.TipoAcao acao;

    private StatusAgendamento statusAnterior;

    private StatusAgendamento statusNovo;

    private String usuarioResponsavel;

    private String descricao;

    private String detalhes;

    private String ipOrigem;
}