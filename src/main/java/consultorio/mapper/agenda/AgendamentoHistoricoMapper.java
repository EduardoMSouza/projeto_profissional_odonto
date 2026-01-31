package consultorio.mapper.agenda;

import consultorio.domain.agenda.AgendamentoHistorico;
import consultorio.dto.request.agenda.AgendamentoHistoricoRequest;
import consultorio.dto.response.agenda.AgendamentoHistoricoResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AgendamentoHistoricoMapper {

    public AgendamentoHistorico toEntity(AgendamentoHistoricoRequest request) {
        AgendamentoHistorico historico = new AgendamentoHistorico();
        historico.setAgendamentoId(request.getAgendamentoId());
        historico.setAcao(request.getAcao());
        historico.setStatusAnterior(request.getStatusAnterior());
        historico.setStatusNovo(request.getStatusNovo());
        historico.setUsuarioResponsavel(request.getUsuarioResponsavel());
        historico.setDescricao(request.getDescricao());
        historico.setDetalhes(request.getDetalhes());
        historico.setIpOrigem(request.getIpOrigem());
        historico.setDataHora(LocalDateTime.now());
        return historico;
    }

    public AgendamentoHistoricoResponse toResponse(AgendamentoHistorico historico) {
        return AgendamentoHistoricoResponse.builder()
                .id(historico.getId())
                .agendamentoId(historico.getAgendamentoId())
                .acao(historico.getAcao())
                .statusAnterior(historico.getStatusAnterior())
                .statusNovo(historico.getStatusNovo())
                .usuarioResponsavel(historico.getUsuarioResponsavel())
                .descricao(historico.getDescricao())
                .detalhes(historico.getDetalhes())
                .dataHora(historico.getDataHora())
                .ipOrigem(historico.getIpOrigem())
                .build();
    }
}