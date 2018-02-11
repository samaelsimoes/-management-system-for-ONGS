
ONG.monta = new Object();

$(document).ready(function(){
	ONG.monta.galeriaEventos = function(){	    
	   var busca = $("#consultarSonho").val();	
	   ONG.monta.montaGaleria(undefined, busca);
	}	

	ONG.monta.montaGaleria = function(listGaleria, busca){
		if(listGaleria != undefined && listGaleria.length > 0 && listGaleria[0].id != undefined){
			var html = "</br><table class='table'>";
			html += "<th> Imagem </th><th> Informação Galeria </th><th> Nome Galeria </th>";	

		 	for(var i = 0; i < listGaleria.length; i++){
			    html += "<tr>";
					html += "<td>" + "<img height='200px' width='250px'  src='" + ONG.contextPath + "/rest/download?file=" + listGaleria[i].nomeImagem +"'>" + "</td>";
				  	html += "<td>" + listGaleria[i].informacaoGaleria + "</td>";
					html += "<td>" + listGaleria[i].nome + "</td>";					
					html += "<td>" + "<button type = 'button' class='btn btn-pencil' onclick='ONG.monta.dadosformulario("+listGaleria[i].id+")'>Editar</button>" + "</td>"
				    html += "<td>" + "<button type = 'button'class='btn btn-trash' onclick='ONG.monta.confirmaExcluir("+listGaleria[i].id+")'>Excluir</button>" + "</td>"
				html += "</tr>";  
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
						var dialog = bootbox.dialog({
						    title: 'Carregando Galeria ',
						    message: '<p><i class="fa fa-spin fa-spinner"></i> Carregando...</p>'
						});

						dialog.init(function(){
						    setTimeout(function(){	
						       dialog.find('.bootbox-body').html('Galeria carregadas com sucesso!');
						    }, 2000);
						});
						ONG.monta.montaGaleria(listGaleria);
					},
					
					error: function(err){
						bootbox.alert("Erro ao consultar os contatos:" + err.responseText);
					}
				};
				
				ONG.ajax.get(cfg);
			}else{
				
				html += "<tr><td colspan='3'>Nenhum registro encontrado</td></tr>";
			}
	    }
		  
		html += "</table>";
		$("#resultadoGaleria").html(html);
	}	

	ONG.monta.montaGaleria(undefined, "");

	ONG.monta.dadosformulario = function(id){

		var url = ONG.contextPath  + "/rest/galeria/consultaPorId/" + id;

		$.ajax({
			  
			type:'GET',
			url: url,
			data: JSON.stringify(id),
			dataType:'Json',
			contentType:'application/json',
				  
			success:function(galeria){ // estou retornando do banco sómente 1 GALERIA PARA EDITAR ! com todos os dados !!!!
				
				ONG.monta.formulario(galeria,id);
			},
			
			error: function(err){
				
				bootbox.alert("Ocorreu erro para editar está galeria");
			}
		});
	}

	ONG.monta.formulario = function(galeria, id){

		// buscar o EVENTO AQUI para listar no select **

		var valorBusca = "";

		if(valorBusca == ""){
						
			valorBusca = null;
		}

		var categoria = '1';

		var cfg ={
				
			url: ONG.contextPath + "/rest/eventos/consultarEvento/" + valorBusca + "/" + categoria,
			
			success: function(listaEventos){
										
				//console.log(listaEventos);
				ONG.monta.formularioGaleria(galeria,listaEventos);
			},
			error: function(err){
				
				bootbox.alert("Erro ao Buscar os Eventos, entrar em contato com o Administrador!");
			}
		};
		
		ONG.ajax.get(cfg);
	}

	ONG.monta.formularioGaleria = function(galeria, listaEvento){

		$("#form").load("formularios/formGaleria.html", function(){

			$('#resultadoGaleria').html("");

			if(galeria != undefined && galeria != null){

			  	if(listaEvento != undefined && listaEvento.length > 0 && listaEvento[0].id != undefined){
		 
					for(var i = 0; i < listaEvento.length; i++){

						var option = $("<option></option>").appendTo($('#eventos'));
						option.attr("value", listaEvento[i].id);
						option.html( listaEvento[i].nomeEvento + " " + ", " + " Data Evento: " + listaEvento[i].dataEvento);
					}
				}  

			  	$("#id").val(galeria.id);
			  	$('#nome').val(galeria.nome);
			  	$("#result").html(""); 
				$("<img>").attr("src",ONG.contextPath +"/rest/download?file="+galeria.nomeImagem).appendTo("#result");
			  	$('#informacaoGaleria').val(galeria.informacaoGaleria);
			}
		});
	}
	
	ONG.monta.validaVaziu = function(campo, valor){

		var msg = "";

		if(valor == null ||  valor.trim() == ""){

			msg += " O campo: " + campo + " Está Vazio. </br>";
		}

		return msg;
	};

	ONG.monta.editarGaleria = function(){

		var valdNomGal = ONG.monta.validaVaziu("Nome galeria: ", $("#nome").val());
		var valdEvent = ONG.monta.validaVaziu("Evento: ", $("#eventos").val());
		var valdInf = ONG.monta.validaVaziu("Informação: " , $("#informacaoGaleria").val());

		var id = $("#id").val();
		var msg = "";
		
		if(valdNomGal !=""  && valdNomGal != null){
			
			msg += valdNomGal;
		}
		
		if(valdEvent != "" && valdEvent != null){
			
			msg += valdEvent;
		}
		
		if(valdInf != "" && valdInf != null){
			
			msg += valdInf;
		}
		
		var newCont = new Object();
		var evento = new Object();

		newCont.id_evento = evento;
		
		evento.id = $("#eventos").val();
		newCont.id_evento = evento;

    	newCont.nome = $("#nome").val();
    	newCont.informacaoGaleria = $("#informacaoGaleria").val();

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

			    	 // ver outro melhor modo

			    	/*
			    	  *
					  * NO BOOT BOX CONFIRM estou criando uma valição pelo próprio botão, que 
					  * SE tiver um nova imagem para realmente editar você coloca sim e ira fazer UPLOAD 
					  * SE NÃO faz nada !!
			    	*/

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

										newCont.nomeImagem = data;

										if(data != "" & data != null){
											
											$("#result").html("");
											$("<img>").attr("src",ONG.contextPath +"/rest/download?file="+data).appendTo("#result");
											
											ONG.monta.editandoGaleria(newCont);
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
						
						ONG.monta.editandoGaleria(newCont);
			    	}
			    }
			});	
		}else{

			bootbox.alert(msg);
		}
	};

	ONG.monta.editandoGaleria = function(newCont){

		if(newCont != undefined || newCont != "" & newCont != null ){
			
			$.ajax({
			
				type: 'PUT',
				url:ONG.contextPath + "/rest/galeria/editarGaleriaPorId/",
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

		        console.log('Excluirdo com Sucesso: ' + result);

		        if(result == true){
		        	
		           var cfg = {

						url:  ONG.contextPath + "/rest/galeria/inativar/" + id,
						success: function (data){
							
							bootbox.alert(data);
						
							var intervalo = window.setInterval(function() {
			            	}, 50);
			            	
			            	window.setTimeout(function() {
			            	
			            	    clearInterval(intervalo);
			            	    ONG.monta.montaGaleria();			
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


