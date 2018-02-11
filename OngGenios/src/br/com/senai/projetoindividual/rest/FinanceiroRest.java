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

import br.com.senai.projetoindividual.objeto.Categoria;
import br.com.senai.projetoindividual.objeto.Doacao;
import br.com.senai.projetoindividual.objeto.Evento;
import br.com.senai.projetoindividual.objeto.Financeiro;
import br.com.senai.projetoindividual.service.EventoService;
import br.com.senai.projetoindividual.service.FinanceiroService;

@Path("/financeiro")
public class FinanceiroRest extends UtilRest {
	
	@POST
	@Path("/depositar")	
	@Consumes("application/*")
	public Response adicionar(String json)throws Exception {	
		try{
			ObjectMapper mapper = new ObjectMapper();
			mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
			Financeiro finan =  mapper.readValue(json, Financeiro.class);
			FinanceiroService finanService = new FinanceiroService();
			finanService.depositar(finan);
			return this.buildResponse("<br> Depositado com Sucesso ");
		}catch(Exception e){
			
			e.printStackTrace();
			return this.buildErrorResponse("<br> <br> Ocorreu o seguinte erro " + e.getMessage());
		}
	}

	@POST
	@Path("/sacar")	
	@Consumes("application/*")
	public Response sacar(String json)throws Exception {
		try{
			ObjectMapper mapper = new ObjectMapper();
			mapper.setDateFormat(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"));
			Financeiro finan =  mapper.readValue(json, Financeiro.class);
			FinanceiroService finanService = new FinanceiroService();
			finanService.saque(finan);
			return this.buildResponse("<br> Saque realizado com Sucesso ");
		}catch(Exception e){
			e.printStackTrace();
			return this.buildErrorResponse("<br> <br> Ocorreu o seguinte erro " + e.getMessage());
		}
	}

	@GET
	@Path("/buscar/{categoria}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscar(@PathParam("categoria")String categoria)throws Exception{		
		try{
			FinanceiroService financeiroService = new FinanceiroService();
			List<Financeiro> financeiro = financeiroService.buscar(categoria);
			return this.buildResponse2(financeiro);
		}catch(Exception e){
			e.printStackTrace();
			return this.buildErrorResponse("<br> <br> Ocorreu o seguinte erro " + e.getMessage());
		}
	}
	
	@GET
	@Path("/buscarPorId/{id}/{tipo}")
	@Consumes("application/*")
	public Response buscarPorId(@PathParam("id") int id, @PathParam("tipo") String tipo){
		try{
			FinanceiroService finanService = new FinanceiroService();
			Financeiro finan = finanService.consultarPorID(id,tipo);
			
			return buildResponse(finan);
		}catch(Exception e){
			e.printStackTrace();
			return this.buildErrorResponse("Ocorreu o seuginte erro " + e.getMessage());
		}
	}
	
	@GET
	@Path("/listatotal/{categoria}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listaTotal(@PathParam("categoria")String categoria)throws Exception {
		try{
			
			FinanceiroService finaServi = new FinanceiroService();			
			double financeiro = finaServi.listaTotal(categoria);
			
			return this.buildResponse2(financeiro);
		}catch(Exception e){		
			e.printStackTrace();
			return this.buildErrorResponse(" Ocorreu o seguinte erro ao listar total no financeiro " + e.getMessage());
		}
	}
	
	@DELETE
	@Path("/inativar/{id}")
	@Consumes("application/*")
	public Response inativar(@PathParam("id") int id){	
		try{	
			FinanceiroService evento = new FinanceiroService();
			evento.inativar(id);
			return this.buildResponse("</br> excluido com Sucesso !");
		}catch(Exception e){
			e.printStackTrace();
			return this.buildErrorResponse("<br> <br> Ocorreu o seguinte erro " + e.getMessage());
		}
	}
	
	@POST
	@Path("/editar")	
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response editar(String json)throws Exception {
		
		try{
			ObjectMapper mapper = new ObjectMapper();
			Financeiro finan= mapper.readValue(json, Financeiro.class);
			FinanceiroService service = new FinanceiroService();
			service.editar(finan);
			
			return this.buildResponse(" Editado com sucesso!");
		}catch(Exception e){
			
			e.printStackTrace();
			return this.buildErrorResponse(" Ocorreu o seguinte erro " + e.getMessage());
		}
	}
}
