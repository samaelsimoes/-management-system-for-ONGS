package br.com.senai.projetoindividual.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.senai.projetoindividual.objeto.Beneficiario;
import br.com.senai.projetoindividual.objeto.Categoria;
import br.com.senai.projetoindividual.objeto.Doacao;
import br.com.senai.projetoindividual.objeto.Prioridade;
import br.com.senai.projetoindividual.service.CategoriaService;
import br.com.senai.projetoindividual.service.DoacaoService;
import br.com.senai.projetoindividual.service.PrioridadeService;

@Path("/prioridade")
public class PrioridadeRest extends UtilRest{
	
	@POST
	@Path("/adicionar")	
	@Consumes("application/*")
	public Response adicionar(String json)throws Exception {
		
		try{ 
			
			PrioridadeService service = new PrioridadeService();
			ObjectMapper mapper = new ObjectMapper();
			Prioridade prioridade =  mapper.readValue(json, Prioridade.class);
			service.adicionar(prioridade);
						
			return this.buildResponse("<br> Prioridade Cadastrada com Sucesso ");
		}catch(Exception e){
			
			e.printStackTrace();
			return this.buildErrorResponse("<br> <br> Ocorreu o seguinte erro " + e.getMessage());
		}		
	}
	
	@GET
	@Path("/buscarprioridade/{valorBusca}")	
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarprioridade(@PathParam("valorBusca")String valorBusca){
		try{
			
			PrioridadeService prioridade = new PrioridadeService();
			List<Prioridade> prioService = prioridade.consultar();
		
			return this.buildResponse(prioService);
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
			
			PrioridadeService prioridade = new PrioridadeService();
			prioridade.inativar(id);
			
			return this.buildResponse("Prioridade Inativado com Sucesso !");
		}catch(Exception e){
			
			e.printStackTrace();
			return this.buildErrorResponse(" Ocorreu o seguinte erro " + e.getMessage());
		}
	}
	
	@POST
	@Path("/editar")	
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response editar(String json)throws Exception {
		
		try{
			
			ObjectMapper mapper = new ObjectMapper();
			Prioridade prio= mapper.readValue(json, Prioridade.class);
			PrioridadeService service = new PrioridadeService();
			service.editar(prio);
			
			return this.buildResponse("Prioridade Editado com sucesso!");
		}catch(Exception e){
			
			e.printStackTrace();
			return this.buildErrorResponse(" Ocorreu o seguinte erro " + e.getMessage());
		}
	}
	
	@GET
	@Path("/buscarid/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarid(@PathParam("id") int id){
		try{
			
			PrioridadeService service = new PrioridadeService();				
			Prioridade prioridade = service.consultarPorId(id);
			
			return buildResponse(prioridade);
		}catch(Exception e){
			
			e.printStackTrace();
			return this.buildErrorResponse(" Ocorreu o seguinte erro " + e.getMessage());
		}
	}
}
