package controllers;

import models.Usuario;
import play.libs.Crypto;
import play.mvc.Controller;

import models.Estante;
import models.Livro;

public class Login extends Controller {
	
	public static void form(){
		render();
	}
	public static void logar(String email, String senha) {
		Usuario usu= Usuario.find("email = ?1 and senha = ?2",  email,Crypto.passwordHash(senha)).first();
		if(usu == null) {
			flash.error("Login ou senha inválidos");
			Login.form();
		}
			session.put("usuario.nome", usu.nome);	
			session.put("usuario.email", usu.email);
			session.put("usuario.cargo", usu.cargo);
             Livros.listar(null);		
	}

	public static void sair() {
		session.clear();
		Login.form();
	}
}
