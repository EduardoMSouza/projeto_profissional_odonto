// QuestionarioSaudeRequest.java
package consultorio.api.dto.request.pessoa.embedded.paciente;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionarioSaudeRequest {
    private Boolean sofreDoenca = false;
    private String sofreDoencaQuais;
    private Boolean tratamentoMedicoAtual = false;
    private Boolean gravidez = false;
    private Boolean usoMedicacao = false;
    private String usoMedicacaoQuais;
    private String medicoAssistenteTelefone;
    private Boolean teveAlergia = false;
    private String teveAlergiaQuais;
    private Boolean foiOperado = false;
    private String foiOperadoQuais;
    private Boolean problemasCicatrizacao = false;
    private Boolean problemasAnestesia = false;
    private Boolean problemasHemorragia = false;
    private String habitos;
    private String antecedentesFamiliares;
}