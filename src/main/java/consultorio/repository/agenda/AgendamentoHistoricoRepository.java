package consultorio.repository.agenda;

import consultorio.domain.agenda.AgendamentoHistorico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AgendamentoHistoricoRepository extends JpaRepository<AgendamentoHistorico, Long> {

    // Busca histórico por agendamento
    @Query("SELECT h FROM AgendamentoHistorico h WHERE h.agendamentoId = :agendamentoId ORDER BY h.dataHora DESC")
    List<AgendamentoHistorico> findByAgendamentoIdOrderByDataHoraDesc(@Param("agendamentoId") Long agendamentoId);

    // Busca por tipo de ação
    @Query("SELECT h FROM AgendamentoHistorico h WHERE h.acao = :acao ORDER BY h.dataHora DESC")
    List<AgendamentoHistorico> findByAcao(@Param("acao") AgendamentoHistorico.TipoAcao acao);

    // Busca por usuário
    @Query("SELECT h FROM AgendamentoHistorico h WHERE h.usuarioResponsavel = :usuario ORDER BY h.dataHora DESC")
    List<AgendamentoHistorico> findByUsuarioResponsavel(@Param("usuario") String usuario);

    // Busca por período
    @Query("SELECT h FROM AgendamentoHistorico h WHERE h.dataHora BETWEEN :inicio AND :fim ORDER BY h.dataHora DESC")
    List<AgendamentoHistorico> findByPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    // Busca por agendamento e período
    @Query("SELECT h FROM AgendamentoHistorico h WHERE h.agendamentoId = :agendamentoId AND h.dataHora BETWEEN :inicio AND :fim ORDER BY h.dataHora DESC")
    List<AgendamentoHistorico> findByAgendamentoAndPeriodo(
            @Param("agendamentoId") Long agendamentoId,
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
    );

    // Busca últimas ações
    @Query("SELECT h FROM AgendamentoHistorico h ORDER BY h.dataHora DESC")
    List<AgendamentoHistorico> findRecentActions();

    // Conta ações por tipo
    @Query("SELECT COUNT(h) FROM AgendamentoHistorico h WHERE h.acao = :acao")
    Long countByAcao(@Param("acao") AgendamentoHistorico.TipoAcao acao);

    // Conta ações por agendamento
    @Query("SELECT COUNT(h) FROM AgendamentoHistorico h WHERE h.agendamentoId = :agendamentoId")
    Long countByAgendamentoId(@Param("agendamentoId") Long agendamentoId);
}