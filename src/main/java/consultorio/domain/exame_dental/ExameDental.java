package consultorio.domain.exame_dental;

import consultorio.domain.paciente.Paciente;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "exames_dentais")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExameDental {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "paciente_id_exame_dental")
    private Long pacienteId;

    @Column(name = "paciente_nome_exame_dental")
    private String pacienteNome;

    @Column(nullable = false)
    private LocalDate dente;

    @Column(nullable = false, length = 1000)
    private String procedimento;

    @Column(precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(length = 2000)
    private String observacoes;

    @Column(name = "valor_total", precision = 10, scale = 2)
    private BigDecimal valorTotal;

    @Column(name = "criado_em")
    private LocalDate criadoEm;

    @Column(name = "atualizado_em")
    private LocalDate atualizadoEm;

    @PrePersist
    protected void onCreate() {
        criadoEm = LocalDate.now();
        atualizadoEm = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        atualizadoEm = LocalDate.now();
    }
}