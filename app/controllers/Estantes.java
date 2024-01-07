package controllers;

import java.util.List;



import models.Estante;
import models.Livro;
import play.cache.Cache;
import play.data.validation.Valid;
import play.mvc.Controller;
import play.mvc.With;
@With(Seguranca.class)

public class Estantes extends Controller{
    public static void form() {
		Estante est = (Estante)Cache.get("est");
    	Cache.clear();
		
		
		render( est);
	}
    public static void salvar(@Valid Estante est){
    	Estante es = Estante.find("categoria = ?1 and setor = ?2", est.categoria, est.setor).first();
		if(es != null) {
			flash.error("Estante já cadastrada");
			form();
		}
    	if(validation.hasErrors()) {
			validation.keep();
			flash.error("Falha ao salvar o livro");
			Cache.set("est", est);
			form();
		}
		est.save();
		flash.success("Estante cadastrada");
		form();
	}
    public static void listar(){
        List<Estante> lista = Estante.findAll();
        render (lista);
    }
    public static void listaAjax(String busca) {
    	 List<Estante> lista;
 		if (busca == null) {
 			lista = Estante.findAll();
 		} else {
 			lista = Estante.find("setor like ?1 or carreira like ?1 or categoria like ?1  order by setor ", "%" + busca + "%").fetch();
 		}
         renderJSON(lista);
	}

    public static void deletar(Long id){
        Estante est = Estante.findById(id);
		est.delete();
		listar();
    }

    public static void editar(Long id){
        Estante est = Estante.findById(id);
		renderTemplate("Estantes/form.html", est);
    }
}
