package gft.API.PartidoPoliticoAPI.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorColumn(name="cargoPolitico",discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value="Politico")
public abstract class Politico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    @CPF(message = "CPF inválido.")
    @Column(unique = true)
    private String cpf;

    @Embedded
    private Endereco endereco;
    private String telefone;

    @JsonManagedReference
    @ManyToOne
    private Partido partido;
    private String foto;

    @JsonBackReference
    @OneToMany(mappedBy = "politico", cascade = CascadeType.REMOVE)
    private List<ProjetoLei> projetos;

    @JsonBackReference
    @OneToMany(mappedBy = "politico", cascade = CascadeType.REMOVE)
    private List<Processo> processos;

    @Transient
    public String getFotoPath() {
        if (foto == null || id == null) {
            return null;
        }
        else if (foto.equals("default.png")){
            return "C:/PartidoPoliticoAPI/politicos-fotos/default.png";
        }
        else { return "C:/PartidoPoliticoAPI/politicos-fotos/" + id + "/" + foto;}
    }

    public Politico(Long id) {
        this.id = id;
    }
}
