package consultorio.domain.evolucao_tratamento;

import consultorio.domain.paciente.Paciente;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "evolucoes_tratamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvolucaoTratamento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "paciente_id_evaolucao_tratamento")
    private Long pacienteId;

    @Column(name = "paciente_nome_evolucao_tratamento")
    private String pacienteNome;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false, length = 3000)
    private String evolucao;

    @Column(name = "intercorrencias", length = 3000)
    private String intercorrencias;

    @Column(name = "assinatura_paciente")
    private String assinaturaPaciente;

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