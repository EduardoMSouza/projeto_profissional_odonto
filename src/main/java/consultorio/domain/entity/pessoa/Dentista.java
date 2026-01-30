package consultorio.domain.entity.pessoa;

import consultorio.domain.entity.agenda.Agendamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um dentista no sistema de consultório odontológico.
 * Um dentista pode ter múltiplos agendamentos associados.
 *
 * @author Sistema Consultório
 * @since 1.0
 */
@Entity
@Table(name = "dentistas", indexes = {
        @Index(name = "idx_dentista_email", columnList = "email_dentista"),
        @Index(name = "idx_dentista_cro", columnList = "cro_dentista"),
        @Index(name = "idx_dentista_ativo", columnList = "ativo"),
        @Index(name = "idx_dentista_especialidade", columnList = "especialidade_dentista")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Dentista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dentista")
    private Long id;

    @Column(name = "nome_dentista", nullable = false, length = 100)
    private String nome;

    @Column(name = "cro_dentista", nullable = false, unique = true, length = 10)
    private String cro;

    @Column(name = "especialidade_dentista", nullable = false, length = 50)
    private String especialidade;

    @Column(name = "telefone_dentista", nullable = false, length = 20)
    private String telefone;

    @Column(name = "email_dentista", nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private Boolean ativo = true;

    @CreationTimestamp
    @Column(name = "criado_em", updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;


    public void desativar() {
        this.ativo = false;
    }

    public void ativar() {
        this.ativo = true;
    }

    public boolean estaAtivo() {
        return Boolean.TRUE.equals(ativo);
    }

    public void setEmail(String email) {
        if (email != null) {
            this.email = email.trim().toLowerCase();
        }
    }

    public void setNome(String nome) {
        if (nome != null) {
            this.nome = nome.trim();
        }
    }

    public void setTelefone(String telefone) {
        if (telefone != null) {
            this.telefone = telefone.trim();
        }
    }

    public void setCro(String cro) {
        if (cro != null) {
            this.cro = cro.trim().toUpperCase();
        }
    }

    public void setEspecialidade(String especialidade) {
        if (especialidade != null) {
            this.especialidade = especialidade.trim();
        }
    }
}