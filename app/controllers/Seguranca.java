package controllers;

import play.mvc.Before;
import models.Estante;
import models.Livro;
import play.mvc.Controller;

public class Seguranca extends Controller{
	@Before (unless= {"Livros.listar"})
	public static void verificar() {
		if(session.contains("usuario.email") == false) {
			Login.form();
		}
	}
}
