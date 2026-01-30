package consultorio.dto.response.evolcao_tratamento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvolucaoTratamentoResponse {

    private String id;
    private Long pacienteId;
    private String pacienteNome;
    private LocalDate data;
    private String evolucao;
    private String intercorrencias;
    private String assinaturaPaciente;
    private LocalDate criadoEm;
    private LocalDate atualizadoEm;
}