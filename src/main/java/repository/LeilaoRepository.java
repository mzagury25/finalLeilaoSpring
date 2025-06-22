package repository;

import com.leilao.model.Leilao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LeilaoRepository extends JpaRepository<Leilao, Long> {

    // Find active auctions
    List<Leilao> findByAtivo(boolean ativo);

    // Find auctions by status
    List<Leilao> findByStatus(String status);

    // Find active auctions that are currently in progress
    @Query("SELECT l FROM Leilao l WHERE l.ativo = true AND l.dataInicio <= :now AND l.dataFim >= :now")
    List<Leilao> findLeiloesEmAndamento(@Param("now") LocalDateTime now);

    // Find upcoming auctions
    @Query("SELECT l FROM Leilao l WHERE l.ativo = true AND l.dataInicio > :now")
    List<Leilao> findLeiloesProximos(@Param("now") LocalDateTime now);

    // Find finished auctions
    @Query("SELECT l FROM Leilao l WHERE l.ativo = true AND l.dataFim < :now")
    List<Leilao> findLeiloesEncerrados(@Param("now") LocalDateTime now);

    // Find auctions by creator
    List<Leilao> findByUsuarioCriacao(Long usuarioCriacao);

    // Find auctions by object
    List<Leilao> findByObjetoId(Long objetoId);

    // Search auctions by title or description
    @Query("SELECT l FROM Leilao l WHERE LOWER(l.titulo) LIKE LOWER(CONCAT('%', :termo, '%')) " +
            "OR LOWER(l.descricao) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Leilao> buscarPorTituloOuDescricao(@Param("termo") String termo);

    // Find auctions with minimum bid count
    @Query("SELECT l FROM Leilao l WHERE l.ativo = true AND SIZE(l.lances) >= :minLances")
    List<Leilao> findLeiloesComMinimoDeLances(@Param("minLances") int minLances);

    // Find auctions by price range
    @Query("SELECT l FROM Leilao l WHERE l.valorInicial BETWEEN :minValor AND :maxValor")
    List<Leilao> findByFaixaDePreco(@Param("minValor") Double minValor, @Param("maxValor") Double maxValor);

    // Find auctions ending soon (within next 24 hours)
    @Query("SELECT l FROM Leilao l WHERE l.ativo = true AND l.dataFim BETWEEN :now AND :tomorrow")
    List<Leilao> findLeiloesTerminandoEmBreve(@Param("now") LocalDateTime now,
                                              @Param("tomorrow") LocalDateTime tomorrow);
}