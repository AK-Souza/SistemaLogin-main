package models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;
import play.data.validation.Email;
import play.data.validation.Equals;
import play.data.validation.MinSize;
import play.data.validation.Required;
import play.db.jpa.Model;
import play.libs.Crypto;

@Entity
public class Usuario extends Model {
    @Required
	public String nome;
    @Required
    @Email
    public String email;
    @Required
    @Equals(value="confirmacaosenha", message ="A senha de confirmação não estão iguais")
    public String senha;
    @Transient
    public String confirmacaosenha;
    @Required
    public int cargo;
	
	public void setSenha(String s) {
		senha = Crypto.passwordHash(s);
	}

	public void setConfirmacaosenha(String s) {
		confirmacaosenha = Crypto.passwordHash(s);
	}
}
