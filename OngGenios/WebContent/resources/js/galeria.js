
ONG.monta = new Object();

$(document).ready(function(){

	ONG.monta.galeriaEventos = function(){
	    	    
	   var busca = $("#consultarSonho").val();	
	   ONG.monta.montaGaleria(undefined, busca);
	}	

	ONG.monta.montaGaleria = function(listGaleria, busca){
		if(listGaleria != undefined && listGaleria.length > 0 && listGaleria[0].id != undefined){
			
			var html = "</br>"
				+ "<div class='col-lg-12 thumb'>"
			        + "<h1 class='page-header'> Galerias: </h1>"
			    +"</div>";

		 	for(var i = 0; i < listGaleria.length; i++){
			  html += "<div class='col-lg-3 col-md-4 col-xs-6 thumb'>";

			  		html += "<div class='thumb custom-galeria'>"; 
			  			html += "<a class='class='thumbnail' href='javascript:  ONG.monta.modal(" + listGaleria[i].id + ");''>";
						html += listGaleria[i].nome + "</a>";
					html += "</div>";
        			html += "<div class='thumb'>"; 
	        			html += "<img  class='img-responsive custom-imagem' src='" + ONG.contextPath + "/rest/download?file=" + listGaleria[i].nomeImagem +"'>";
					html += "</div>";
					html += "<div class='thumb'>"; 
						html += "<div> <h3>Informação da Galeria: </h3>" + listGaleria[i].informacaoGaleria + "</div>";
					html += "</div>";
				html += "</div>";		
		 	}
	  	}else{
		  	if(listGaleria == undefined || (listGaleria != undefined && listGaleria.length > 0)){
			  	if(busca == ""){
			  		busca = null;
				}

				var categoria = 2;

				var cfg = {
												
					url:  ONG.contextPath + "/rest/galeria/consultarGaleria/" + busca + "/" + categoria,
				
					success: function(listGaleria){

						
						ONG.monta.montaGaleria(listGaleria);
					},
					
					error: function(err){
						
						bootbox.alert("Erro ao consultar os contatos:" + err.responseText);
					}
				};
				
				ONG.ajax.get(cfg);
			}else{
				
				html+="<tr><td colspan='3'>Nenhum registro encontrado</td></tr>";
			}
	    }
		  
		$("#resultadoGaleria").html(html);
	}	

	ONG.monta.montaGaleria(undefined, "");

	ONG.monta.modal = function(id){
		$("#form").load("formularios/imagensGaleria.html", function(){
			$('#resultadoGaleria').html("");
			console.log("teste2");
			
		  	$("#id").val(id);		
		  	ONG.imagem.imagemDaGaleria();
		});
	}
});


