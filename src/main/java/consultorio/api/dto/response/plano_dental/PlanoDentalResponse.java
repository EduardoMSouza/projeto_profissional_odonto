// PlanoDentalResponse.java
package consultorio.api.dto.response.plano_dental;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanoDentalResponse {

    private Long id;
    private Long pacienteId;
    private String pacienteNome;
    private Long dentistaId;
    private String dentistaNome;
    private String dente;
    private String procedimento;
    private BigDecimal valor;
    private BigDecimal valorFinal;
    private String observacoes;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}