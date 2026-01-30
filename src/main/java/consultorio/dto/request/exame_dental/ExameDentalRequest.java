package consultorio.dto.request.exame_dental;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExameDentalRequest {

    @NotNull(message = "ID do paciente é obrigatório")
    @Positive(message = "ID do paciente deve ser positivo")
    private Long pacienteId;

    @NotBlank(message = "Nome do paciente é obrigatório")
    private String pacienteNome;

    @NotNull(message = "Data do dente é obrigatória")
    private LocalDate dente;

    @NotBlank(message = "Procedimento é obrigatório")
    private String procedimento;

    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "Valor deve ser positivo")
    private BigDecimal valor;

    private String observacoes;

    private BigDecimal valorTotal;
}
