package consultorio.service.impl.agenda;

import consultorio.domain.agenda.FilaEspera;
import consultorio.domain.agenda.TipoProcedimento;

import consultorio.dto.request.agenda.FilaEsperaRequest;
import consultorio.dto.response.agenda.FilaEsperaResponse;
import consultorio.mapper.agenda.FilaEsperaMapper;
import consultorio.repository.agenda.FilaEsperaRepository;
import consultorio.service.FilaEsperaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilaEsperaServiceImpl implements FilaEsperaService {

    private final FilaEsperaRepository repository;
    private final FilaEsperaMapper mapper;

    public FilaEsperaServiceImpl(FilaEsperaRepository repository, FilaEsperaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public FilaEsperaResponse criar(FilaEsperaRequest request) {
        // Validações
        if (request.getHoraInicioPreferencial() != null && request.getHoraFimPreferencial() != null) {
            if (request.getHoraFimPreferencial().isBefore(request.getHoraInicioPreferencial()) ||
                    request.getHoraFimPreferencial().equals(request.getHoraInicioPreferencial())) {
                throw new IllegalArgumentException("Hora fim deve ser posterior à hora início");
            }
        }

        FilaEspera fila = mapper.toEntity(request);
        fila = repository.save(fila);
        return mapper.toResponse(fila);
    }

    @Override
    @Transactional
    public FilaEsperaResponse atualizar(Long id, FilaEsperaRequest request) {
        FilaEspera fila = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fila de espera não encontrada com ID: " + id));

        // Validações
        if (request.getHoraInicioPreferencial() != null && request.getHoraFimPreferencial() != null) {
            if (request.getHoraFimPreferencial().isBefore(request.getHoraInicioPreferencial()) ||
                    request.getHoraFimPreferencial().equals(request.getHoraInicioPreferencial())) {
                throw new IllegalArgumentException("Hora fim deve ser posterior à hora início");
            }
        }

        mapper.updateEntity(fila, request);
        fila = repository.save(fila);
        return mapper.toResponse(fila);
    }

    @Override
    @Transactional(readOnly = true)
    public FilaEsperaResponse buscarPorId(Long id) {
        FilaEspera fila = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fila de espera não encontrada com ID: " + id));
        return mapper.toResponse(fila);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FilaEsperaResponse> listarTodos() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FilaEsperaResponse> listarPorStatus(FilaEspera.StatusFila status) {
        return repository.findByStatus(status).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FilaEsperaResponse> listarFilasAtivas() {
        return repository.findFilasAtivas().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FilaEsperaResponse> listarPorPaciente(Long pacienteId) {
        return repository.findByPacienteId(pacienteId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FilaEsperaResponse> listarPorDentista(Long dentistaId) {
        return repository.findByDentistaId(dentistaId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FilaEsperaResponse> listarPorTipoProcedimento(TipoProcedimento tipo) {
        return repository.findByTipoProcedimento(tipo).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FilaEsperaResponse> listarPorPeriodoPreferencial(FilaEspera.PeriodoPreferencial periodo) {
        return repository.findByPeriodoPreferencial(periodo).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FilaEsperaResponse> listarPorDataPreferencial(LocalDate data) {
        return repository.findByDataPreferencial(data).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FilaEsperaResponse> listarPendentesNotificacao() {
        return repository.findPendentesNotificacao().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FilaEsperaResponse> listarExpiradas() {
        return repository.findExpiradas(LocalDate.now()).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FilaEsperaResponse> listarCompativeis(Long dentistaId, LocalDate data, LocalTime horaInicio) {
        List<FilaEspera> filas = repository.findCompativeis(dentistaId, data);

        // Filtra as compatíveis com o horário
        return filas.stream()
                .filter(f -> f.compativel(data, horaInicio, dentistaId))
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FilaEsperaResponse> buscarPorPacienteNome(String nome) {
        return repository.findByPacienteNomeContaining(nome).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FilaEsperaResponse> buscarPorDentistaNome(String nome) {
        return repository.findByDentistaNomeContaining(nome).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FilaEsperaResponse> listarPorPrioridadeMinima(Integer prioridade) {
        return repository.findByPrioridadeMinima(prioridade).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FilaEsperaResponse> listarComMultiplasTentativas(Integer minTentativas) {
        return repository.findComMultiplasTentativas(minTentativas).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public FilaEsperaResponse notificar(Long id) {
        FilaEspera fila = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fila de espera não encontrada com ID: " + id));

        if (!fila.podeSerNotificado()) {
            throw new IllegalArgumentException("Fila de espera não pode ser notificada");
        }

        fila.notificar();
        fila = repository.save(fila);
        return mapper.toResponse(fila);
    }

    @Override
    @Transactional
    public FilaEsperaResponse converterEmAgendamento(Long id, Long agendamentoId) {
        FilaEspera fila = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fila de espera não encontrada com ID: " + id));

        if (!fila.isAtiva()) {
            throw new IllegalArgumentException("Apenas filas ativas podem ser convertidas em agendamento");
        }

        fila.converterEmAgendamento(agendamentoId);
        fila = repository.save(fila);
        return mapper.toResponse(fila);
    }

    @Override
    @Transactional
    public FilaEsperaResponse cancelar(Long id) {
        FilaEspera fila = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fila de espera não encontrada com ID: " + id));

        fila.cancelar();
        fila = repository.save(fila);
        return mapper.toResponse(fila);
    }

    @Override
    @Transactional
    public FilaEsperaResponse incrementarTentativaContato(Long id) {
        FilaEspera fila = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fila de espera não encontrada com ID: " + id));

        fila.incrementarTentativaContato();
        fila = repository.save(fila);
        return mapper.toResponse(fila);
    }

    @Override
    @Transactional
    public void expirarFilasAntigas() {
        List<FilaEspera> expiradas = repository.findExpiradas(LocalDate.now());
        expiradas.forEach(FilaEspera::expirar);
        repository.saveAll(expiradas);
    }

    @Override
    @Transactional(readOnly = true)
    public Long contarPorStatus(FilaEspera.StatusFila status) {
        return repository.countByStatus(status);
    }

    @Override
    @Transactional(readOnly = true)
    public Long contarPorPaciente(Long pacienteId) {
        return repository.countByPacienteId(pacienteId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long contarFilasAtivas() {
        return repository.countFilasAtivas();
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Fila de espera não encontrada com ID: " + id);
        }
        repository.deleteById(id);
    }
}