
package repository;

import com.leilao.model.Usuario;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Find user by email
    Usuario findByEmail(String email);

    // Check if user exists by email
    boolean existsByEmail(String email);

    // Find user by username
    Usuario findByUsername(String username);

    // Check if user exists by username
    boolean existsByUsername(String username);

    // Find user by email and status
    Usuario findByEmailAndAtivo(String email, boolean ativo);
}