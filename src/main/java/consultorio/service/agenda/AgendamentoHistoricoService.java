package consultorio.service.agenda;

import consultorio.domain.agenda.AgendamentoHistorico;
import consultorio.dto.request.agenda.AgendamentoHistoricoRequest;
import consultorio.dto.response.agenda.AgendamentoHistoricoResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface AgendamentoHistoricoService {

    AgendamentoHistoricoResponse criar(AgendamentoHistoricoRequest request);

    AgendamentoHistoricoResponse buscarPorId(Long id);

    List<AgendamentoHistoricoResponse> listarTodos();

    List<AgendamentoHistoricoResponse> listarPorAgendamento(Long agendamentoId);

    List<AgendamentoHistoricoResponse> listarPorAcao(AgendamentoHistorico.TipoAcao acao);

    List<AgendamentoHistoricoResponse> listarPorUsuario(String usuario);

    List<AgendamentoHistoricoResponse> listarPorPeriodo(LocalDateTime inicio, LocalDateTime fim);

    List<AgendamentoHistoricoResponse> listarPorAgendamentoEPeriodo(Long agendamentoId, LocalDateTime inicio, LocalDateTime fim);

    List<AgendamentoHistoricoResponse> listarAcoesRecentes(int limit);

    Long contarPorAcao(AgendamentoHistorico.TipoAcao acao);

    Long contarPorAgendamento(Long agendamentoId);

    void registrarCriacao(Long agendamentoId, String usuario, String descricao);

    void registrarMudancaStatus(Long agendamentoId, consultorio.domain.agenda.StatusAgendamento statusAnterior,
                                consultorio.domain.agenda.StatusAgendamento statusNovo, String usuario, String descricao);

    void registrarLembreteEnviado(Long agendamentoId, String usuario);
}