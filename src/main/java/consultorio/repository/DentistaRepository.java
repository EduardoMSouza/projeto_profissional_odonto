// DentistaRepository.java
package consultorio.repository;

import consultorio.domain.dentista.Dentista;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DentistaRepository extends JpaRepository<Dentista, Long> {

    boolean existsByCro(String cro);

    boolean existsByEmail(String email);

    boolean existsByCroAndIdNot(String cro, Long id);

    boolean existsByEmailAndIdNot(String email, Long id);

    Optional<Dentista> findByCro(String cro);

    Optional<Dentista> findByEmail(String email);

    Page<Dentista> findByAtivoTrue(Pageable pageable);

    @Query("SELECT d FROM Dentista d WHERE d.ativo = true AND d.especialidade = :especialidade")
    Page<Dentista> findByAtivoTrueAndEspecialidade(String especialidade, Pageable pageable);
}