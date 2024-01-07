package models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Livro extends Model{
	@Required
    public String nome;
	@Required
    public String codigo;
	@Required
    public String preco;
    public String descricao;
    
    @ManyToOne
    public Estante estante;
}
