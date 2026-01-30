package consultorio.domain.entity.pessoa;

import consultorio.domain.entity.pessoa.embedded.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Representa um paciente do consultório odontológico.
 * Contém todas as informações pessoais, médicas e odontológicas do paciente.
 *
 * @author Sistema Consultório
 * @since 1.0
 */
@Entity
@Table(name = "pacientes", indexes = {
        @Index(name = "idx_paciente_prontuario", columnList = "prontuario_numero", unique = true),
        @Index(name = "idx_paciente_cpf", columnList = "cpf", unique = true),
        @Index(name = "idx_paciente_telefone", columnList = "telefone_paciente"),
        @Index(name = "idx_paciente_nome", columnList = "nome_paciente"),
        @Index(name = "idx_paciente_status", columnList = "status_paciente"),
        @Index(name = "idx_paciente_ativo", columnList = "ativo")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("ativo = true")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paciente")
    private Long id;

    // Dados básicos do paciente
    @Embedded
    private DadosBasicos dadosBasicos;

    // Responsável pelo tratamento (para menores ou dependentes)
    @Embedded
    private Responsavel responsavel;

    // Anamnese (histórico médico)
    @Embedded
    private Anamnese anamnese;

    // Convênio médico
    @Embedded
    private Convenio convenio;

    // Inspeção bucal inicial
    @Embedded
    private InspecaoBucal inspecaoBucal;

    // Questionário de saúde
    @Embedded
    private QuestionarioSaude questionarioSaude;

    // Status e controle
    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    // Timestamps
    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizado_em", nullable = false)
    private LocalDateTime atualizadoEm;

    /**
     * Define o CPF removendo caracteres não numéricos.
     *
     * @param cpf CPF a ser formatado
     */
    public void setCpf(String cpf) {
        if (cpf != null) {
            dadosBasicos.setCpf(cpf.replaceAll("[^0-9]", ""));
        }
    }

    /**
     * Define o nome removendo espaços extras.
     *
     * @param nome nome do paciente
     */
    public void setNome(String nome) {
        if (nome != null) {
            dadosBasicos.setNome(nome.trim());
        }
    }

    /**
     * Método auxiliar para obter o nome do paciente
     */
    public String getNome() {
        return dadosBasicos != null ? dadosBasicos.getNome() : null;
    }

    /**
     * Método auxiliar para obter o CPF do paciente
     */
    public String getCpf() {
        return dadosBasicos != null ? dadosBasicos.getCpf() : null;
    }

    /**
     * Método auxiliar para obter o telefone do paciente
     */
    public String getTelefone() {
        return dadosBasicos != null ? dadosBasicos.getTelefone() : null;
    }

}