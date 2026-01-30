package consultorio.domain.repository.tratamento;

import consultorio.domain.entity.tratamento.PlanoDental;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PlanoDentalRepository extends JpaRepository<PlanoDental, Long> {

    List<PlanoDental> findByPacienteId(Long pacienteId);

    List<PlanoDental> findByDentistaId(Long dentistaId);

    List<PlanoDental> findByDente(String dente);

    List<PlanoDental> findByProcedimentoContainingIgnoreCase(String procedimento);

    @Query("SELECT p FROM PlanoDental p WHERE p.valor BETWEEN :valorMin AND :valorMax")
    List<PlanoDental> findByValorBetween(
            @Param("valorMin") BigDecimal valorMin,
            @Param("valorMax") BigDecimal valorMax);

    @Query("SELECT p FROM PlanoDental p WHERE p.paciente.id = :pacienteId AND p.dentista.id = :dentistaId")
    List<PlanoDental> findByPacienteAndDentista(
            @Param("pacienteId") Long pacienteId,
            @Param("dentistaId") Long dentistaId);

    @Query("SELECT SUM(p.valor) FROM PlanoDental p WHERE p.paciente.id = :pacienteId")
    BigDecimal sumValorByPaciente(@Param("pacienteId") Long pacienteId);

    @Query("SELECT SUM(p.valorFinal) FROM PlanoDental p WHERE p.paciente.id = :pacienteId")
    BigDecimal sumValorFinalByPaciente(@Param("pacienteId") Long pacienteId);

    Page<PlanoDental> findAll(Pageable pageable);

    Page<PlanoDental> findByPacienteId(Long pacienteId, Pageable pageable);
}