package consultorio.service.impl.agenda;

import consultorio.domain.agenda.Agendamento;
import consultorio.domain.agenda.StatusAgendamento;
import consultorio.domain.agenda.TipoProcedimento;
import consultorio.dto.agendamento.AgendamentoRequest;

import consultorio.dto.response.agenda.AgendamentoResponse;
import consultorio.mapper.agenda.AgendamentoMapper;
import consultorio.repository.agenda.AgendamentoRepository;
import consultorio.service.agenda.AgendamentoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgendamentoServiceImpl implements AgendamentoService {

    private final AgendamentoRepository repository;
    private final AgendamentoMapper mapper;

    public AgendamentoServiceImpl(AgendamentoRepository repository, AgendamentoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public AgendamentoResponse criar(AgendamentoRequest request) {
        // Validação de horário
        if (request.getHoraFim().isBefore(request.getHoraInicio()) || request.getHoraFim().equals(request.getHoraInicio())) {
            throw new IllegalArgumentException("Hora de fim deve ser posterior à hora de início");
        }

        // Verifica conflitos de horário
        List<Agendamento> conflitos = repository.findConflitos(
                request.getDentistaId(),
                request.getDataConsulta(),
                request.getHoraInicio(),
                request.getHoraFim()
        );

        if (!conflitos.isEmpty()) {
            throw new IllegalArgumentException("Já existe agendamento neste horário para este dentista");
        }

        Agendamento agendamento = mapper.toEntity(request);
        agendamento = repository.save(agendamento);
        return mapper.toResponse(agendamento);
    }

    @Override
    @Transactional
    public AgendamentoResponse atualizar(Long id, AgendamentoRequest request) {
        Agendamento agendamento = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado com ID: " + id));

        if (!agendamento.isPodeSerEditado()) {
            throw new IllegalArgumentException("Agendamento não pode ser editado no status atual");
        }

        // Validação de horário
        if (request.getHoraFim().isBefore(request.getHoraInicio()) || request.getHoraFim().equals(request.getHoraInicio())) {
            throw new IllegalArgumentException("Hora de fim deve ser posterior à hora de início");
        }

        // Verifica conflitos (exceto o próprio agendamento)
        List<Agendamento> conflitos = repository.findConflitos(
                        request.getDentistaId(),
                        request.getDataConsulta(),
                        request.getHoraInicio(),
                        request.getHoraFim()
                ).stream()
                .filter(a -> !a.getId().equals(id))
                .collect(Collectors.toList());

        if (!conflitos.isEmpty()) {
            throw new IllegalArgumentException("Já existe agendamento neste horário para este dentista");
        }

        mapper.updateEntity(agendamento, request);
        agendamento = repository.save(agendamento);
        return mapper.toResponse(agendamento);
    }

    @Override
    @Transactional(readOnly = true)
    public AgendamentoResponse buscarPorId(Long id) {
        Agendamento agendamento = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado com ID: " + id));
        return mapper.toResponse(agendamento);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgendamentoResponse> listarTodos() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgendamentoResponse> listarPorDentistaEData(Long dentistaId, LocalDate data) {
        return repository.findByDentistaIdAndDataConsulta(dentistaId, data).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgendamentoResponse> listarPorPacienteEData(Long pacienteId, LocalDate data) {
        return repository.findByPacienteIdAndDataConsulta(pacienteId, data).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgendamentoResponse> listarPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        return repository.findByPeriodo(dataInicio, dataFim).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgendamentoResponse> listarPorDentistaEPeriodo(Long dentistaId, LocalDate dataInicio, LocalDate dataFim) {
        return repository.findByDentistaAndPeriodo(dentistaId, dataInicio, dataFim).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgendamentoResponse> listarPorPacienteEPeriodo(Long pacienteId, LocalDate dataInicio, LocalDate dataFim) {
        return repository.findByPacienteAndPeriodo(pacienteId, dataInicio, dataFim).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgendamentoResponse> listarPorStatus(StatusAgendamento status) {
        return repository.findByStatus(status).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgendamentoResponse> listarPorDataEStatus(LocalDate data, StatusAgendamento status) {
        return repository.findByDataAndStatus(data, status).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgendamentoResponse> listarPorTipoProcedimento(TipoProcedimento tipo) {
        return repository.findByTipoProcedimento(tipo).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgendamentoResponse> listarPendentesLembrete(LocalDate data) {
        return repository.findPendentesLembrete(data).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgendamentoResponse> listarConsultasDoDia(LocalDate data) {
        return repository.findConsultasDoDia(data).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgendamentoResponse> buscarPorPacienteNome(String nome) {
        return repository.findByPacienteNomeContaining(nome).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgendamentoResponse> buscarPorDentistaNome(String nome) {
        return repository.findByDentistaNomeContaining(nome).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgendamentoResponse> listarProximosAgendamentosPaciente(Long pacienteId) {
        return repository.findProximosAgendamentosPaciente(pacienteId, LocalDate.now()).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgendamentoResponse> listarHistoricoAgendamentosPaciente(Long pacienteId) {
        return repository.findHistoricoAgendamentosPaciente(pacienteId, LocalDate.now()).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean verificarConflito(Long dentistaId, LocalDate data, Long agendamentoId) {
        Agendamento agendamento = repository.findById(agendamentoId)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado com ID: " + agendamentoId));

        List<Agendamento> conflitos = repository.findConflitos(
                        dentistaId,
                        data,
                        agendamento.getHoraInicio(),
                        agendamento.getHoraFim()
                ).stream()
                .filter(a -> !a.getId().equals(agendamentoId))
                .collect(Collectors.toList());

        return !conflitos.isEmpty();
    }

    @Override
    @Transactional
    public AgendamentoResponse confirmar(Long id, String usuario) {
        Agendamento agendamento = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado com ID: " + id));

        agendamento.confirmar(usuario);
        agendamento = repository.save(agendamento);
        return mapper.toResponse(agendamento);
    }

    @Override
    @Transactional
    public AgendamentoResponse iniciarAtendimento(Long id, String usuario) {
        Agendamento agendamento = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado com ID: " + id));

        agendamento.iniciarAtendimento(usuario);
        agendamento = repository.save(agendamento);
        return mapper.toResponse(agendamento);
    }

    @Override
    @Transactional
    public AgendamentoResponse concluir(Long id, String usuario) {
        Agendamento agendamento = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado com ID: " + id));

        agendamento.concluir(usuario);
        agendamento = repository.save(agendamento);
        return mapper.toResponse(agendamento);
    }

    @Override
    @Transactional
    public AgendamentoResponse cancelar(Long id, String motivo, String usuario) {
        Agendamento agendamento = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado com ID: " + id));

        if (!agendamento.isPodeSerCancelado()) {
            throw new IllegalArgumentException("Agendamento não pode ser cancelado no status atual");
        }

        agendamento.cancelar(motivo, usuario);
        agendamento = repository.save(agendamento);
        return mapper.toResponse(agendamento);
    }

    @Override
    @Transactional
    public AgendamentoResponse marcarFalta(Long id, String usuario) {
        Agendamento agendamento = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado com ID: " + id));

        agendamento.marcarFalta(usuario);
        agendamento = repository.save(agendamento);
        return mapper.toResponse(agendamento);
    }

    @Override
    @Transactional
    public AgendamentoResponse enviarLembrete(Long id) {
        Agendamento agendamento = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado com ID: " + id));

        agendamento.marcarLembreteEnviado();
        agendamento = repository.save(agendamento);
        return mapper.toResponse(agendamento);
    }

    @Override
    @Transactional(readOnly = true)
    public Long contarAgendamentosDia(Long dentistaId, LocalDate data) {
        return repository.countAgendamentosDia(dentistaId, data);
    }

    @Override
    @Transactional(readOnly = true)
    public Long contarPorPaciente(Long pacienteId) {
        return repository.countByPacienteId(pacienteId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long contarPorStatus(StatusAgendamento status) {
        return repository.countByStatus(status);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        Agendamento agendamento = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado com ID: " + id));

        agendamento.desativar();
        repository.save(agendamento);
    }
}