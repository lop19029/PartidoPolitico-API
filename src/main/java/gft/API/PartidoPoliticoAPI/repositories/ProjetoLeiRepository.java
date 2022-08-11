package gft.API.PartidoPoliticoAPI.repositories;

import gft.API.PartidoPoliticoAPI.entities.ProjetoLei;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjetoLeiRepository extends JpaRepository<ProjetoLei, Long> {
}
