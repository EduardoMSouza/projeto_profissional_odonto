package consultorio.repository;

import consultorio.domain.evolucao_tratamento.EvolucaoTratamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EvolucaoTratamentoRepository extends JpaRepository<EvolucaoTratamento, String> {

    List<EvolucaoTratamento> findByPacienteId(Long pacienteId);

    @Query("SELECT e FROM EvolucaoTratamento e WHERE e.pacienteId = :pacienteId ORDER BY e.data DESC")
    List<EvolucaoTratamento> findByPacienteIdOrderByDataDesc(@Param("pacienteId") Long pacienteId);

    @Query("SELECT e FROM EvolucaoTratamento e WHERE e.data BETWEEN :dataInicio AND :dataFim ORDER BY e.data DESC")
    List<EvolucaoTratamento> findByDataBetween(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);

    @Query("SELECT e FROM EvolucaoTratamento e WHERE e.pacienteId = :pacienteId AND e.data BETWEEN :dataInicio AND :dataFim ORDER BY e.data DESC")
    List<EvolucaoTratamento> findByPacienteIdAndDataBetween(
            @Param("pacienteId") Long pacienteId,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim
    );

    @Query("SELECT COUNT(e) FROM EvolucaoTratamento e WHERE e.pacienteId = :pacienteId")
    Long countByPacienteId(@Param("pacienteId") Long pacienteId);

    @Query("SELECT e FROM EvolucaoTratamento e WHERE LOWER(e.pacienteNome) LIKE LOWER(CONCAT('%', :nome, '%')) ORDER BY e.data DESC")
    List<EvolucaoTratamento> findByPacienteNomeContaining(@Param("nome") String nome);

    @Query("SELECT e FROM EvolucaoTratamento e WHERE e.pacienteId = :pacienteId AND e.data = :data")
    List<EvolucaoTratamento> findByPacienteIdAndData(@Param("pacienteId") Long pacienteId, @Param("data") LocalDate data);
}