package consultorio.domain.service.impl;

import consultorio.api.dto.request.plano_dental.PlanoDentalRequest;
import consultorio.api.dto.response.plano_dental.PlanoDentalResponse;
import consultorio.api.mapper.tratamento.PlanoDentalMapper;
import consultorio.domain.entity.pessoa.Dentista;

import consultorio.domain.entity.tratamento.PlanoDental;
import consultorio.domain.repository.pessoa.DentistaRepository;
import consultorio.domain.repository.pessoa.PacienteRepository;
import consultorio.domain.repository.tratamento.PlanoDentalRepository;
import consultorio.domain.service.PlanoDentalService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanoDentalServiceImpl implements PlanoDentalService {

    private final PlanoDentalRepository planoRepository;
    private final PacienteRepository pacienteRepository;
    private final DentistaRepository dentistaRepository;
    private final PlanoDentalMapper mapper;

    @Override
    @Transactional
    public PlanoDentalResponse criar(PlanoDentalRequest request) {
        Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado"));

        Dentista dentista = dentistaRepository.findById(request.getDentistaId())
                .orElseThrow(() -> new EntityNotFoundException("Dentista não encontrado"));

        PlanoDental plano = mapper.toEntity(request);
        plano.setPaciente(paciente);
        plano.setDentista(dentista);

        PlanoDental salvo = planoRepository.save(plano);
        return mapper.toResponse(salvo);
    }

    @Override
    @Transactional(readOnly = true)
    public PlanoDentalResponse buscarPorId(Long id) {
        PlanoDental plano = planoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Plano dental não encontrado"));
        return mapper.toResponse(plano);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlanoDentalResponse> buscarTodos() {
        return planoRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlanoDentalResponse> buscarTodosPaginados(Pageable pageable) {
        return planoRepository.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlanoDentalResponse> buscarPorPaciente(Long pacienteId) {
        return planoRepository.findByPacienteId(pacienteId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlanoDentalResponse> buscarPorPacientePaginado(Long pacienteId, Pageable pageable) {
        return planoRepository.findByPacienteId(pacienteId, pageable)
                .map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlanoDentalResponse> buscarPorDentista(Long dentistaId) {
        return planoRepository.findByDentistaId(dentistaId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlanoDentalResponse> buscarPorDente(String dente) {
        return planoRepository.findByDente(dente)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlanoDentalResponse> buscarPorProcedimento(String procedimento) {
        return planoRepository.findByProcedimentoContainingIgnoreCase(procedimento)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlanoDentalResponse> buscarPorValorEntre(BigDecimal valorMin, BigDecimal valorMax) {
        return planoRepository.findByValorBetween(valorMin, valorMax)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlanoDentalResponse> buscarPorPacienteEDentista(Long pacienteId, Long dentistaId) {
        return planoRepository.findByPacienteAndDentista(pacienteId, dentistaId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calcularTotalValorPorPaciente(Long pacienteId) {
        BigDecimal total = planoRepository.sumValorByPaciente(pacienteId);
        return total != null ? total : BigDecimal.ZERO;
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calcularTotalValorFinalPorPaciente(Long pacienteId) {
        BigDecimal total = planoRepository.sumValorFinalByPaciente(pacienteId);
        return total != null ? total : BigDecimal.ZERO;
    }

    @Override
    @Transactional
    public PlanoDentalResponse atualizar(Long id, PlanoDentalRequest request) {
        PlanoDental plano = planoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Plano dental não encontrado"));

        // Verificar se paciente ou dentista foram alterados
        if (!plano.getPaciente().getId().equals(request.getPacienteId())) {
            Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                    .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado"));
            plano.setPaciente(paciente);
        }

        if (!plano.getDentista().getId().equals(request.getDentistaId())) {
            Dentista dentista = dentistaRepository.findById(request.getDentistaId())
                    .orElseThrow(() -> new EntityNotFoundException("Dentista não encontrado"));
            plano.setDentista(dentista);
        }

        mapper.updateEntity(plano, request);
        PlanoDental atualizado = planoRepository.save(plano);
        return mapper.toResponse(atualizado);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        if (!planoRepository.existsById(id)) {
            throw new EntityNotFoundException("Plano dental não encontrado");
        }
        planoRepository.deleteById(id);
    }
}