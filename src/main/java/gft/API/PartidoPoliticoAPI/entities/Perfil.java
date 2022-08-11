package gft.API.PartidoPoliticoAPI.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Perfil implements GrantedAuthority {
    @Id
    private Long id;

    private String nome;

    @Override
    public String getAuthority() {
        return getNome();
    }
}
