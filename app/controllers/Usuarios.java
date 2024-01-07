package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import models.Usuario;
import play.cache.Cache;
import play.data.validation.Valid;
import play.mvc.Controller;
import play.mvc.With;

@With(Seguranca.class)
public class Usuarios extends Controller {
	
	public static void form() {
		Usuario usuario = (Usuario)Cache.get("usuario");
		render();
	}
	
	public static void salvar(Usuario usuario, String senha) {
		Usuario usu = Usuario.find("nome = ?1 and email = ?2", usuario.nome, usuario.email).first();
		if(usu != null) {
			flash.error("Usuário já cadastrado");
			form();
		}
		
		
		
		if(senha.equals("")== false || usuario.id == null) {
			usuario.senha = senha;
			if (senha.length()<6) {
				validation.addError("usuario.senha", "A senha deve conter pelo menos 6 dígitos");
			}
		}

		validation.valid(usuario);
		
		if(senha.equals("") && usuario.id != null) {
			validation.removeErrors("usuario.senha");
			if(validation.errors().size() == 1) {
				validation.clear();
			}
		}
		
		if (validation.hasErrors()) {
			validation.keep();
			flash.error("Falha ao cadastrar usuário");
			Cache.set("usuario", usuario);
			form();
		}

		usuario.save();
		flash.success("Usuário cadastrado");
		listar();
	}
	
	public static void editar(Long id) {
		Usuario usuario = Usuario.findById(id);
		renderTemplate("Usuarios/form.html", usuario);
	}
	
	public static void remover(Long id) {
		Usuario usuario = Usuario.findById(id);
		usuario.delete();
		listar();
	}
	
	public static void listar() {
		List<Usuario> usuarios = Usuario.findAll();
		render(usuarios);
	}

	public static void listarAjax(String busca) {
		List<Usuario> usuarios;
		if (busca == null || busca.trim().isEmpty()) {
			usuarios = Usuario.findAll();
		} 
        else {
			usuarios = Usuario.find("lower(nome) like ?1 or lower(email) like ?1 "
					+ "order by nome ", "%" + busca.toLowerCase() + "%").fetch();
		}
		renderJSON(usuarios);
	}
}
