package controllers;

import java.util.List;

import models.Estante;
import models.Livro;
import models.Usuario;
import play.cache.Cache;
import play.data.validation.Valid;
import play.mvc.Controller;
import play.mvc.With;
@With(Seguranca.class)

public class Livros extends Controller{
    public static void form() {
    	Livro liv = (Livro)Cache.get("liv");
    	Cache.clear();
		
		List<Estante> estantes = Estante.findAll();
		render(liv, estantes);
	}

	public static void salvar(@Valid Livro liv) {
		Livro li = Livro.find("nome = ?1 and codigo = ?2", liv.nome, liv.codigo).first();
		if(li != null) {
			flash.error("Livro já cadastrado");
			form();
		}
		if(validation.hasErrors()) {
			validation.keep();
			flash.error("Falha ao salvar o livro");
			Cache.set("liv", liv);
			form();
		}
	
		liv.save();
		flash.success("Livro cadastrado");
		form();
	}

	public static void deletar(long id) {
		Livro liv = Livro.findById(id);
		liv.delete();
		flash.success("Livro removido");
		listar(null);
	}

	public static void editar(long id) {
		Livro liv = Livro.findById(id);
		List<Estante> estantes = Estante.findAll();
		renderTemplate("Livros/form.html", liv, estantes);
	}

	public static void listar(String busca) {
		List<Livro> lista;
		if (busca == null) {
			lista = Livro.findAll();
		} 
        else {
			lista = Livro.find("nome like ?1 or codigo like ?1 order by nome ", "%" + busca + "%").fetch();
		}
        render (lista);
	}

	public static void listarPorEstante(Long id, String busca) {
		List<Livro> lista;
		if (busca == null) {
			lista = Livro.find("estante_id like ?1", id).fetch();
		} 
		else {
			lista = Livro.find("lower(nome) like ?1 or codigo like ?1 and estante_id like ?2 order by nome ", "%" + busca.toLowerCase() + "%", id).fetch();
		}
		renderTemplate("Livros/listar.html", lista, busca, id);
	}
}
