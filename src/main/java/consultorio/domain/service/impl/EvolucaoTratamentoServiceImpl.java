package consultorio.domain.service.impl;// EvolucaoTratamentoServiceImpl.java

import consultorio.api.dto.request.tratamento.EvolucaoTratamentoRequest;
import consultorio.api.dto.response.tratamento.EvolucaoTratamentoResponse;
import consultorio.api.mapper.tratamento.EvolucaoTratamentoMapper;
import consultorio.domain.entity.pessoa.Dentista;
import consultorio.domain.entity.tratamento.EvolucaoTratamento;
import consultorio.domain.repository.pessoa.DentistaRepository;
import consultorio.domain.repository.pessoa.PacienteRepository;
import consultorio.domain.repository.tratamento.EvolucaoTratamentoRepository;
import consultorio.domain.service.EvolucaoTratamentoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EvolucaoTratamentoServiceImpl implements EvolucaoTratamentoService {

    private final EvolucaoTratamentoRepository evolucaoRepository;
    private final PacienteRepository pacienteRepository;
    private final DentistaRepository dentistaRepository;
    private final EvolucaoTratamentoMapper mapper;

    @Override
    @Transactional
    public EvolucaoTratamentoResponse criar(EvolucaoTratamentoRequest request) {
        Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado"));

        Dentista dentista = dentistaRepository.findById(request.getDentistaId())
                .orElseThrow(() -> new EntityNotFoundException("Dentista não encontrado"));

        EvolucaoTratamento evolucao = mapper.toEntity(request);
        evolucao.setPaciente(paciente);
        evolucao.setDentista(dentista);

        EvolucaoTratamento salvo = evolucaoRepository.save(evolucao);
        return mapper.toResponse(salvo);
    }

    @Override
    @Transactional(readOnly = true)
    public EvolucaoTratamentoResponse buscarPorId(Long id) {
        EvolucaoTratamento evolucao = evolucaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evolução de tratamento não encontrada"));
        return mapper.toResponse(evolucao);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EvolucaoTratamentoResponse> buscarTodos() {
        return evolucaoRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EvolucaoTratamentoResponse> buscarPorPaciente(Long pacienteId) {
        return evolucaoRepository.findByPacienteId(pacienteId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EvolucaoTratamentoResponse> buscarPorDentista(Long dentistaId) {
        return evolucaoRepository.findByDentistaId(dentistaId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EvolucaoTratamentoResponse> buscarPorPeriodo(LocalDate inicio, LocalDate fim) {
        return evolucaoRepository.findByDataBetween(inicio, fim)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public EvolucaoTratamentoResponse atualizar(Long id, EvolucaoTratamentoRequest request) {
        EvolucaoTratamento evolucao = evolucaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evolução de tratamento não encontrada"));

        // Verificar se paciente ou dentista foram alterados
        if (!evolucao.getPaciente().getId().equals(request.getPacienteId())) {
            Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                    .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado"));
            evolucao.setPaciente(paciente);
        }

        if (!evolucao.getDentista().getId().equals(request.getDentistaId())) {
            Dentista dentista = dentistaRepository.findById(request.getDentistaId())
                    .orElseThrow(() -> new EntityNotFoundException("Dentista não encontrado"));
            evolucao.setDentista(dentista);
        }

        mapper.updateEntity(evolucao, request);
        EvolucaoTratamento atualizado = evolucaoRepository.save(evolucao);
        return mapper.toResponse(atualizado);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        if (!evolucaoRepository.existsById(id)) {
            throw new EntityNotFoundException("Evolução de tratamento não encontrada");
        }
        evolucaoRepository.deleteById(id);
    }
}