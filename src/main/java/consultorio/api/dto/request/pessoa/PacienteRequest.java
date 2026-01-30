// PacienteRequest.java
package consultorio.api.dto.request.pessoa;

import consultorio.api.dto.request.pessoa.embedded.paciente.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PacienteRequest {
    private DadosBasicosRequest dadosBasicos;
    private ResponsavelRequest responsavel;
    private AnamneseRequest anamnese;
    private ConvenioRequest convenio;
    private InspecaoBucalRequest inspecaoBucal;
    private QuestionarioSaudeRequest questionarioSaude;
    private String observacoes;
}