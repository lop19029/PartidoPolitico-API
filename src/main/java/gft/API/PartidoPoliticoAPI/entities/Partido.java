package gft.API.PartidoPoliticoAPI.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Partido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String sigla;

    @JsonBackReference
    @OneToMany(mappedBy = "partido", cascade = CascadeType.REMOVE)
    private List<Politico> politicos;

    public Partido(Long id) {
        this.id = id;
    }
}
