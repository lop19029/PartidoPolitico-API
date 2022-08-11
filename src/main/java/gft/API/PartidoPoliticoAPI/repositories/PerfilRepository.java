package gft.API.PartidoPoliticoAPI.repositories;

import gft.API.PartidoPoliticoAPI.entities.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {
}
