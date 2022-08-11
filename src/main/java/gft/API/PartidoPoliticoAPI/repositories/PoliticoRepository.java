package gft.API.PartidoPoliticoAPI.repositories;

import gft.API.PartidoPoliticoAPI.entities.Politico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PoliticoRepository extends JpaRepository<Politico, Long> {

    Page<Politico> findByOrderByNomeAsc(Pageable pageable);

    Page<Politico> findByOrderByNomeDesc(Pageable pageable);

    @Query("SELECT p FROM Politico p WHERE SIZE(p.projetos) = ?1")
    Page<Politico> findPoliticosByNumeroProjetos(Integer numeroProjetos, Pageable pageable);

    @Query("SELECT p FROM Politico p WHERE cargo_politico LIKE ?1%")
    Page<Politico> findPoliticoByCargo(String cargo, Pageable pageable);

    @Query("SELECT p FROM Politico p WHERE id = ?1 AND cargo_politico LIKE ?2%")
    Optional<Politico> findByIdAndCargo(Long id, String cargo);

    @Query("SELECT p FROM Politico p WHERE partido_id = ?1 AND liderOuRepresentante = TRUE")
    Optional<Politico> findPartidoLider(Long partidoId);


}
