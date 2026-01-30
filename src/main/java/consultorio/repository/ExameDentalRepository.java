package consultorio.repository;

import consultorio.domain.exame_dental.ExameDental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExameDentalRepository extends JpaRepository<ExameDental, String> {

    List<ExameDental> findByPacienteId(Long pacienteId);

    @Query("SELECT e FROM ExameDental e WHERE e.pacienteId = :pacienteId ORDER BY e.dente DESC")
    List<ExameDental> findByPacienteIdOrderByDenteDesc(@Param("pacienteId") Long pacienteId);

    @Query("SELECT e FROM ExameDental e WHERE e.dente BETWEEN :dataInicio AND :dataFim ORDER BY e.dente DESC")
    List<ExameDental> findByDenteBetween(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);

    @Query("SELECT e FROM ExameDental e WHERE e.pacienteId = :pacienteId AND e.dente BETWEEN :dataInicio AND :dataFim ORDER BY e.dente DESC")
    List<ExameDental> findByPacienteIdAndDenteBetween(
            @Param("pacienteId") Long pacienteId,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim
    );

    @Query("SELECT COUNT(e) FROM ExameDental e WHERE e.pacienteId = :pacienteId")
    Long countByPacienteId(@Param("pacienteId") Long pacienteId);

    @Query("SELECT e FROM ExameDental e WHERE LOWER(e.pacienteNome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<ExameDental> findByPacienteNomeContaining(@Param("nome") String nome);
}
