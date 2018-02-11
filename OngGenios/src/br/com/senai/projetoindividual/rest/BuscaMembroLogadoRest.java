package br.com.senai.projetoindividual.rest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import br.com.senai.projetoindividual.objeto.Membro;

@Path("/membroLogado")
public class BuscaMembroLogadoRest extends UtilRest{

	@GET//
	@Path("/buscar")	
	@Consumes("application/*")
	public Response buscar(String json)throws Exception {
		try{
			
			Membro membro = getLoguedUser();

			return this.buildResponse(membro);
		}catch(Exception e){
			
			e.printStackTrace();
			return this.buildErrorResponse("<br> <br> Ocorreu o seguinte erro " + e.getMessage());
		}		
	}
}
