package gft.API.PartidoPoliticoAPI.repositories;

import gft.API.PartidoPoliticoAPI.entities.Partido;
import gft.API.PartidoPoliticoAPI.entities.Politico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartidoRepository extends JpaRepository<Partido, Long> {

    Page<Partido> findAll(Pageable pageable);

    Optional<Partido> findBySigla(String sigla);
}
