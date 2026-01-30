package consultorio.domain.entity.pessoa;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long pacienteId;

    private Long dentistaId;

    @Column(name = "data", nullable = false)
    private LocalDate data;

    @Column(name = "evolucao_e_intercorrencias", columnDefinition = "TEXT")
    private String evolucaoEIntercorrencias;

}