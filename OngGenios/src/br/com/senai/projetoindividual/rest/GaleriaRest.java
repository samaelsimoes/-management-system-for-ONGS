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

import br.com.senai.projetoindividual.exception.OngException;
import br.com.senai.projetoindividual.objeto.Beneficiario;
import br.com.senai.projetoindividual.objeto.Doacao;
import br.com.senai.projetoindividual.objeto.Financeiro;
import br.com.senai.projetoindividual.objeto.Galeria;
import br.com.senai.projetoindividual.objeto.Imagem;
import br.com.senai.projetoindividual.objeto.Sonho;
import br.com.senai.projetoindividual.service.BeneficiarioService;
import br.com.senai.projetoindividual.service.DoacaoService;
import br.com.senai.projetoindividual.service.GaleriaService;

import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/galeria")

public class GaleriaRest extends UtilRest {
	
	@POST
	@Path("/adicionar")	
	@Consumes(MediaType.APPLICATION_JSON)
	public Response adicionar(String json)throws Exception {
		
		try{
		
			ObjectMapper mapper = new ObjectMapper();
			Galeria galeria =  mapper.readValue(json, Galeria.class);		
			GaleriaService galeriaService = new GaleriaService();
	
			galeriaService.cadastrar(galeria);
			return this.buildResponse("<br> Imagem Cadastrada com Sucesso ");

		}catch (Exception e) {
			
			e.printStackTrace();
			return this.buildErrorResponse("<br> <br> Ocorreu o seguinte erro " + e.getMessage());
		}
	}
	
	@GET
	@Path("/consultarGaleria/{nome}/{categoria}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultarGaleria(@PathParam("nome")String nome , @PathParam("categoria")String categoria)throws Exception{
	
		try{
			
			GaleriaService service = new GaleriaService();
			List<Galeria> event = service.consultarGaleria(nome,categoria);
			
			return this.buildResponse(event);
		}catch(Exception e){
			
			e.printStackTrace();
			return this.buildErrorResponse("<br> <br> Ocorreu o seguinte erro " + e.getMessage());
		}
	}
	
	// ===========================================================================================================================================================================================================
   // ========================================================================================================================================================================================================		
	
	@GET
	@Path("/consultaPorId/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response consultaPorId(@PathParam("id") int id) throws OngException{
				
		try{

			GaleriaService galeriaService = new GaleriaService();
			Galeria galeria = galeriaService.consultarPorId(id);

			return buildResponse(galeria);
		}catch(Exception e){

			e.printStackTrace();
			return this.buildErrorResponse("<br> <br> Ocorreu o seguinte erro " + e.getMessage());
		}
	}
	
	@PUT
	@Path("/editarGaleriaPorId")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response editarGaleriaPorId(String json)throws Exception {
		
		try{

			ObjectMapper mapper = new ObjectMapper();
			Galeria galeria = mapper.readValue(json, Galeria.class);
			GaleriaService galeriaservice = new GaleriaService();
			galeriaservice.editar(galeria);
			
			return this.buildResponse("</br> Galeria Editada com Sucesso !");
		}catch(Exception e ){
			
			e.printStackTrace();
			return this.buildErrorResponse("<br> <br> Ocorreu o seguinte erro " + e.getMessage());
		}
	}
	
	@DELETE
	@Path("/inativar/{id}")
	@Consumes("application/*")
	public Response inativar(@PathParam("id") int id){
		
		try{
			
			GaleriaService galeria = new GaleriaService();
			galeria.inativar(id);
			
			return this.buildResponse("</br> Membro Inativado com Sucesso !");
		}catch (Exception e) {
			
			e.printStackTrace();
			return this.buildErrorResponse("<br> <br> Ocorreu o seguinte erro " + e.getMessage());		}
	}
}
