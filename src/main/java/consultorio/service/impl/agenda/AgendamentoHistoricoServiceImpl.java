package consultorio.service.impl.agenda;

import consultorio.domain.agenda.AgendamentoHistorico;
import consultorio.domain.agenda.StatusAgendamento;
import consultorio.dto.request.agenda.AgendamentoHistoricoRequest;
import consultorio.dto.response.agenda.AgendamentoHistoricoResponse;
import consultorio.mapper.agenda.AgendamentoHistoricoMapper;
import consultorio.repository.agenda.AgendamentoHistoricoRepository;
import consultorio.service.agenda.AgendamentoHistoricoService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgendamentoHistoricoServiceImpl implements AgendamentoHistoricoService {

    private final AgendamentoHistoricoRepository repository;
    private final AgendamentoHistoricoMapper mapper;

    public AgendamentoHistoricoServiceImpl(AgendamentoHistoricoRepository repository,
                                           AgendamentoHistoricoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public AgendamentoHistoricoResponse criar(AgendamentoHistoricoRequest request) {
        AgendamentoHistorico historico = mapper.toEntity(request);
        historico = repository.save(historico);
        return mapper.toResponse(historico);
    }

    @Override
    @Transactional(readOnly = true)
    public AgendamentoHistoricoResponse buscarPorId(Long id) {
        AgendamentoHistorico historico = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Histórico não encontrado com ID: " + id));
        return mapper.toResponse(historico);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgendamentoHistoricoResponse> listarTodos() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgendamentoHistoricoResponse> listarPorAgendamento(Long agendamentoId) {
        return repository.findByAgendamentoIdOrderByDataHoraDesc(agendamentoId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgendamentoHistoricoResponse> listarPorAcao(AgendamentoHistorico.TipoAcao acao) {
        return repository.findByAcao(acao).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgendamentoHistoricoResponse> listarPorUsuario(String usuario) {
        return repository.findByUsuarioResponsavel(usuario).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgendamentoHistoricoResponse> listarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return repository.findByPeriodo(inicio, fim).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgendamentoHistoricoResponse> listarPorAgendamentoEPeriodo(Long agendamentoId,
                                                                           LocalDateTime inicio,
                                                                           LocalDateTime fim) {
        return repository.findByAgendamentoAndPeriodo(agendamentoId, inicio, fim).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgendamentoHistoricoResponse> listarAcoesRecentes(int limit) {
        return repository.findRecentActions().stream()
                .limit(limit)
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Long contarPorAcao(AgendamentoHistorico.TipoAcao acao) {
        return repository.countByAcao(acao);
    }

    @Override
    @Transactional(readOnly = true)
    public Long contarPorAgendamento(Long agendamentoId) {
        return repository.countByAgendamentoId(agendamentoId);
    }

    @Override
    @Transactional
    public void registrarCriacao(Long agendamentoId, String usuario, String descricao) {
        AgendamentoHistorico historico = AgendamentoHistorico.criar(
                agendamentoId,
                AgendamentoHistorico.TipoAcao.CRIACAO,
                usuario,
                descricao
        );
        repository.save(historico);
    }

    @Override
    @Transactional
    public void registrarMudancaStatus(Long agendamentoId,
                                       StatusAgendamento statusAnterior,
                                       StatusAgendamento statusNovo,
                                       String usuario,
                                       String descricao) {
        AgendamentoHistorico historico = AgendamentoHistorico.criarMudancaStatus(
                agendamentoId,
                statusAnterior,
                statusNovo,
                usuario,
                descricao
        );
        repository.save(historico);
    }

    @Override
    @Transactional
    public void registrarLembreteEnviado(Long agendamentoId, String usuario) {
        AgendamentoHistorico historico = AgendamentoHistorico.criar(
                agendamentoId,
                AgendamentoHistorico.TipoAcao.LEMBRETE_ENVIADO,
                usuario,
                "Lembrete enviado ao paciente"
        );
        repository.save(historico);
    }
}