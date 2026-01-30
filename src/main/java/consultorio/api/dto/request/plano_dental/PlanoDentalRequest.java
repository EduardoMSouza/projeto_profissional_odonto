package consultorio.api.dto.request.plano_dental;// PlanoDentalRequest.java

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanoDentalRequest {

    @NotNull(message = "ID do paciente é obrigatório")
    private Long pacienteId;

    @NotNull(message = "ID do dentista é obrigatório")
    private Long dentistaId;

    @NotBlank(message = "Dente é obrigatório")
    @jakarta.validation.constraints.Size(min = 1, max = 2, message = "Dente deve ter até 2 caracteres")
    private String dente;

    @NotBlank(message = "Procedimento é obrigatório")
    @jakarta.validation.constraints.Size(max = 200, message = "Procedimento deve ter no máximo 200 caracteres")
    private String procedimento;

    @Digits(integer = 10, fraction = 2, message = "Valor deve ter no máximo 10 dígitos inteiros e 2 decimais")
    private BigDecimal valor;

    @Digits(integer = 10, fraction = 2, message = "Valor final deve ter no máximo 10 dígitos inteiros e 2 decimais")
    private BigDecimal valorFinal;

    private String observacoes;
}