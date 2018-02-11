package br.com.senai.projetoindividual.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.senai.projetoindividual.rest.util.FileUtil;
import br.com.senai.projetoindividual.rest.util.FileUtilGaleriaImagens;

@Path("DownloadGaleriaImagem")
public class DownloadGaleriaImagemRest {

	@GET
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response download(@QueryParam("file") String fileName){
		
		return Response.ok(FileUtilGaleriaImagens.getFile(fileName)).build();
	}
}
