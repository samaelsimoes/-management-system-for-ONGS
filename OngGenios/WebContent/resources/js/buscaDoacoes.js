ONG.contato = new Object();

$(document).ready(function(){

	ONG.contato.montaTabelaDoacao = function(listaDoacao){

		if(listaDoacao != undefined && listaDoacao.length > 0 && listaDoacao[0].id != undefined){
			
			var html = "<p> Doações</p> </br><table class='table'>";
			html += "<tr> </br> <th> Doação </th><th> categoria </th></tr>";	
	  		
		 	for(var i = 0; i < listaDoacao.length; i++){
			  
			  html += "<tr>";

				  html += "<td>" + listaDoacao[i].doacao + "</td>";
				  html += "<td>" + listaDoacao[i].categoria.tipoCategoria + "</td>";
				  html += "<td>";
			  html += "</tr>";  
		 	}
	  	}else{
		  	if(listaDoacao == undefined || (listaDoacao != undefined && listaDoacao.length > 0)){
				
		  	  	
				var categoria = "";
		  		var valorBusca = "";

			  	if(valorBusca == ""){					
					valorBusca = null;
				}
			  
			  	if(categoria == ""){					
					categoria = null;
				}	 
			  	
				var cfg ={
												
					url:  ONG.contextPath + "/rest/doacao/consultarDoacao/" + valorBusca + "/" + categoria,
				
					success: function(listaDoacao){												
						ONG.contato.montaTabelaDoacao(listaDoacao);
					},
					error: function(err){						
						bootbox.alert("Erro ao consultar os contatos:"+err.responseText);
					}
				};
				
				ONG.ajax.get(cfg);
			}else{
				
				html += "<tr><td colspan='3'>Nenhum registro encontrado</td></tr>";
			}
	  }
		  
		  html += "</table>";
		  $("#resultadoDoacoes").html(html);
	}
	
	ONG.contato.montaTabelaDoacao(undefined, "");
});





