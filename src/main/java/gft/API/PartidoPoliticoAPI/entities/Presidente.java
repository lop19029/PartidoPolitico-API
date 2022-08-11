package gft.API.PartidoPoliticoAPI.entities;

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
@DiscriminatorValue(value="Presidente")
public class Presidente extends Politico{
    public Presidente(Long id, String nome, @CPF(message = "CPF inválido.") String cpf,
                      Endereco endereco, String telefone, Partido partido, String foto,
                      List<ProjetoLei> projetos, List<Processo> processos) {
        super(id, nome, cpf, endereco, telefone, partido, foto, projetos, processos);
    }
}
