package consultorio.domain.paciente;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "pacientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String prontuario;

    @Column(nullable = false)
    private String nome;

    private String telefone;

    @Column(name = "rg")
    private String rg;

    @Column(name = "orgao_expedidor")
    private String orgaoExpedidor;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    private String sexo;

    private String naturalidade;

    private String nacionalidade;

    @Column(name = "estado_civil")
    private String estadoCivil;

    private String profissao;

    @Column(name = "endereco_residencial", length = 500)
    private String enderecoResidencial;

    @Column(name = "indicado_por")
    private String indicadoPor;

    private String convenio;

    @Column(name = "numero_inscricao")
    private String numeroInscricao;

    // Responsável pelo tratamento
    @Column(name = "responsavel_nome")
    private String responsavelNome;

    @Column(name = "responsavel_rg")
    private String responsavelRg;

    @Column(name = "responsavel_orgao_expedidor")
    private String responsavelOrgaoExpedidor;

    @Column(name = "responsavel_cpf")
    private String responsavelCpf;

    @Column(name = "responsavel_estado_civil")
    private String responsavelEstadoCivil;

    @Column(name = "responsavel_conjuge")
    private String responsavelConjuge;

    @Column(name = "responsavel_rg_conjuge")
    private String responsavelRgConjuge;

    @Column(name = "responsavel_orgao_expedidor_conjuge")
    private String responsavelOrgaoExpedidorConjuge;

    @Column(name = "responsavel_cpf_conjuge")
    private String responsavelCpfConjuge;

    // Ficha de anamnese - Dados de saúde geral
    @Column(name = "febre_reumatica")
    private Boolean febreReumatica;

    @Column(name = "hepatite")
    private Boolean hepatite;

    @Column(name = "diabetes")
    private Boolean diabetes;

    @Column(name = "hipertensao_arterial")
    private Boolean hipertensaoArterial;

    @Column(name = "portador_hiv")
    private Boolean portadorHiv;

    @Column(name = "alteracao_coagulacao")
    private Boolean alteracaoCoagulacao;

    @Column(name = "reacoes_alergicas")
    private Boolean reacoesAlergicas;

    @Column(name = "doencas_sistemicas")
    private Boolean doencasSystemicas;

    @Column(name = "tratamentos_medicos_anteriores", length = 1000)
    private String tratamentosMedicosAnteriores;

    @Column(name = "internacao_recente")
    private Boolean internacaoRecente;

    @Column(name = "utilizando_medicacao")
    private Boolean utilizandoMedicacao;

    @Column(name = "medicacao_nome")
    private String medicacaoNome;

    @Column(name = "medicacao_quantidade")
    private String medicacaoQuantidade;

    @Column(name = "medicacao_tempo")
    private String medicacaoTempo;

    @Column(name = "bebidas_alcoolicas")
    private Boolean bebidasAlcoolicas;

    @Column(name = "problemas_cardiacos")
    private Boolean problemasCardiacos;

    @Column(name = "problemas_renais")
    private Boolean problemasRenais;

    @Column(name = "problemas_gastricos")
    private Boolean problemasGastricos;

    @Column(name = "problemas_respiratorios")
    private Boolean problemasRespiratorios;

    @Column(name = "problemas_alergicos")
    private Boolean problemasAlergicos;

    @Column(name = "problemas_articulares")
    private Boolean problemasArticulares;

    // Queixa principal
    @Column(name = "queixa_principal", length = 2000)
    private String queixaPrincipal;

    // Questionário de saúde
    @Column(name = "sofre_doenca")
    private Boolean sofreDoenca;

    @Column(name = "doenca_qual")
    private String doencaQual;

    @Column(name = "em_tratamento_medico")
    private Boolean emTratamentoMedico;

    @Column(name = "gravidez")
    private Boolean gravidez;

    @Column(name = "fazendo_uso_medicacao")
    private Boolean fazendoUsoMedicacao;

    @Column(name = "medicacao_qual")
    private String medicacaoQual;

    @Column(name = "medico_assistente")
    private String medicoAssistente;

    @Column(name = "medico_telefone")
    private String medicoTelefone;

    @Column(name = "tem_alergia")
    private Boolean temAlergia;

    @Column(name = "alergia_qual")
    private String alergiaQual;

    @Column(name = "foi_operado")
    private Boolean foiOperado;

    @Column(name = "operacao_qual")
    private String operacaoQual;

    @Column(name = "problemas_cicatrizacao")
    private Boolean problemasCicatrizacao;

    @Column(name = "problemas_anestesia")
    private Boolean problemasAnestesia;

    @Column(name = "problemas_hemorragia")
    private Boolean problemasHemorragia;

    @Column(name = "habitos", length = 1000)
    private String habitos;

    @Column(name = "antecedentes_familiares", length = 1000)
    private String antecedentesFamiliares;

    // Inspeção da boca e face
    private String lingua;
    private String mucosa;
    private String palato;
    private String labios;
    private String gengivas;
    private String nariz;
    private String face;
    private String ganglios;

    @Column(name = "glandulas_salivares")
    private String glandulasSalivares;

    @Column(name = "alteracao_oclusao")
    private Boolean alteracaoOclusao;

    @Column(name = "alteracao_oclusao_tipo")
    private String alteracaoOclusaoTipo;

    @Column(name = "protese")
    private Boolean protese;

    @Column(name = "protese_tipo")
    private String proteseTipo;

    @Column(name = "outras_observacoes", length = 2000)
    private String outrasObservacoes;

    @Column(name = "criado_em")
    private LocalDate criadoEm;

    @Column(name = "atualizado_em")
    private LocalDate atualizadoEm;

    @PrePersist
    protected void onCreate() {
        criadoEm = LocalDate.now();
        atualizadoEm = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        atualizadoEm = LocalDate.now();
    }
}