// DentistaService.java
package consultorio.service;

import consultorio.dto.request.dentista.DentistaRequest;
import consultorio.dto.response.dentista.DentistaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DentistaService {

    DentistaResponse criar(DentistaRequest request);

    DentistaResponse buscarPorId(Long id);

    Page<DentistaResponse> listarTodos(Pageable pageable);

    Page<DentistaResponse> listarAtivos(Pageable pageable);

    Page<DentistaResponse> listarPorEspecialidade(String especialidade, Pageable pageable);

    DentistaResponse atualizar(Long id, DentistaRequest request);

    void desativar(Long id);

    void ativar(Long id);

    void deletar(Long id);
}