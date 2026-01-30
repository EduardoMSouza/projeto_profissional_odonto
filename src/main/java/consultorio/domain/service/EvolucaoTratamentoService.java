package consultorio.domain.service;// EvolucaoTratamentoService.java

import consultorio.api.dto.request.tratamento.EvolucaoTratamentoRequest;
import consultorio.api.dto.response.tratamento.EvolucaoTratamentoResponse;

import java.time.LocalDate;
import java.util.List;

public interface EvolucaoTratamentoService {

    EvolucaoTratamentoResponse criar(EvolucaoTratamentoRequest request);

    EvolucaoTratamentoResponse buscarPorId(Long id);

    List<EvolucaoTratamentoResponse> buscarTodos();

    List<EvolucaoTratamentoResponse> buscarPorPaciente(Long pacienteId);

    List<EvolucaoTratamentoResponse> buscarPorDentista(Long dentistaId);

    List<EvolucaoTratamentoResponse> buscarPorPeriodo(LocalDate inicio, LocalDate fim);

    EvolucaoTratamentoResponse atualizar(Long id, EvolucaoTratamentoRequest request);

    void deletar(Long id);
}