package consultorio.repository.agenda;

import consultorio.domain.agenda.FilaEspera;
import consultorio.domain.agenda.TipoProcedimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FilaEsperaRepository extends JpaRepository<FilaEspera, Long> {

    // Busca por status
    @Query("SELECT f FROM FilaEspera f WHERE f.status = :status ORDER BY f.prioridade DESC, f.criadoEm ASC")
    List<FilaEspera> findByStatus(@Param("status") FilaEspera.StatusFila status);

    // Busca filas ativas (aguardando ou notificado)
    @Query("SELECT f FROM FilaEspera f WHERE f.status IN ('AGUARDANDO', 'NOTIFICADO') ORDER BY f.prioridade DESC, f.criadoEm ASC")
    List<FilaEspera> findFilasAtivas();

    // Busca por paciente
    @Query("SELECT f FROM FilaEspera f WHERE f.pacienteId = :pacienteId ORDER BY f.criadoEm DESC")
    List<FilaEspera> findByPacienteId(@Param("pacienteId") Long pacienteId);

    // Busca por dentista
    @Query("SELECT f FROM FilaEspera f WHERE f.dentistaId = :dentistaId AND f.status IN ('AGUARDANDO', 'NOTIFICADO') ORDER BY f.prioridade DESC, f.criadoEm ASC")
    List<FilaEspera> findByDentistaId(@Param("dentistaId") Long dentistaId);

    // Busca por tipo de procedimento
    @Query("SELECT f FROM FilaEspera f WHERE f.tipoProcedimento = :tipo AND f.status IN ('AGUARDANDO', 'NOTIFICADO') ORDER BY f.prioridade DESC, f.criadoEm ASC")
    List<FilaEspera> findByTipoProcedimento(@Param("tipo") TipoProcedimento tipo);

    // Busca por período preferencial
    @Query("SELECT f FROM FilaEspera f WHERE f.periodoPreferencial = :periodo AND f.status IN ('AGUARDANDO', 'NOTIFICADO') ORDER BY f.prioridade DESC, f.criadoEm ASC")
    List<FilaEspera> findByPeriodoPreferencial(@Param("periodo") FilaEspera.PeriodoPreferencial periodo);

    // Busca por data preferencial
    @Query("SELECT f FROM FilaEspera f WHERE f.dataPreferencial = :data AND f.status IN ('AGUARDANDO', 'NOTIFICADO') ORDER BY f.prioridade DESC, f.criadoEm ASC")
    List<FilaEspera> findByDataPreferencial(@Param("data") LocalDate data);

    // Busca pendentes de notificação
    @Query("SELECT f FROM FilaEspera f WHERE f.status = 'AGUARDANDO' AND f.notificado = false ORDER BY f.prioridade DESC, f.criadoEm ASC")
    List<FilaEspera> findPendentesNotificacao();

    // Busca expiradas
    @Query("SELECT f FROM FilaEspera f WHERE f.dataPreferencial < :dataAtual AND f.status IN ('AGUARDANDO', 'NOTIFICADO')")
    List<FilaEspera> findExpiradas(@Param("dataAtual") LocalDate dataAtual);

    // Busca compatíveis com horário e dentista
    @Query("SELECT f FROM FilaEspera f WHERE f.status IN ('AGUARDANDO', 'NOTIFICADO') " +
            "AND (f.dentistaId = :dentistaId OR f.dentistaId IS NULL OR f.aceitaQualquerDentista = true) " +
            "AND (f.dataPreferencial = :data OR f.dataPreferencial IS NULL) " +
            "ORDER BY f.prioridade DESC, f.criadoEm ASC")
    List<FilaEspera> findCompativeis(@Param("dentistaId") Long dentistaId, @Param("data") LocalDate data);

    // Busca por nome de paciente
    @Query("SELECT f FROM FilaEspera f WHERE LOWER(f.pacienteNome) LIKE LOWER(CONCAT('%', :nome, '%')) ORDER BY f.criadoEm DESC")
    List<FilaEspera> findByPacienteNomeContaining(@Param("nome") String nome);

    // Busca por nome de dentista
    @Query("SELECT f FROM FilaEspera f WHERE LOWER(f.dentistaNome) LIKE LOWER(CONCAT('%', :nome, '%')) ORDER BY f.criadoEm DESC")
    List<FilaEspera> findByDentistaNomeContaining(@Param("nome") String nome);

    // Busca por prioridade mínima
    @Query("SELECT f FROM FilaEspera f WHERE f.prioridade >= :prioridade AND f.status IN ('AGUARDANDO', 'NOTIFICADO') ORDER BY f.prioridade DESC, f.criadoEm ASC")
    List<FilaEspera> findByPrioridadeMinima(@Param("prioridade") Integer prioridade);

    // Conta por status
    @Query("SELECT COUNT(f) FROM FilaEspera f WHERE f.status = :status")
    Long countByStatus(@Param("status") FilaEspera.StatusFila status);

    // Conta por paciente
    @Query("SELECT COUNT(f) FROM FilaEspera f WHERE f.pacienteId = :pacienteId")
    Long countByPacienteId(@Param("pacienteId") Long pacienteId);

    // Conta filas ativas
    @Query("SELECT COUNT(f) FROM FilaEspera f WHERE f.status IN ('AGUARDANDO', 'NOTIFICADO')")
    Long countFilasAtivas();

    // Busca com múltiplas tentativas de contato
    @Query("SELECT f FROM FilaEspera f WHERE f.tentativasContato >= :minTentativas AND f.status = 'NOTIFICADO' ORDER BY f.tentativasContato DESC")
    List<FilaEspera> findComMultiplasTentativas(@Param("minTentativas") Integer minTentativas);
}