package consultorio.repository;

import consultorio.domain.paciente.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, String> {

    Optional<Paciente> findByProntuario(String prontuario);

    Optional<Paciente> findByCpf(String cpf);

    @Query("SELECT p FROM Paciente p WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Paciente> findByNomeContaining(@Param("nome") String nome);

    @Query("SELECT p FROM Paciente p WHERE p.telefone = :telefone")
    List<Paciente> findByTelefone(@Param("telefone") String telefone);

    boolean existsByProntuario(String prontuario);

    boolean existsByCpf(String cpf);

    @Query("SELECT p FROM Paciente p WHERE p.convenio = :convenio")
    List<Paciente> findByConvenio(@Param("convenio") String convenio);

    @Query("SELECT p FROM Paciente p WHERE LOWER(p.convenio) LIKE LOWER(CONCAT('%', :convenio, '%'))")
    List<Paciente> findByConvenioContaining(@Param("convenio") String convenio);
}
