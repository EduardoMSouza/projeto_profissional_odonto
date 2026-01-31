// DentistaMapper.java
package consultorio.mapper;

import consultorio.domain.dentista.Dentista;
import consultorio.dto.request.dentista.DentistaRequest;
import consultorio.dto.response.dentista.DentistaResponse;
import org.springframework.stereotype.Component;

@Component
public class DentistaMapper {

    public Dentista toEntity(DentistaRequest request) {
        Dentista dentista = new Dentista();
        dentista.setNome(request.getNome());
        dentista.setCro(request.getCro());
        dentista.setEspecialidade(request.getEspecialidade());
        dentista.setTelefone(request.getTelefone());
        dentista.setEmail(request.getEmail());
        return dentista;
    }

    public DentistaResponse toResponse(Dentista dentista) {
        DentistaResponse response = new DentistaResponse();
        response.setId(dentista.getId());
        response.setNome(dentista.getNome());
        response.setCro(dentista.getCro());
        response.setEspecialidade(dentista.getEspecialidade());
        response.setTelefone(dentista.getTelefone());
        response.setEmail(dentista.getEmail());
        response.setAtivo(dentista.getAtivo());
        response.setCriadoEm(dentista.getCriadoEm());
        response.setAtualizadoEm(dentista.getAtualizadoEm());
        return response;
    }

    public void updateEntity(DentistaRequest request, Dentista dentista) {
        dentista.setNome(request.getNome());
        dentista.setEspecialidade(request.getEspecialidade());
        dentista.setTelefone(request.getTelefone());
        dentista.setEmail(request.getEmail());
    }
}