ONG.imagem = new Object();

$(document).ready(function(){
	ONG.imagem.imagemDaGaleria = function(){
				
		var id_galeria = $("#id").val();
		ONG.imagem.imagensGaleria(undefined, id_galeria);
	}

	ONG.imagem.imagensGaleria = function(listaImagens, id_galeria){
		if(listaImagens!=undefined && listaImagens.length > 0 && listaImagens[0].id!=undefined){

			var html="</br>"
				+ "<div class='col-lg-12 thumb'>"
			        + "<h1 class='page-header'> Imagens da Galeria:  </h1>"
			    +"</div>";				

		 	for(var i=0; i < listaImagens.length; i++){

        		html+="<div class='col-lg-3 col-md-4 col-xs-6 thumb'>";
        			/*html+="<div class='thumb'>";
        				html+="<div class='thumb'>" +listaImagens[i].nomeGaleria.nome+"</div>";
        			html+="</div>";*/
        			html+="<div class='thumb'>"; 
	        			html+="<img  class='img-responsive custom-imagem' src='"+ONG.contextPath+"/rest/DownloadGaleriaImagem?file="+listaImagens[i].nome+"'>";
					html+="</div>";
					html+="<div class='thumb'>"; 
						html+="<div> <h3>Informação da Imagem: </h3>"+listaImagens[i].informacaoImagem+"</div>";
					html+="</div>";
				html+="</div>";	
		 	}
	  	}else{
		  		if(id_galeria != "" && id_galeria != null && id_galeria != undefined){

					var cfg = {
													
						url:  ONG.contextPath + "/rest/imagem/consultarImagem/" + id_galeria,
					
						success: function(listaImagens){

							ONG.imagem.imagensGaleria(listaImagens);
						},
						
						error: function(err){
							
							bootbox.alert("Erro ao consultar os contatos:" + err.responseText);
						}
					};
					
					ONG.ajax.get(cfg);W				
			}else{
				
				html += "<tr><td colspan='3'>Nenhum registro encontrado</td></tr>";
			}
	    }
		$("#resultadoImagens").html(html);
	}	

	ONG.imagem.imagensGaleria(undefined, "");
	
});


