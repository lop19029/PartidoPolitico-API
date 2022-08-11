package gft.API.PartidoPoliticoAPI.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.stereotype.Service;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;

@Entity
@Service
@Getter
@NoArgsConstructor
@DiscriminatorValue(value="Governador")
public class Governador extends Politico {
    public Governador(Long id, String nome, @CPF(message = "CPF inválido.") String cpf,
                      Endereco endereco, String telefone, Partido partido, String foto, List<ProjetoLei> projetos,
                      List<Processo> processos) {
        super(id, nome, cpf, endereco, telefone, partido, foto, projetos, processos);
    }
}
