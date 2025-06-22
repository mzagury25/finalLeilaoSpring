
package repository;

import com.leilao.model.Lance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LanceRepository extends JpaRepository<Lance, Long> {

    // Find all bids for a specific auction
    List<Lance> findByLeilaoIdOrderByValorDesc(Long leilaoId);

    // Find all bids by a specific user
    List<Lance> findByUsuarioIdOrderByDataDesc(Long usuarioId);

    // Find winning bid for an auction
    @Query("SELECT l FROM Lance l WHERE l.leilao.id = :leilaoId AND l.valor = " +
            "(SELECT MAX(l2.valor) FROM Lance l2 WHERE l2.leilao.id = :leilaoId)")
    Optional<Lance> findLanceVencedor(@Param("leilaoId") Long leilaoId);

    // Find highest bid for an auction
    @Query("SELECT MAX(l.valor) FROM Lance l WHERE l.leilao.id = :leilaoId")
    Optional<Double> findMaiorLance(@Param("leilaoId") Long leilaoId);

    // Count number of bids for an auction
    long countByLeilaoId(Long leilaoId);

    // Find last bid for an auction
    Optional<Lance> findFirstByLeilaoIdOrderByDataDesc(Long leilaoId);

    // Find bids by value range
    List<Lance> findByLeilaoIdAndValorBetweenOrderByValorDesc(Long leilaoId,
                                                              Double valorMinimo,
                                                              Double valorMaximo);

    // Find bids by date range
    List<Lance> findByLeilaoIdAndDataBetweenOrderByDataDesc(Long leilaoId,
                                                            LocalDateTime inicio,
                                                            LocalDateTime fim);

    // Find all bids for a user in active auctions
    @Query("SELECT l FROM Lance l WHERE l.usuario.id = :usuarioId AND l.leilao.ativo = true " +
            "ORDER BY l.data DESC")
    List<Lance> findLancesUsuarioEmLeiloesAtivos(@Param("usuarioId") Long usuarioId);

    // Check if user has already bid in an auction
    boolean existsByLeilaoIdAndUsuarioId(Long leilaoId, Long usuarioId);

    // Find average bid value for an auction
    @Query("SELECT AVG(l.valor) FROM Lance l WHERE l.leilao.id = :leilaoId")
    Optional<Double> findMediaLances(@Param("leilaoId") Long leilaoId);

    // Find last N bids for an auction
    @Query("SELECT l FROM Lance l WHERE l.leilao.id = :leilaoId " +
            "ORDER BY l.data DESC LIMIT :quantidade")
    List<Lance> findUltimosLances(@Param("leilaoId") Long leilaoId,
                                  @Param("quantidade") int quantidade);

    // Find bids above a certain value
    List<Lance> findByLeilaoIdAndValorGreaterThanEqualOrderByValorDesc(Long leilaoId,
                                                                       Double valor);
}s