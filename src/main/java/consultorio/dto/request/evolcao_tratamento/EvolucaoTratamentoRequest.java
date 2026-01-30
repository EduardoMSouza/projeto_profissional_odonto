package consultorio.dto.request.evolcao_tratamento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvolucaoTratamentoRequest {

    @NotNull(message = "ID do paciente é obrigatório")
    @Positive(message = "ID do paciente deve ser positivo")
    private Long pacienteId;

    @NotBlank(message = "Nome do paciente é obrigatório")
    private String pacienteNome;

    @NotNull(message = "Data é obrigatória")
    private LocalDate data;

    @NotBlank(message = "Evolução é obrigatória")
    private String evolucao;

    private String intercorrencias;

    private String assinaturaPaciente;
}