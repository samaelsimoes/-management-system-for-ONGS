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

import br.com.senai.projetoindividual.objeto.Beneficiario;
import br.com.senai.projetoindividual.service.BeneficiarioService;

@Path("/beneficiarios")
public class BenefiarioRest extends UtilRest {
	
	@POST//
	@Path("/adicionar")	
	@Consumes(MediaType.APPLICATION_JSON)
	
	public Response adicionar(String json) throws Exception{

		try{
						
			BeneficiarioService service = new BeneficiarioService();
			ObjectMapper mapper = new ObjectMapper();
			mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
			
			Beneficiario beneficiario =  mapper.readValue(json, Beneficiario.class);
			
			beneficiario.setMembro(getLoguedUser());
			
			service.adicionar(beneficiario);
			
			return this.buildResponse("Beneficiario cadastrado com Sucesso !");
		}catch(Exception e){
			
			e.printStackTrace();
			return this.buildErrorResponse("Ocorreu o seguinte erro " + e.getMessage());
		}
	}
	
	@GET
	@Path("/consultarNome/{nome}")
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response consultarNome(@PathParam("nome")String nome )throws Exception{		
		
		try{

			BeneficiarioService service = new BeneficiarioService();
			List<Beneficiario> bene = service.consultarNome(nome);
			return this.buildResponse(bene);
		}catch(Exception e){
			
			e.printStackTrace();
			return this.buildErrorResponse(" Ocorreu o seguinte erro " + e.getMessage());
		}
	}
	
	@GET
	@Path("/consultarPorId/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response consultarPorId(@PathParam("id") int id){
		
		try{
			
			BeneficiarioService service = new BeneficiarioService();				
			Beneficiario bene = service.consultarPorId(id);
			
			return buildResponse(bene);
		}catch(Exception e){
			
			e.printStackTrace();
			return this.buildErrorResponse(" Ocorreu o seguinte erro " + e.getMessage());
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
			
			BeneficiarioService service = new BeneficiarioService();
			Beneficiario beneficiario = mapper.readValue(json, Beneficiario.class);
			service.editar(beneficiario);
			
			return this.buildResponse("Beneficiarios Editado com Sucesso !");
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
			
			BeneficiarioService service = new BeneficiarioService();
			service.inativar(id);
			
			return this.buildResponse("Beneficiarios Inativado com Sucesso !");
		}catch(Exception e){
			
			e.printStackTrace();
			return this.buildErrorResponse(" Ocorreu o seguinte erro " + e.getMessage());
		}
	}
}
