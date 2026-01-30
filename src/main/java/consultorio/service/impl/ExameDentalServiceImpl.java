package consultorio.service.impl;

import consultorio.domain.exame_dental.ExameDental;

import consultorio.dto.request.exame_dental.ExameDentalRequest;
import consultorio.dto.response.exame_dental.ExameDentalResponse;
import consultorio.mapper.ExameDentalMapper;
import consultorio.repository.ExameDentalRepository;
import consultorio.service.ExameDentalService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExameDentalServiceImpl implements ExameDentalService {

    private final ExameDentalRepository repository;
    private final ExameDentalMapper mapper;

    public ExameDentalServiceImpl(ExameDentalRepository repository, ExameDentalMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public ExameDentalResponse criar(ExameDentalRequest request) {
        ExameDental exame = mapper.toEntity(request);
        exame = repository.save(exame);
        return mapper.toResponse(exame);
    }

    @Override
    @Transactional
    public ExameDentalResponse atualizar(String id, ExameDentalRequest request) {
        ExameDental exame = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Exame dental não encontrado com ID: " + id));

        mapper.updateEntity(exame, request);
        exame = repository.save(exame);
        return mapper.toResponse(exame);
    }

    @Override
    @Transactional(readOnly = true)
    public ExameDentalResponse buscarPorId(String id) {
        ExameDental exame = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Exame dental não encontrado com ID: " + id));
        return mapper.toResponse(exame);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExameDentalResponse> listarTodos() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExameDentalResponse> listarPorPaciente(Long pacienteId) {
        return repository.findByPacienteId(pacienteId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExameDentalResponse> listarPorPacienteOrdenado(Long pacienteId) {
        return repository.findByPacienteIdOrderByDenteDesc(pacienteId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExameDentalResponse> listarPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        return repository.findByDenteBetween(dataInicio, dataFim).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExameDentalResponse> listarPorPacienteEPeriodo(Long pacienteId, LocalDate dataInicio, LocalDate dataFim) {
        return repository.findByPacienteIdAndDenteBetween(pacienteId, dataInicio, dataFim).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExameDentalResponse> buscarPorNomePaciente(String nome) {
        return repository.findByPacienteNomeContaining(nome).stream()
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
            throw new IllegalArgumentException("Exame dental não encontrado com ID: " + id);
        }
        repository.deleteById(id);
    }
}