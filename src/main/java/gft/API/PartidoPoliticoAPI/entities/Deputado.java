package gft.API.PartidoPoliticoAPI.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@DiscriminatorValue(value="Deputado")
public class Deputado extends Politico {

    private Boolean liderOuRepresentante;

    public Deputado(Long id, String nome, @CPF String cpf, Endereco endereco, String telefone, Partido partido,
                    String foto, List<ProjetoLei> projetos, List<Processo> processos, Boolean liderOuRepresentante) {
        super(id, nome, cpf, endereco, telefone, partido, foto, projetos, processos);
        this.liderOuRepresentante = liderOuRepresentante;
    }
}
