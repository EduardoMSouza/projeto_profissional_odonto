package consultorio.service;


import consultorio.dto.request.paciente.PacienteRequest;
import consultorio.dto.response.paciente.PacienteResponse;

import java.util.List;

public interface PacienteService {

    PacienteResponse criar(PacienteRequest request);

    PacienteResponse atualizar(String id, PacienteRequest request);

    PacienteResponse buscarPorId(String id);

    List<PacienteResponse> listarTodos();

    PacienteResponse buscarPorProntuario(String prontuario);

    PacienteResponse buscarPorCpf(String cpf);

    List<PacienteResponse> buscarPorNome(String nome);

    List<PacienteResponse> buscarPorTelefone(String telefone);

    List<PacienteResponse> buscarPorConvenio(String convenio);

    void deletar(String id);
}
