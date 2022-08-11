package gft.API.PartidoPoliticoAPI.repositories;

import gft.API.PartidoPoliticoAPI.entities.Processo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessoRepository extends JpaRepository<Processo, Long> {
}
