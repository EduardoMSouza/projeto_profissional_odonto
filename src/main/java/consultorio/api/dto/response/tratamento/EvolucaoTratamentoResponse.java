package consultorio.api.dto.response.tratamento;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvolucaoTratamentoResponse {

    private Long id;
    private Long pacienteId;
    private String pacienteNome;
    private Long dentistaId;
    private String dentistaNome;
    private LocalDate data;
    private String evolucaoEIntercorrencias;
}
