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

import br.com.senai.projetoindividual.objeto.Doacao;
import br.com.senai.projetoindividual.service.DoacaoService;

@Path("/doacao")
public class DoacaoRest extends UtilRest {

	@POST
	@Path("/adicionar")	
	@Consumes("application/*")
	public Response adicionar(String json)throws Exception {
		
		try{ 

			DoacaoService service = new DoacaoService();
			ObjectMapper mapper = new ObjectMapper();
			
			Doacao doacao =  mapper.readValue(json, Doacao.class);
			service.adicionar(doacao);
						
			return this.buildResponse(" Doacao Cadastrada com Sucesso ");
		}catch(Exception e){
			
			e.printStackTrace();
			return this.buildErrorResponse(" Ocorreu o seguinte erro " + e.getMessage());
		}		
	}
	
	@GET
	@Path("/consultarDoacao/{nome}/{categoria}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultarDoacao(@PathParam("nome")String nome, @PathParam("categoria")String categoria)throws Exception{
		
		try{
			DoacaoService doacaoServ = new DoacaoService();
			List<Doacao> doacao = doacaoServ.consultarNome(nome, categoria);
			return this.buildResponse(doacao);		
		}catch(Exception e){
			e.printStackTrace();
			return this.buildErrorResponse(" Ocorreu o seguinte erro "+ e.getMessage());
		}
	}	

	@GET
	@Path("/consultarPorId/{id}")	
	@Consumes("application/*")	
	public Response consultarPorId(@PathParam("id") int id){

		try{
			DoacaoService doacaoService = new DoacaoService();
			Doacao doacao = doacaoService.consultarPorId(id);
			return buildResponse(doacao);
		}catch(Exception e){

			e.printStackTrace();
			return this.buildErrorResponse(" Ocorreu o seguinte erro " + e.getMessage());
		}
	}	

	@POST
	@Path("/editarDoacao")	
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response editarDoacao(String json)throws Exception {
				
		try{
						
			ObjectMapper mapper = new ObjectMapper();
			Doacao doacao = mapper.readValue(json, Doacao.class);
			DoacaoService service = new DoacaoService();
			service.editar(doacao);

			return this.buildResponse(" Doação Editado com Sucesso !");
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

			DoacaoService doacao = new DoacaoService();
			doacao.inativar(id);

			return this.buildResponse(" Doação excluida com Sucesso !");
		}catch(Exception e){

			e.printStackTrace();
			return this.buildErrorResponse(" Ocorreu o seguinte erro " + e.getMessage());
		}
	}
}
