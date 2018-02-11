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

import br.com.senai.projetoindividual.objeto.Galeria;
import br.com.senai.projetoindividual.objeto.Imagem;
import br.com.senai.projetoindividual.service.GaleriaService;
import br.com.senai.projetoindividual.service.ImagemGaleriaService;

@Path("/imagem")

public class ImagemRest extends UtilRest {
	
	@POST
	@Path("/adicionar")	
	@Consumes(MediaType.APPLICATION_JSON)
	public Response adicionar(String json)throws Exception {
		
		try{
			
			ObjectMapper mapper = new ObjectMapper();
			Imagem imagem =  mapper.readValue(json, Imagem.class);		
			
			ImagemGaleriaService imagemService = new ImagemGaleriaService();
			imagemService.cadastrarImagens(imagem);
			
			return this.buildResponse("<br> Imagem Cadastrada com Sucesso ");
		}catch (Exception e) {
			
			e.printStackTrace();
			return this.buildErrorResponse("<br> <br> Ocorreu o seguinte erro " + e.getMessage());
		}
	}
	
	@GET
	@Path("/consultarImagem/{id_galeria}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultarImagem( @PathParam("id_galeria")int id_galeria)throws Exception{
		
		try{
			
			ImagemGaleriaService galeriaImagem = new ImagemGaleriaService();
			List<Imagem> imagem = galeriaImagem.consultarImagem(id_galeria);
			
			return this.buildResponse(imagem);
		}catch(Exception e){
			
			e.printStackTrace();
			return this.buildErrorResponse("<br> <br> Ocorreu o seguinte erro " + e.getMessage());
		}
	}
	
	@PUT
	@Path("/editarPorId")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response editarPorId(String json)throws Exception {
		
		try{
			
			ObjectMapper mapper = new ObjectMapper();
			Imagem imagem = mapper.readValue(json, Imagem.class);

			ImagemGaleriaService imagemService = new ImagemGaleriaService();
			imagemService.editar(imagem);
			
			return this.buildResponse("</br> Imagem da Galeria Editada com Sucesso !");
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
			
			ImagemGaleriaService imagem = new ImagemGaleriaService();
			imagem.inativar(id);
			
			return this.buildResponse("</br> Imagem Inativado com Sucesso !");
		}catch (Exception e) {
			
			e.printStackTrace();
			return this.buildErrorResponse("<br> <br> Ocorreu o seguinte erro " + e.getMessage());		}
	}
}
