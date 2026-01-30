package consultorio.service.impl;

import consultorio.domain.evolucao_tratamento.EvolucaoTratamento;
import consultorio.dto.request.evolcao_tratamento.EvolucaoTratamentoRequest;
import consultorio.dto.response.evolcao_tratamento.EvolucaoTratamentoResponse;
import consultorio.mapper.EvolucaoTratamentoMapper;
import consultorio.repository.EvolucaoTratamentoRepository;
import consultorio.service.EvolucaoTratamentoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EvolucaoTratamentoServiceImpl implements EvolucaoTratamentoService {

    private final EvolucaoTratamentoRepository repository;
    private final EvolucaoTratamentoMapper mapper;

    public EvolucaoTratamentoServiceImpl(EvolucaoTratamentoRepository repository, EvolucaoTratamentoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public EvolucaoTratamentoResponse criar(EvolucaoTratamentoRequest request) {
        EvolucaoTratamento evolucao = mapper.toEntity(request);
        evolucao = repository.save(evolucao);
        return mapper.toResponse(evolucao);
    }

    @Override
    @Transactional
    public EvolucaoTratamentoResponse atualizar(String id, EvolucaoTratamentoRequest request) {
        EvolucaoTratamento evolucao = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Evolução de tratamento não encontrada com ID: " + id));

        mapper.updateEntity(evolucao, request);
        evolucao = repository.save(evolucao);
        return mapper.toResponse(evolucao);
    }

    @Override
    @Transactional(readOnly = true)
    public EvolucaoTratamentoResponse buscarPorId(String id) {
        EvolucaoTratamento evolucao = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Evolução de tratamento não encontrada com ID: " + id));
        return mapper.toResponse(evolucao);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EvolucaoTratamentoResponse> listarTodos() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EvolucaoTratamentoResponse> listarPorPaciente(Long pacienteId) {
        return repository.findByPacienteId(pacienteId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EvolucaoTratamentoResponse> listarPorPacienteOrdenado(Long pacienteId) {
        return repository.findByPacienteIdOrderByDataDesc(pacienteId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EvolucaoTratamentoResponse> listarPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        return repository.findByDataBetween(dataInicio, dataFim).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EvolucaoTratamentoResponse> listarPorPacienteEPeriodo(Long pacienteId, LocalDate dataInicio, LocalDate dataFim) {
        return repository.findByPacienteIdAndDataBetween(pacienteId, dataInicio, dataFim).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EvolucaoTratamentoResponse> buscarPorNomePaciente(String nome) {
        return repository.findByPacienteNomeContaining(nome).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EvolucaoTratamentoResponse> buscarPorPacienteEData(Long pacienteId, LocalDate data) {
        return repository.findByPacienteIdAndData(pacienteId, data).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Long contarPorPaciente(Long pacienteId) {
        return repository.countByPacienteId(pacienteId);
    }

    @Override
    @Transactional
    public void deletar(String id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Evolução de tratamento não encontrada com ID: " + id);
        }
        repository.deleteById(id);
    }
}