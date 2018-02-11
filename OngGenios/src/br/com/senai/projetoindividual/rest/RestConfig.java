package br.com.senai.projetoindividual.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.media.multipart.MultiPartFeature;

@ApplicationPath("rest")
public class RestConfig extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		
		Set<Class<?>> resources = new HashSet<Class<?>>();
		
		resources.add(UploadRest.class);
		resources.add(uploadGaleriaImagens.class);
		resources.add(DownloadRest.class);
		resources.add(DownloadGaleriaImagemRest.class);
		resources.add(MultiPartFeature.class);
		resources.add(PrioridadeRest.class);

		resources.add(BenefiarioRest.class);
		resources.add(BuscaMembroLogadoRest.class);
		resources.add(CategoriaRest.class);
		resources.add(DoacaoRest.class);
		resources.add(EventosRest.class);
		resources.add(MembroRest.class);
		resources.add(SonhoRest.class);
		resources.add(GaleriaRest.class);
		resources.add(ImagemRest.class);
		resources.add(FinanceiroRest.class);

		return resources;
	}
}
