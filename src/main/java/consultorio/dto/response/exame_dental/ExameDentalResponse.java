package consultorio.dto.response.exame_dental;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExameDentalResponse {

    private String id;
    private Long pacienteId;
    private String pacienteNome;
    private LocalDate dente;
    private String procedimento;
    private BigDecimal valor;
    private String observacoes;
    private BigDecimal valorTotal;
    private LocalDate criadoEm;
    private LocalDate atualizadoEm;
}