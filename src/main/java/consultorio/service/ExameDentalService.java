package consultorio.service;

import consultorio.dto.request.exame_dental.ExameDentalRequest;
import consultorio.dto.response.exame_dental.ExameDentalResponse;

import java.time.LocalDate;
import java.util.List;

public interface ExameDentalService {

    ExameDentalResponse criar(ExameDentalRequest request);

    ExameDentalResponse atualizar(String id, ExameDentalRequest request);

    ExameDentalResponse buscarPorId(String id);

    List<ExameDentalResponse> listarTodos();

    List<ExameDentalResponse> listarPorPaciente(Long pacienteId);

    List<ExameDentalResponse> listarPorPacienteOrdenado(Long pacienteId);

    List<ExameDentalResponse> listarPorPeriodo(LocalDate dataInicio, LocalDate dataFim);

    List<ExameDentalResponse> listarPorPacienteEPeriodo(Long pacienteId, LocalDate dataInicio, LocalDate dataFim);

    List<ExameDentalResponse> buscarPorNomePaciente(String nome);

    Long contarPorPaciente(Long pacienteId);

    void deletar(String id);
}