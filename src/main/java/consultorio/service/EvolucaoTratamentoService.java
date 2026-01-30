package consultorio.service;

import consultorio.dto.request.evolcao_tratamento.EvolucaoTratamentoRequest;
import consultorio.dto.response.evolcao_tratamento.EvolucaoTratamentoResponse;

import java.time.LocalDate;
import java.util.List;

public interface EvolucaoTratamentoService {

    EvolucaoTratamentoResponse criar(EvolucaoTratamentoRequest request);

    EvolucaoTratamentoResponse atualizar(String id, EvolucaoTratamentoRequest request);

    EvolucaoTratamentoResponse buscarPorId(String id);

    List<EvolucaoTratamentoResponse> listarTodos();

    List<EvolucaoTratamentoResponse> listarPorPaciente(Long pacienteId);

    List<EvolucaoTratamentoResponse> listarPorPacienteOrdenado(Long pacienteId);

    List<EvolucaoTratamentoResponse> listarPorPeriodo(LocalDate dataInicio, LocalDate dataFim);

    List<EvolucaoTratamentoResponse> listarPorPacienteEPeriodo(Long pacienteId, LocalDate dataInicio, LocalDate dataFim);

    List<EvolucaoTratamentoResponse> buscarPorNomePaciente(String nome);

    List<EvolucaoTratamentoResponse> buscarPorPacienteEData(Long pacienteId, LocalDate data);

    Long contarPorPaciente(Long pacienteId);

    void deletar(String id);
}