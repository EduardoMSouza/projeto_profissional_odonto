package consultorio.api.dto.request.tratamento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvolucaoTratamentoRequest {

    @NotNull(message = "ID do paciente é obrigatório")
    private Long pacienteId;

    @NotNull(message = "ID do dentista é obrigatório")
    private Long dentistaId;

    @NotNull(message = "Data é obrigatória")
    private LocalDate data;

    @NotBlank(message = "Evolução e intercorrências é obrigatório")
    private String evolucaoEIntercorrencias;
}
