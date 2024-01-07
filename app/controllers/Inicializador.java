package controllers;

import models.Estante;
import models.Usuario;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

@OnApplicationStart
public class Inicializador extends Job{
	public void doJob() {
		if(Usuario.count() == 0) {
			Fixtures.loadModels("adm.yml");
		}
		if(Estante.count() == 0) {
			Fixtures.loadModels("estante.yml");
		}	
	}
}
