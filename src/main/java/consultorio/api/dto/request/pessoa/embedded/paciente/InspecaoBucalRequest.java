// InspecaoBucalRequest.java
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
public class InspecaoBucalRequest {
    private String lingua;
    private String mucosa;
    private String palato;
    private String labios;
    private String gengivas;
    private String nariz;
    private String face;
    private String ganglios;
    private String glandulasSalivares;
    private Boolean alteracaoOclusao = false;
    private String alteracaoOclusaoTipo;
    private Boolean protese = false;
    private String proteseTipo;
    private String outrasObservacoes;
}