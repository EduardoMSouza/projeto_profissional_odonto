package consultorio.domain.agenda;

import consultorio.domain.agenda.TipoProcedimento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "fila_espera")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilaEspera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "paciente_id", nullable = false)
    private Long pacienteId;

    @Column(name = "paciente_nome", nullable = false)
    private String pacienteNome;

    @Column(name = "dentista_id")
    private Long dentistaId;

    @Column(name = "dentista_nome")
    private String dentistaNome;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_procedimento")
    private TipoProcedimento tipoProcedimento;

    @Column(name = "data_preferencial")
    private LocalDate dataPreferencial;

    @Column(name = "hora_inicio_preferencial")
    private LocalTime horaInicioPreferencial;

    @Column(name = "hora_fim_preferencial")
    private LocalTime horaFimPreferencial;

    @Column(name = "periodo_preferencial", length = 20)
    @Enumerated(EnumType.STRING)
    private PeriodoPreferencial periodoPreferencial;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusFila status = StatusFila.AGUARDANDO;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @Column(nullable = false)
    private Integer prioridade = 0;

    @Column(name = "aceita_qualquer_horario")
    private Boolean aceitaQualquerHorario = false;

    @Column(name = "aceita_qualquer_dentista")
    private Boolean aceitaQualquerDentista = false;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em", nullable = false)
    private LocalDateTime atualizadoEm;

    @Column(name = "criado_por", length = 100)
    private String criadoPor;

    @Column(name = "agendamento_id")
    private Long agendamentoId;

    @Column(name = "convertido_em")
    private LocalDateTime convertidoEm;

    @Column(name = "notificado")
    private Boolean notificado = false;

    @Column(name = "notificado_em")
    private LocalDateTime notificadoEm;

    @Column(name = "tentativas_contato")
    private Integer tentativasContato = 0;

    @Column(name = "ultima_tentativa_contato")
    private LocalDateTime ultimaTentativaContato;

    public enum StatusFila {
        AGUARDANDO("Aguardando Vaga"),
        NOTIFICADO("Notificado"),
        CONVERTIDO("Convertido em Agendamento"),
        CANCELADO("Cancelado"),
        EXPIRADO("Expirado");

        private final String descricao;

        StatusFila(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }

    public enum PeriodoPreferencial {
        MANHA("Manhã"),
        TARDE("Tarde"),
        QUALQUER("Qualquer Horário");

        private final String descricao;

        PeriodoPreferencial(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }

        public boolean contemHorario(LocalTime hora) {
            switch (this) {
                case MANHA:
                    return hora.isBefore(LocalTime.of(12, 0));
                case TARDE:
                    return !hora.isBefore(LocalTime.of(12, 0));
                case QUALQUER:
                default:
                    return true;
            }
        }
    }

    @PrePersist
    protected void onCreate() {
        criadoEm = LocalDateTime.now();
        atualizadoEm = LocalDateTime.now();
        if (status == null) {
            status = StatusFila.AGUARDANDO;
        }
        if (prioridade == null) {
            prioridade = 0;
        }
        if (notificado == null) {
            notificado = false;
        }
        if (tentativasContato == null) {
            tentativasContato = 0;
        }
        if (aceitaQualquerHorario == null) {
            aceitaQualquerHorario = false;
        }
        if (aceitaQualquerDentista == null) {
            aceitaQualquerDentista = false;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        atualizadoEm = LocalDateTime.now();
    }

    // Métodos de negócio
    public boolean isAtiva() {
        return status == StatusFila.AGUARDANDO || status == StatusFila.NOTIFICADO;
    }

    public boolean podeSerNotificado() {
        return isAtiva() && !notificado;
    }

    public boolean isExpirado() {
        if (dataPreferencial == null) {
            return false;
        }
        return dataPreferencial.isBefore(LocalDate.now());
    }

    public void notificar() {
        this.status = StatusFila.NOTIFICADO;
        this.notificado = true;
        this.notificadoEm = LocalDateTime.now();
    }

    public void converterEmAgendamento(Long agendamentoId) {
        this.status = StatusFila.CONVERTIDO;
        this.agendamentoId = agendamentoId;
        this.convertidoEm = LocalDateTime.now();
    }

    public void cancelar() {
        this.status = StatusFila.CANCELADO;
    }

    public void expirar() {
        this.status = StatusFila.EXPIRADO;
    }

    public void incrementarTentativaContato() {
        this.tentativasContato++;
        this.ultimaTentativaContato = LocalDateTime.now();
    }

    public boolean compativel(LocalDate data, LocalTime horaInicio, Long dentistaIdParam) {
        if (dataPreferencial != null && !dataPreferencial.equals(data)) {
            return false;
        }

        if (dentistaId != null && !dentistaId.equals(dentistaIdParam)) {
            return false;
        }

        if (horaInicioPreferencial != null && horaFimPreferencial != null) {
            return !horaInicio.isBefore(horaInicioPreferencial) &&
                    !horaInicio.isAfter(horaFimPreferencial);
        }

        if (periodoPreferencial != null) {
            return periodoPreferencial.contemHorario(horaInicio);
        }

        return true;
    }
}