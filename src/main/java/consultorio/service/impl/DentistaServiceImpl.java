// DentistaServiceImpl.java
package consultorio.service.impl;

import consultorio.domain.dentista.Dentista;
import consultorio.dto.request.dentista.DentistaRequest;
import consultorio.dto.response.dentista.DentistaResponse;
import consultorio.mapper.DentistaMapper;
import consultorio.repository.DentistaRepository;
import consultorio.service.DentistaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DentistaServiceImpl implements DentistaService {

    private final DentistaRepository repository;
    private final DentistaMapper mapper;

    @Override
    @Transactional
    public DentistaResponse criar(DentistaRequest request) {
        if (repository.existsByCro(request.getCro())) {
            throw new IllegalArgumentException("CRO já cadastrado");
        }
        if (repository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        Dentista dentista = mapper.toEntity(request);
        Dentista saved = repository.save(dentista);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public DentistaResponse buscarPorId(Long id) {
        Dentista dentista = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Dentista não encontrado"));
        return mapper.toResponse(dentista);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DentistaResponse> listarTodos(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DentistaResponse> listarAtivos(Pageable pageable) {
        return repository.findByAtivoTrue(pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DentistaResponse> listarPorEspecialidade(String especialidade, Pageable pageable) {
        return repository.findByAtivoTrueAndEspecialidade(especialidade, pageable)
                .map(mapper::toResponse);
    }

    @Override
    @Transactional
    public DentistaResponse atualizar(Long id, DentistaRequest request) {
        Dentista dentista = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Dentista não encontrado"));

        if (repository.existsByEmailAndIdNot(request.getEmail(), id)) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        mapper.updateEntity(request, dentista);
        Dentista updated = repository.save(dentista);
        return mapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void desativar(Long id) {
        Dentista dentista = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Dentista não encontrado"));
        dentista.desativar();
        repository.save(dentista);
    }

    @Override
    @Transactional
    public void ativar(Long id) {
        Dentista dentista = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Dentista não encontrado"));
        dentista.ativar();
        repository.save(dentista);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Dentista não encontrado");
        }
        repository.deleteById(id);
    }
}