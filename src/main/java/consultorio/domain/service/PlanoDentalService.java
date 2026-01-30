package consultorio.domain.service;

import consultorio.api.dto.request.plano_dental.PlanoDentalRequest;
import consultorio.api.dto.response.plano_dental.PlanoDentalResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface PlanoDentalService {

    PlanoDentalResponse criar(PlanoDentalRequest request);

    PlanoDentalResponse buscarPorId(Long id);

    List<PlanoDentalResponse> buscarTodos();

    Page<PlanoDentalResponse> buscarTodosPaginados(Pageable pageable);

    List<PlanoDentalResponse> buscarPorPaciente(Long pacienteId);

    Page<PlanoDentalResponse> buscarPorPacientePaginado(Long pacienteId, Pageable pageable);

    List<PlanoDentalResponse> buscarPorDentista(Long dentistaId);

    List<PlanoDentalResponse> buscarPorDente(String dente);

    List<PlanoDentalResponse> buscarPorProcedimento(String procedimento);

    List<PlanoDentalResponse> buscarPorValorEntre(BigDecimal valorMin, BigDecimal valorMax);

    List<PlanoDentalResponse> buscarPorPacienteEDentista(Long pacienteId, Long dentistaId);

    BigDecimal calcularTotalValorPorPaciente(Long pacienteId);

    BigDecimal calcularTotalValorFinalPorPaciente(Long pacienteId);

    PlanoDentalResponse atualizar(Long id, PlanoDentalRequest request);

    void deletar(Long id);
}