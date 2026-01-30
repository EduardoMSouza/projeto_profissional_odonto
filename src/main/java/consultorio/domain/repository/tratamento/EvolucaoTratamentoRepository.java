package consultorio.domain.repository.tratamento;

import consultorio.domain.entity.tratamento.EvolucaoTratamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EvolucaoTratamentoRepository extends JpaRepository<EvolucaoTratamento, Long> {

    List<EvolucaoTratamento> findByPacienteId(Long pacienteId);

    List<EvolucaoTratamento> findByDentistaId(Long dentistaId);

    List<EvolucaoTratamento> findByDataBetween(LocalDate inicio, LocalDate fim);

    @Query("SELECT e FROM EvolucaoTratamento e WHERE e.paciente.id = :pacienteId AND e.data BETWEEN :inicio AND :fim")
    List<EvolucaoTratamento> findByPacienteAndPeriodo(
            @Param("pacienteId") Long pacienteId,
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim);
}