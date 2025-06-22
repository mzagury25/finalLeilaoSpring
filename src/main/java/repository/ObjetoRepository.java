package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.leilao.model.Objeto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface ObjetoRepository extends JpaRepository<Objeto, Long> {

    // Find by name
    List<Objeto> findByNome(String nome);

    // Find by name containing (case insensitive)
    List<Objeto> findByNomeContainingIgnoreCase(String nome);

    // Find by active status
    List<Objeto> findByAtivo(boolean ativo);

    // Find by name and active status
    List<Objeto> findByNomeAndAtivo(String nome, boolean ativo);

    // Custom query to search objects by name or description
    @Query("SELECT o FROM Objeto o WHERE LOWER(o.nome) LIKE LOWER(CONCAT('%', :termo, '%')) " +
            "OR LOWER(o.descricao) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Objeto> buscarPorNomeOuDescricao(@Param("termo") String termo);

    // Count objects by active status
    long countByAtivo(boolean ativo);

    // Find objects created by a specific user
    List<Objeto> findByUsuarioCriacao(Long usuarioId);

    // Find active objects ordered by name
    @Query("SELECT o FROM Objeto o WHERE o.ativo = true ORDER BY o.nome")
    List<Objeto> findAllAtivosOrdenadosPorNome();
}