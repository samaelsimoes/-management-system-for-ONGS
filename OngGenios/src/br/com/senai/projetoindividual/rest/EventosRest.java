package br.com.senai.projetoindividual.rest;

import java.text.SimpleDateFormat;
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

import br.com.senai.projetoindividual.objeto.Evento;
import br.com.senai.projetoindividual.service.EventoService;

@Path("/eventos")
public class EventosRest extends UtilRest{

	@POST
	@Path("/adicionar")	
	@Consumes("application/*")
	public Response adicionar(String json)throws Exception {
		
		try{
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
			Evento evento =  mapper.readValue(json, Evento.class);		
			EventoService eventoserv = new EventoService();
			eventoserv.adicionar(evento);
			
			return this.buildResponse(" Cadastrado Evento com Sucesso ");
		}catch(Exception e){

			e.printStackTrace();
			return this.buildErrorResponse(" Ocorreu o seguinte erro " + e.getMessage());
		}
	}
	
	@GET
	@Path("/consultarEvento/{nome}/{categoria}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultarEvento(@PathParam("nome")String nome , @PathParam("categoria")String categoria)throws Exception{
	
		try{
			
			EventoService service = new EventoService();
			List<Evento> event = service.consultarNome(nome,categoria);
			
			return this.buildResponse(event);
		}catch(Exception e){
			
			e.printStackTrace();
			return this.buildErrorResponse(" Ocorreu o seguinte erro " + e.getMessage());
		}
	}
	
	@GET
	@Path("/consultarPorId/{id}/{categoria}")	
	@Consumes("application/*")	
	public Response consultarPorId(@PathParam("id") int id , @PathParam("categoria")String categoria){
		
		try{
						
			EventoService service = new EventoService();
			Evento event = service.consultarPorId(id,categoria);

			return this.buildResponse(event);
		}catch(Exception e){
			
			e.printStackTrace();
			return this.buildErrorResponse(" Ocorreu o seguinte erro " + e.getMessage());
		}
	}
	
	@POST
	@Path("/editarEvento")	
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response editarEvento(String json)throws Exception {
		
		try{
			
			ObjectMapper mapper = new ObjectMapper();
			Evento evento= mapper.readValue(json, Evento.class);
			EventoService service = new EventoService();
			service.editar(evento);
			
			return this.buildResponse("Evento Editado com sucesso!");
		}catch(Exception e){
			
			e.printStackTrace();
			return this.buildErrorResponse(" Ocorreu o seguinte erro " + e.getMessage());
		}
	}
	
	@DELETE
	@Path("/inativar/{id}")
	@Consumes("application/*")
	public Response inativar(@PathParam("id") int id){
		
		try{
			
			EventoService evento = new EventoService();
			evento.inativar(id);
			
			return this.buildResponse(" Evento excluida com Sucesso !");
		}catch(Exception e){
	
			e.printStackTrace();
			return this.buildErrorResponse(" Ocorreu o seguinte erro " + e.getMessage());
		}
	}
}
