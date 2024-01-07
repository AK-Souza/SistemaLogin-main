package models;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Estante extends Model {
	@Required
    public String setor;
	@Required
    public String carreira;
	@Required
    public String categoria;

    @OneToMany(mappedBy="estante")
    public List<Livro> livros;

    public long qtdLivros() {
        return livros.size();
    }
}
