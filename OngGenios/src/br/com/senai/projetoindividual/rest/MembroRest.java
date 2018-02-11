package br.com.senai.projetoindividual.rest;

import java.text.SimpleDateFormat;
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

import br.com.senai.projetoindividual.objeto.Membro;
import br.com.senai.projetoindividual.service.MembroService;

@Path("/membros")
public class MembroRest extends UtilRest {
	
	@POST//
	@Path("/adicionar")	
	@Consumes("application/*")
	public Response adicionar(String json) throws Exception{
				
		try{
						
			MembroService service = new MembroService();
			ObjectMapper mapper = new ObjectMapper();
			
			mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
			Membro membro =  mapper.readValue(json, Membro.class);
			service.adicionar(membro);
			
			return this.buildResponse("Membro cadastrado com Sucesso !");
		}catch(Exception e){
			
			e.printStackTrace();
			return this.buildErrorResponse("Ocorreu o seguinte erro: " + e.getMessage());
		}
	}
	
	@GET
	@Path("/consultarNome/{nome}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultarNome(@PathParam("nome")String nome )throws Exception{		
		
		try{
			
			MembroService service = new MembroService();
			List<Membro> memb = service.consultarNome(nome);
			
			return this.buildResponse(memb);
		}catch(Exception e){
			
			e.printStackTrace();
			return this.buildErrorResponse("Ocorreu o seguinte erro: " + e.getMessage());
		}
	}
	
	@GET
	@Path("/consultarPorId/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response consultarPorId(@PathParam("id") int id){
		
		try{
			
			MembroService service = new MembroService();					
			Membro memb = service.consultarPorId(id);
			
			return buildResponse(memb);
		}catch(Exception e){
			
			e.printStackTrace();
			return this.buildErrorResponse("Ocorreu o seguinte erro " + e.getMessage());
		}
	}
	
	@PUT
	@Path("/editar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	
	public Response editar(String json) throws Exception{
		
		try{
									
			ObjectMapper mapper = new ObjectMapper();
			mapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));	
			
			MembroService service = new MembroService();
			Membro membro = mapper.readValue(json, Membro.class);
			service.editar(membro);
			
			return this.buildResponse("Membro Editado com Sucesso !");
		}catch(Exception e){
			e.printStackTrace();
			return this.buildErrorResponse("Ocorreu o seguinte erro " + e.getMessage());
		}
	}
	
	@DELETE
	@Path("/inativar/{id}")
	@Consumes("application/*")
	
	public Response inativar(@PathParam("id") int id){
		
		try{
			
			MembroService service = new MembroService();
			service.inativar(id);
			
			return this.buildResponse("Membro Inativado com Sucesso !");
		}catch(Exception e){
			
			e.printStackTrace();
			return this.buildErrorResponse("Ocorreu o seguinte erro " + e.getMessage());
		}
	}
}
