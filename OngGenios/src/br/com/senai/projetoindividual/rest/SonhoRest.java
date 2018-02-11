package br.com.senai.projetoindividual.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.senai.projetoindividual.objeto.Sonho;
import br.com.senai.projetoindividual.service.SonhoService;

@Path("/sonhos")
public class SonhoRest extends UtilRest{

	@POST//
	@Path("/adicionar")	
	@Consumes("application/*")
	public Response adicionar(String json) throws Exception{
		try{
			
			SonhoService service = new SonhoService();
			ObjectMapper mapper = new ObjectMapper();
			Sonho sonho =  mapper.readValue(json, Sonho.class);
			
			service.adicionar(sonho);
			
			return this.buildResponse("Sonho cadastrado com Sucesso !");
		}catch(Exception e){
			
			e.printStackTrace();
			return this.buildErrorResponse("<br> <br> Ocorreu o seguinte erro " + e.getMessage());
		}
	}
	
	@GET
	@Path("/consultarSonho/{busca}/{prioridade}")
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response consultarSonho(@PathParam("busca")String busca, @PathParam("prioridade")String prioridade)throws Exception{		
		try{
			
			String valor = "";
								
			if(prioridade.equals("0")){
				
				valor+=null;
			}else if(!prioridade.equals("0")){
				valor+=prioridade;
			}

			SonhoService service = new SonhoService();
			List<Sonho> bene = service.consultarSonho(busca,valor);
			return this.buildResponse(bene);
		}catch(Exception e){
			
			e.printStackTrace();
			return this.buildErrorResponse("<br> <br> Ocorreu o seguinte erro " + e.getMessage());
		}
	}
	
	@GET
	@Path("/consultarPorId/{id}")	
	@Consumes("application/*")	
	public Response consultarPorId(@PathParam("id") int id){
		try{
			
			SonhoService sonhoService = new SonhoService();
			Sonho sonho = sonhoService.consultarPorId(id);
			
			return buildResponse(sonho);
		}catch(Exception e){

			e.printStackTrace();
			return this.buildErrorResponse("<br> <br> Ocorreu o seguinte erro " + e.getMessage());
		}		
	}
	
	@PUT
	@Path("/editar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response editar(String json) throws Exception{
		try{

			ObjectMapper mapper = new ObjectMapper();
			Sonho sonho = mapper.readValue(json, Sonho.class);
			SonhoService sonhoService = new SonhoService();
			
			sonhoService.editar(sonho);
			
			return this.buildResponse(" Sonho Editado com Sucesso !");
		}catch(Exception e){
			e.printStackTrace();
			return this.buildErrorResponse("<br> <br> Ocorreu o seguinte erro " + e.getMessage());
		
		}
	}
	
	@DELETE
	@Path("/inativar/{id}")
	@Consumes("application/*")
	
	public Response inativar(@PathParam("id") int id){
		try{
			
			SonhoService service = new SonhoService();
			service.inativar(id);
			
			return this.buildResponse(" Sonho Inativado com Sucesso !");
		}catch(Exception e){
			
			e.printStackTrace();
			return this.buildErrorResponse("<br> <br> Ocorreu o seguinte erro " + e.getMessage());
		}
	}
}
