package consultorio.dto.request.paciente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PacienteRequest {

    @NotBlank(message = "Prontuário é obrigatório")
    private String prontuario;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    private String telefone;
    private String rg;
    private String orgaoExpedidor;
    private String cpf;

    @Past(message = "Data de nascimento deve ser no passado")
    private LocalDate dataNascimento;

    private String sexo;
    private String naturalidade;
    private String nacionalidade;
    private String estadoCivil;
    private String profissao;
    private String enderecoResidencial;
    private String indicadoPor;
    private String convenio;
    private String numeroInscricao;

    // Responsável
    private String responsavelNome;
    private String responsavelRg;
    private String responsavelOrgaoExpedidor;
    private String responsavelCpf;
    private String responsavelEstadoCivil;
    private String responsavelConjuge;
    private String responsavelRgConjuge;
    private String responsavelOrgaoExpedidorConjuge;
    private String responsavelCpfConjuge;

    // Anamnese
    private Boolean febreReumatica;
    private Boolean hepatite;
    private Boolean diabetes;
    private Boolean hipertensaoArterial;
    private Boolean portadorHiv;
    private Boolean alteracaoCoagulacao;
    private Boolean reacoesAlergicas;
    private Boolean doencasSystemicas;
    private String tratamentosMedicosAnteriores;
    private Boolean internacaoRecente;
    private Boolean utilizandoMedicacao;
    private String medicacaoNome;
    private String medicacaoQuantidade;
    private String medicacaoTempo;
    private Boolean bebidasAlcoolicas;
    private Boolean problemasCardiacos;
    private Boolean problemasRenais;
    private Boolean problemasGastricos;
    private Boolean problemasRespiratorios;
    private Boolean problemasAlergicos;
    private Boolean problemasArticulares;
    private String queixaPrincipal;

    // Questionário
    private Boolean sofreDoenca;
    private String doencaQual;
    private Boolean emTratamentoMedico;
    private Boolean gravidez;
    private Boolean fazendoUsoMedicacao;
    private String medicacaoQual;
    private String medicoAssistente;
    private String medicoTelefone;
    private Boolean temAlergia;
    private String alergiaQual;
    private Boolean foiOperado;
    private String operacaoQual;
    private Boolean problemasCicatrizacao;
    private Boolean problemasAnestesia;
    private Boolean problemasHemorragia;
    private String habitos;
    private String antecedentesFamiliares;

    // Inspeção
    private String lingua;
    private String mucosa;
    private String palato;
    private String labios;
    private String gengivas;
    private String nariz;
    private String face;
    private String ganglios;
    private String glandulasSalivares;
    private Boolean alteracaoOclusao;
    private String alteracaoOclusaoTipo;
    private Boolean protese;
    private String proteseTipo;
    private String outrasObservacoes;
}
