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
import br.com.senai.projetoindividual.objeto.Evento;
import br.com.senai.projetoindividual.objeto.Membro;
import br.com.senai.projetoindividual.service.BeneficiarioService;
import br.com.senai.projetoindividual.service.CategoriaService;
import br.com.senai.projetoindividual.service.DoacaoService;
import br.com.senai.projetoindividual.service.EventoService;

@Path("/categoria")
public class CategoriaRest extends UtilRest {
	
	@POST
	@Path("/adicionar")
	@Consumes("application/*")
	public Response adicionar(String json)throws Exception{
		try{
			CategoriaService service = new CategoriaService();
			
			ObjectMapper mapper = new ObjectMapper();
			Categoria categoria =  mapper.readValue(json, Categoria.class);
			service.adicionar(categoria);
			
			return this.buildResponse(" Cadastro Realizado com sucesso");
		}catch(Exception e ){
			
			e.printStackTrace();
			return this.buildErrorResponse(" Ocorreu o seguinte erro no cadastro destá prioridade " + e.getMessage());
		}
	}
	
	@GET//
	@Path("/buscar")	
	@Consumes("application/*")
	public Response buscar()throws Exception {
		try{
						
			CategoriaService catService = new CategoriaService();
			List<Categoria> categoria = catService.buscar();
			
			return this.buildResponse(categoria);
		}catch(Exception e){
			
			e.printStackTrace();
			return this.buildErrorResponse("<br> <br> Ocorreu o seguinte erro " + e.getMessage());
		}		
	}
	@GET
	@Path("/consultarPorId/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response consultarPorId(@PathParam("id") int id){
		try{
			
			CategoriaService service = new CategoriaService();				
			Categoria categoria= service.consultarPorId(id);
			
			return buildResponse(categoria);
		}catch(Exception e){
			
			e.printStackTrace();
			return this.buildErrorResponse(" Ocorreu o seguinte erro " + e.getMessage());
		}
	}
	
	
	@POST
	@Path("/editarCategoria")	
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response editar(String json)throws Exception {
		
		try{
			
			ObjectMapper mapper = new ObjectMapper();
			Categoria cate= mapper.readValue(json, Categoria.class);
			CategoriaService service = new CategoriaService();
			service.editar(cate);
			
			return this.buildResponse("Categoria Editado com sucesso!");
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
			
			CategoriaService categoria = new CategoriaService();
			categoria.inativar(id);
			
			return this.buildResponse("Categoria Inativado com Sucesso !");
		}catch(Exception e){
			
			e.printStackTrace();
			return this.buildErrorResponse(" Ocorreu o seguinte erro " + e.getMessage());
		}
	}
}
