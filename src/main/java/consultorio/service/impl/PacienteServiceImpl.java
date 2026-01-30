package consultorio.service.impl;

import consultorio.domain.paciente.Paciente;
import consultorio.dto.request.paciente.PacienteRequest;
import consultorio.dto.response.paciente.PacienteResponse;
import consultorio.mapper.PacienteMapper;
import consultorio.repository.PacienteRepository;
import consultorio.service.PacienteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository repository;
    private final PacienteMapper mapper;

    public PacienteServiceImpl(PacienteRepository repository, PacienteMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public PacienteResponse criar(PacienteRequest request) {
        if (repository.existsByProntuario(request.getProntuario())) {
            throw new IllegalArgumentException("Prontuário já existe: " + request.getProntuario());
        }

        if (request.getCpf() != null && repository.existsByCpf(request.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado: " + request.getCpf());
        }

        Paciente paciente = mapper.toEntity(request);
        paciente = repository.save(paciente);
        return mapper.toResponse(paciente);
    }

    @Override
    @Transactional
    public PacienteResponse atualizar(String id, PacienteRequest request) {
        Paciente paciente = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado com ID: " + id));

        // Verifica prontuário duplicado
        if (!paciente.getProntuario().equals(request.getProntuario()) &&
                repository.existsByProntuario(request.getProntuario())) {
            throw new IllegalArgumentException("Prontuário já existe: " + request.getProntuario());
        }

        // Verifica CPF duplicado
        if (request.getCpf() != null &&
                !request.getCpf().equals(paciente.getCpf()) &&
                repository.existsByCpf(request.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado: " + request.getCpf());
        }

        mapper.updateEntity(paciente, request);
        paciente = repository.save(paciente);
        return mapper.toResponse(paciente);
    }

    @Override
    @Transactional(readOnly = true)
    public PacienteResponse buscarPorId(String id) {
        Paciente paciente = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado com ID: " + id));
        return mapper.toResponse(paciente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PacienteResponse> listarTodos() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PacienteResponse buscarPorProntuario(String prontuario) {
        Paciente paciente = repository.findByProntuario(prontuario)
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado com prontuário: " + prontuario));
        return mapper.toResponse(paciente);
    }

    @Override
    @Transactional(readOnly = true)
    public PacienteResponse buscarPorCpf(String cpf) {
        Paciente paciente = repository.findByCpf(cpf)
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado com CPF: " + cpf));
        return mapper.toResponse(paciente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PacienteResponse> buscarPorNome(String nome) {
        return repository.findByNomeContaining(nome).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PacienteResponse> buscarPorTelefone(String telefone) {
        return repository.findByTelefone(telefone).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PacienteResponse> buscarPorConvenio(String convenio) {
        return repository.findByConvenioContaining(convenio).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deletar(String id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Paciente não encontrado com ID: " + id);
        }
        repository.deleteById(id);
    }
}
