package consultorio.repository.agenda;

import consultorio.domain.agenda.Agendamento;
import consultorio.domain.agenda.StatusAgendamento;
import consultorio.domain.agenda.TipoProcedimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    // Busca por dentista e data
    List<Agendamento> findByDentistaIdAndDataConsulta(Long dentistaId, LocalDate data);

    // Busca por paciente e data
    List<Agendamento> findByPacienteIdAndDataConsulta(Long pacienteId, LocalDate data);

    // Busca agendamentos ativos
    @Query("SELECT a FROM Agendamento a WHERE a.dentistaId = :dentistaId AND a.dataConsulta = :data AND a.ativo = true AND a.status NOT IN ('CANCELADO', 'FALTOU')")
    List<Agendamento> findAgendamentosAtivos(@Param("dentistaId") Long dentistaId, @Param("data") LocalDate data);

    // Busca conflitos de horário
    @Query("SELECT a FROM Agendamento a WHERE a.dentistaId = :dentistaId AND a.dataConsulta = :data " +
            "AND a.ativo = true AND a.status NOT IN ('CANCELADO', 'FALTOU') " +
            "AND ((a.horaInicio < :horaFim AND a.horaFim > :horaInicio))")
    List<Agendamento> findConflitos(@Param("dentistaId") Long dentistaId,
                                    @Param("data") LocalDate data,
                                    @Param("horaInicio") LocalTime horaInicio,
                                    @Param("horaFim") LocalTime horaFim);

    // Busca por período
    @Query("SELECT a FROM Agendamento a WHERE a.dataConsulta BETWEEN :dataInicio AND :dataFim AND a.ativo = true ORDER BY a.dataConsulta, a.horaInicio")
    List<Agendamento> findByPeriodo(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);

    // Busca por dentista e período
    @Query("SELECT a FROM Agendamento a WHERE a.dentistaId = :dentistaId AND a.dataConsulta BETWEEN :dataInicio AND :dataFim AND a.ativo = true ORDER BY a.dataConsulta, a.horaInicio")
    List<Agendamento> findByDentistaAndPeriodo(@Param("dentistaId") Long dentistaId,
                                               @Param("dataInicio") LocalDate dataInicio,
                                               @Param("dataFim") LocalDate dataFim);

    // Busca por paciente e período
    @Query("SELECT a FROM Agendamento a WHERE a.pacienteId = :pacienteId AND a.dataConsulta BETWEEN :dataInicio AND :dataFim AND a.ativo = true ORDER BY a.dataConsulta, a.horaInicio")
    List<Agendamento> findByPacienteAndPeriodo(@Param("pacienteId") Long pacienteId,
                                               @Param("dataInicio") LocalDate dataInicio,
                                               @Param("dataFim") LocalDate dataFim);

    // Busca por status
    @Query("SELECT a FROM Agendamento a WHERE a.status = :status AND a.ativo = true ORDER BY a.dataConsulta, a.horaInicio")
    List<Agendamento> findByStatus(@Param("status") StatusAgendamento status);

    // Busca por data e status
    @Query("SELECT a FROM Agendamento a WHERE a.dataConsulta = :data AND a.status = :status AND a.ativo = true ORDER BY a.horaInicio")
    List<Agendamento> findByDataAndStatus(@Param("data") LocalDate data, @Param("status") StatusAgendamento status);

    // Busca por tipo de procedimento
    @Query("SELECT a FROM Agendamento a WHERE a.tipoProcedimento = :tipo AND a.ativo = true ORDER BY a.dataConsulta, a.horaInicio")
    List<Agendamento> findByTipoProcedimento(@Param("tipo") TipoProcedimento tipo);

    // Busca pendentes de lembrete
    @Query("SELECT a FROM Agendamento a WHERE a.dataConsulta = :data AND a.lembreteEnviado = false AND a.ativo = true AND a.status NOT IN ('CANCELADO', 'CONCLUIDO', 'FALTOU') ORDER BY a.horaInicio")
    List<Agendamento> findPendentesLembrete(@Param("data") LocalDate data);

    // Busca consultas do dia
    @Query("SELECT a FROM Agendamento a WHERE a.dataConsulta = :data AND a.ativo = true ORDER BY a.horaInicio")
    List<Agendamento> findConsultasDoDia(@Param("data") LocalDate data);

    // Busca por nome de paciente
    @Query("SELECT a FROM Agendamento a WHERE LOWER(a.pacienteNome) LIKE LOWER(CONCAT('%', :nome, '%')) AND a.ativo = true ORDER BY a.dataConsulta DESC, a.horaInicio")
    List<Agendamento> findByPacienteNomeContaining(@Param("nome") String nome);

    // Busca por nome de dentista
    @Query("SELECT a FROM Agendamento a WHERE LOWER(a.dentistaNome) LIKE LOWER(CONCAT('%', :nome, '%')) AND a.ativo = true ORDER BY a.dataConsulta DESC, a.horaInicio")
    List<Agendamento> findByDentistaNomeContaining(@Param("nome") String nome);

    // Conta agendamentos do dia
    @Query("SELECT COUNT(a) FROM Agendamento a WHERE a.dentistaId = :dentistaId AND a.dataConsulta = :data AND a.ativo = true AND a.status NOT IN ('CANCELADO', 'FALTOU')")
    Long countAgendamentosDia(@Param("dentistaId") Long dentistaId, @Param("data") LocalDate data);

    // Conta agendamentos por paciente
    @Query("SELECT COUNT(a) FROM Agendamento a WHERE a.pacienteId = :pacienteId AND a.ativo = true")
    Long countByPacienteId(@Param("pacienteId") Long pacienteId);

    // Conta agendamentos por status
    @Query("SELECT COUNT(a) FROM Agendamento a WHERE a.status = :status AND a.ativo = true")
    Long countByStatus(@Param("status") StatusAgendamento status);

    // Busca próximos agendamentos do paciente
    @Query("SELECT a FROM Agendamento a WHERE a.pacienteId = :pacienteId AND a.dataConsulta >= :dataAtual AND a.ativo = true AND a.status NOT IN ('CANCELADO', 'FALTOU', 'CONCLUIDO') ORDER BY a.dataConsulta, a.horaInicio")
    List<Agendamento> findProximosAgendamentosPaciente(@Param("pacienteId") Long pacienteId, @Param("dataAtual") LocalDate dataAtual);

    // Busca histórico de agendamentos do paciente
    @Query("SELECT a FROM Agendamento a WHERE a.pacienteId = :pacienteId AND a.dataConsulta < :dataAtual AND a.ativo = true ORDER BY a.dataConsulta DESC, a.horaInicio DESC")
    List<Agendamento> findHistoricoAgendamentosPaciente(@Param("pacienteId") Long pacienteId, @Param("dataAtual") LocalDate dataAtual);
}