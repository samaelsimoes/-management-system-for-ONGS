ONG.monta = new Object();

$(document).ready(function(){
	ONG.monta.buscaGaleria = function(){

		var busca;
		
		if(busca == ""){	
	  		busca = null;
		}

		var categoria = 2;

		var cfg = {
												
			url: ONG.contextPath + "/rest/galeria/consultarGaleria/" + busca + "/" + categoria,
		
			success: function(listGaleria){

				var dialog = bootbox.dialog({
			   
				    title: 'Carregando Galeria ',
				    message: '<p><i class="fa fa-spin fa-spinner"></i> Carregando...</p>'
				});

				dialog.init(function(){

				    setTimeout(function(){
				    	
				       dialog.find('.bootbox-body').html('Galeria carregadas com sucesso!');
				    }, 2000);
				});

				ONG.monta.selectGaleria(listGaleria);
			},
			
			error: function(err){
				
				bootbox.alert("Erro ao consultar os contatos:" + err.responseText);
			}
		};
		ONG.ajax.get(cfg);
	}

	ONG.monta.selectGaleria = function(listGaleria){

		if(listGaleria != undefined && listGaleria.length > 0 && listGaleria[0].id != undefined){
			for(var i = 0; i < listGaleria.length; i++){

				var option = $("<option></option>").appendTo($('#galeria'));
				option.attr("value", listGaleria[i].id);
				option.html( listGaleria[i].nome);
			}
		}  
	}

	ONG.monta.imagemDaGaleria = function(){
		
		var id_galeria=$("#galeria").val();
		var listaImagens=undefined;

		if(id_galeria!=null || id_galeria!=""){	
			ONG.monta.imagensGaleria(listaImagens, id_galeria);
		}else{
			bootbox.alert("Caro, por gentileza informar a Galeria que deseja ver as imagem");
		}
	}

	ONG.monta.modal = function(id, nome, informacao){
		$("#form").load("formularios/formImagensGaleria.html", function(){

			$('#resultadoGaleria').html("");

		  	$("#id").val(id);
		  	$("#result").html(""); 
			$("<img class='custom-imagem2'>").attr("src",ONG.contextPath +"/rest/DownloadGaleriaImagem?file="+nome).appendTo("#result");
		  	$('#informacaoImagem').val(informacao);
		});
	}

	ONG.monta.imagensGaleria=function(listaImagens, id_galeria){
		
		var html = "</br>";

		if(listaImagens!=undefined && listaImagens.length > 0 && listaImagens[0].id!=undefined){

		 	for(var i = 0; i < listaImagens.length; i++){

        		html += "<div class='col-lg-3 col-md-4 col-xs-6 thumb'>";


        			html += "<div class='thumb'>"; 
	        			html += "<a class='class='thumbnail' href='javascript:  ONG.monta.modal(" + listaImagens[i].id + ',"' +  listaImagens[i].nome + '","' +  listaImagens[i].informacaoImagem + '"' +");''>";
	        				html += "<img  class='img-responsive custom-imagem' src='" + ONG.contextPath + "/rest/DownloadGaleriaImagem?file=" + listaImagens[i].nome +"'>";
						html +="</a>";
					html += "</div>";

					html += "<div class='thumb '>";
        			
        				html += "<div> " + " Informação: " + listaImagens[i].informacaoImagem + "</div>";
					html += "</div>";

					html +=  "<button type = 'button' class='btn btn-pencil' onclick='ONG.monta.modal(" + listaImagens[i].id + ',"' +  listaImagens[i].nome + '","' +  listaImagens[i].informacaoImagem + '"' +")'>Editar</button>";
				    html +=  "<button type = 'button'class='btn btn-trash' onclick='ONG.monta.confirmaExcluir("+listaImagens[i].id+")'>Excluir</button>" ;
				html += "</div>";	
		 	}
	  	}else{
		  	if(listaImagens == undefined || (listaImagens != undefined && listaImagens.length > 0)){
		  		if(id_galeria != "" && id_galeria != null && id_galeria != undefined){
					var cfg = {
													
						url:  ONG.contextPath + "/rest/imagem/consultarImagem/" + id_galeria,
					
						success: function(listaImagens){

							ONG.monta.imagensGaleria(listaImagens);
						},
						
						error: function(err){
							
							bootbox.alert("Erro ao consultar os contatos:" + err.responseText);
						}
					};
					
					ONG.ajax.get(cfg);
				}else{

					bootbox.alert("Caro usuário, por gentileza selecionar uma galeria para ser listado as imagens");
				}
			}else{
				
				html += "<h3>Nenhum registro encontrado</h3>";
			}
	    }
		$("#resultadoGaleria").html(html);
	}


	ONG.monta.validaVaziu = function(campo, valor){

		var msg = "";

		if(valor == null ||  valor.trim() == ""){

			msg += " O campo: " + campo + " Está Vazio. </br>";
		}

		return msg;
	};

	ONG.monta.editarImagemGaleria = function(){

		var valdInf = ONG.monta.validaVaziu("Informação: " , $("#informacaoImagem").val());

		var id = $("#id").val();
		var msg = "";
		
		if(valdInf != "" && valdInf != null){
			
			msg += valdInf;
		}
		
		var newCont = new Object();

    	newCont.informacaoImagem = $("#informacaoImagem").val();
    	newCont.id = $("#id").val();

		if(msg == "" || msg == null ){

			bootbox.confirm({

			    message: "Caro Usuário você selecionou uma nova imagem ? ",

			    buttons: {
			        confirm: {
			            label: 'Sim',
			            className: 'btn-success',
			        },
			        cancel: {
			            label: 'Não',
			            className: 'btn-danger'
			        }
			    },
		    
			    callback: function (result) {
			    	if(result == true){	
  						
			    		var nome_arquivo = $("#fileArquivo").val();
				    					    	
			    		if(nome_arquivo != "" && nome_arquivo != undefined){

			    			newCont.id = $("#id").val();
					        var formData = new FormData($("#formulario")[0]);

					        if($("#result") != null || $("#result") != ""){

								$.ajax({

									url: ONG.contextPath + "/rest/upload",
									type: "POST",
									data: formData,
									contentType: false,
									processData: false,

									success: function(data){

										newCont.nome = data;

										if(data != "" & data != null){
											
											$("#result").html("");
											$("<img class='custom-imagem2'>").attr("src",ONG.contextPath +"/rest/download?file="+data).appendTo("#result");
											
											ONG.monta.editandoImagemGaleria(newCont);
										}
									},
									error: function(err){
									
									bootbox.alert("Erro ao tentar subir imagem para o servidor, entrar em contato com o Administrador!");
									}
								});
							}
						}else if(nome_arquivo == null || nome_arquivo == '' || nome_arquivo == undefined){

							bootbox.alert("Nenhuma imagem selecionada ! ");
							return false;
						}
					}else{
						
						ONG.monta.editandoImagemGaleria(newCont);
			    	}
			    }
			});	
		}else{

			bootbox.alert(msg);
		}
	};

	ONG.monta.editandoImagemGaleria = function(newCont){

		if(newCont != undefined || newCont != "" & newCont != null ){
			
			$.ajax({
			
				type: 'PUT',
				url:ONG.contextPath + "/rest/imagem/editarPorId/",
				data: JSON.stringify(newCont),
				
				dataType:'text',
				contentType:'application/json',
				
				success:function(data){
					
					bootbox.alert(data);
				},		
				error: function(err){
					
					bootbox.alert( err.responseText); 
				}
			});
		}else{

			bootbox.alert(" Dados de editar nulo ");
		}
	}

	ONG.monta.confirmaExcluir = function(id){

		bootbox.confirm({

		    message: "Você Desejea Excluir este Evento?",
		    buttons: {
		        
		        confirm: {
		            label: 'Sim',
		            className: 'btn-success',
		        },
		        
		        cancel: {
		            label: 'Não',
		            className: 'btn-danger'
		        }
		    },
		    
		    callback: function (result) {

		        if(result == true){		        	
		           var cfg = {

						url:  ONG.contextPath + "/rest/imagem/inativar/" + id,
						success: function (data){
							
							bootbox.alert(data);
						
							var intervalo = window.setInterval(function() {
			            	}, 50);
			            	
			            	window.setTimeout(function() {

			            	    ONG.monta.imagensGaleria();		

			            	}, 3000);
						},
				
						error: function (err){
							
							bootbox.alert("Erro ao deletar está galeria: " + err.responseText);
						}
					};
					
					ONG.ajax.delet(cfg);
		        }
		    }
		});
	}
});


