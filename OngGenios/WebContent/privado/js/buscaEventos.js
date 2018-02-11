
ONG.montar = new Object();

ONG.consultarEvento = {};

$(document).ready(function(){
	ONG.montar.consultarEvento=function(){
		
	    var valorBusca=$("#consultarEvento").val();
	    var valorLista=$("#lista").val();
	    
		if(valorLista==0){
			bootbox.alert("Escolha uma das categorias");
		}

		if(valorLista!=0){

		    if(valorLista == 1){
			    ONG.montar.consultarEventos(undefined, valorBusca);
			}
		    if(valorLista == 2){
				ONG.montar.consultarEventosBeneficiario(undefined, valorBusca);
			}	    
		}
	}		
	
	ONG.montar.consultarEventos = function(listaEventos, valorBusca){
				
		var html = "<table class='table'>";
		
		html += "<tr><p></p><p> Eventos </p>  </br><th> Nome Evento </th><th> Endereco </th>" +
				"<th> DataEvento </th><th> Horario </th> <th> Responsavel </th> <th> Contato Responsavel </th></tr>";					
		
		    if(listaEventos != undefined && listaEventos.length > 0 && listaEventos[0].id != undefined){
			  
			  	for(var i = 0; i < listaEventos.length; i++){
				  
					  html += "<tr>";
					  html += "<td>" + listaEventos[i].nomeEvento + "</td>";
					  html += "<td>" + listaEventos[i].endereco + "</td>";
					  html += "<td>" + listaEventos[i].dataEvento + "</td>";
					  html += "<td>" + listaEventos[i].horario + "</td>";
					  html += "<td>" + listaEventos[i].responsavel.nome + "</td>";
					  html += "<td>" + listaEventos[i].contato + "</td>";
					  html += "<td>";
					  html += "<button type='button' class='btn btn-pencil' onclick='ONG.montar.pesquisaIdEventos("+listaEventos[i].id+")'>Editar</button>"
					  html += "</td>";
					  html += "<td>";
					  html += "<button type='button'class='btn btn-trash' onclick='ONG.montar.confirmaExclusaoEventos("+listaEventos[i].id+")'>Excluir</button>"
					  html += "</td>";
					  html += "</tr>";  
			    }
		    }else{
			    if(listaEventos == undefined || (listaEventos != undefined && listaEventos.length > 0)){
					if(valorBusca == ""){						
						valorBusca = null;
					}

					var valorLista = $("#lista").val();

					var cfg ={
							
						url:  ONG.contextPath + "/rest/eventos/consultarEvento/"+valorBusca+ "/" + valorLista,
						
						success: function(listaEventos,valorLista){
													
							ONG.montar.consultarEventos(listaEventos,valorLista);
						},
						error: function(err){							
							bootbox.alert("Erro ao Buscar Beneficiarios, entrar em contato com o Administrador!");
						}
					};
					
					ONG.ajax.get(cfg);
				}else{					
					html += "<tr><td colspan='3'>Nenhum registro encontrado</td></tr>";
				}
		  }
		  
		  html +="</table>";
		  $("#resultadoEvento").html(html);
	}
	
	ONG.montar.consultarEventos(undefined, "");


 //======================================================================================================================================	
// ====================================================================================================================================

	ONG.montar.consultarEventosBeneficiario = function(listaEventoBene, valorBusca){
				
		var html = "<table class='table'>";
		
		html += "<tr><p></p><p> Eventos </p>  </br><th> Nome Evento </th>" +
				"<th> DataEvento </th><th> Horario </th> <th> Beneficiario: </th> <th> Nome </th> <th>SobreNome </th></tr>";					
		    if(listaEventoBene != undefined && listaEventoBene.length > 0 && listaEventoBene[0].id != undefined){
			  
			  for(var i = 0; i < listaEventoBene.length; i++){
				  
				html += "<tr>";
				html += "<td>" + listaEventoBene[i].nomeEvento + "</td>";
				html += "<td>" + listaEventoBene[i].dataEvento + "</td>";
				html += "<td>" + listaEventoBene[i].horario + "</td>";
				html += "<td>" + "Beneficiario -->" +"</td>";
				html += "<td>" + listaEventoBene[i].beneficiario.nome + "</td>";
				html += "<td>" + listaEventoBene[i].beneficiario.sobreNome + "</td>";
				html += "<td>";
				html += "<button type='button' class='btn btn-pencil' onclick='ONG.montar.pesquisaIdEventos("+listaEventoBene[i].id+")'>Editar</button>"
				html += "</td>";
				html += "<td>";
				html += "<button type='button'class='btn btn-trash' onclick='ONG.montar.confirmaExclusaoEventos("+listaEventoBene[i].id+")'>Excluir</button>"
				html += "</td>";
				html += "</tr>";  
			}
		  }else{
			  if(listaEventoBene==undefined||(listaEventoBene!=undefined&&listaEventoBene.length>0)){
					
					if(valorBusca==""){						
					   valorBusca=null;
					}

					var valorLista=$("#lista").val();

					var cfg={
							
						url:ONG.contextPath+"/rest/eventos/consultarEvento/"+valorBusca+"/"+valorLista,
						success: function(listaEventoBene){
													
							ONG.montar.consultarEventosBeneficiario(listaEventoBene,valorLista);
						},
						
						error: function(err){
							bootbox.alert("Erro ao Buscar Beneficiarios dos Eventos, entrar em contato com o Administrador!");
						}
					};
					
					ONG.ajax.get(cfg);
				}else{
					
					html += "<tr><td colspan='3'>Nenhum registro encontrado</td></tr>";
				}
		  }
		html +="</table>";
		$("#resultadoEvento").html(html);
	}

	ONG.montar.consultarEventosBeneficiario(undefined, "");

	ONG.montar.pesquisaIdEventos=function(id){

	    var valorLista = $("#lista").val(); // valor lista estou pegando  no meu HTML ou seja o ID do meu select 
		var url = ONG.contextPath + "/rest/eventos/consultarPorId/" + id + "/" + valorLista;	

		$.ajax({
			  
			type:'GET',
			url: url,
			data: JSON.stringify(id),
			dataType:'Json',
			contentType:'application/json',
				  
			success:function(data){

				ONG.montar.formularioEvento(data,valorLista);
			},
			
			error: function(err){				
				bootbox.alert("Ocorreu erro ao chamar os dados do evento para o Formulário ");
			}
		});
	}
	
	ONG.montar.formularioEvento=function(data, valorLista){

		if(valorLista==1){
			$("#form").load("formularios/formEvento2.html",function(){
				$('#resultadoEvento').html("");

				if(data!=undefined && data!=null){
					  
					$("#id").val(data.id);
					$('#nomeEvento').val(data.nomeEvento);
					var splitted = data.dataEvento.split("/");
					$('#dataEvento').val( splitted[2]+"-"+splitted[1]+"-"+splitted[0]);
					$('#horario').val(data.horario);
					$('#nomeresponsavel').val(data.responsavel.nome);
					$('#contatoresponsavel').val(data.contato);
					$('#mensagem').val(data.mensagem);
				}
			});
		}
	    if(valorLista==2){

	    	$("#form").load("formularios/formEvento3.html",function(){
				$('#resultadoEvento').html("");

				if(data!=undefined && data!=null){
					  
					$("#id").val(data.id);
					$('#nomeEvento').val(data.nomeEvento);
					var splitted = data.dataEvento.split("/");
					$('#dataEvento').val( splitted[2]+"-"+splitted[1]+"-"+splitted[0]);
					$('#horario').val(data.horario);
					$('#nomebeneficiario').val(data.beneficiario.nome);
					$('#sobrenomebeneficiario').val(data.beneficiario.sobreNome);
				}
			});
		}	    
	}

	ONG.montar.validaVazio = function(campo, valor){
		var msg = "";
		if(valor == null ||  valor.trim() == ""){
			msg += " - campo: " + campo + " Está Vazio. </br>";
		}
		return msg;
	};

	ONG.montar.editarEvento=function(){

		var msg="";
		var extressao="";
		var valorLista=$("#lista").val();
		var newCont=new Object();

		if(valorLista=="1"){

			msg+=ONG.montar.validaVazio("Nome Evento ", $("#nomeEvento").val());
			msg+=ONG.montar.validaVazio("Data Evento ", $("#dataEvento").val());
			msg+=ONG.montar.validaVazio("Horario ", $("#horario").val());
			msg+=ONG.montar.validaVazio("Contato Responsavel ", $("#contatoresponsavel").val());
			msg+=ONG.montar.validaVazio("Mensagem ", $("#mensagem").val());

			if(!$("#contatoresponsavel").val().match(/^\d{10,13}$/)){	
			    	
				extressao += "Telefone invalido ! </br> " + "</br>"+
			     		"ex: 4791088783" + "</br>" + "</br>";
			}
			if(msg==""||msg==null){
				if(extressao==""){
			    	newCont.id=$("#id").val();
					newCont.nomeEvento=$("#nomeEvento").val();
					newCont.dataEvento= $("#dataEvento").val();
					newCont.horario=$("#horario").val();
					newCont.contato=$("#contatoresponsavel").val();
					newCont.mensagem=$("#mensagem").val();
					newCont.valorLista=valorLista;

					ONG.montar.confirmaEditar(newCont);
				}else{
					bootbox.alert("Caro usuario, por gentileza realizar ajuste no seguinte campo: <br>" + extressao);
				}
			}else{

				bootbox.alert("Caro usuário gentileza verificar o seguinte campo: <br>"+msg);
			}
		}else if(valorLista=="2"){

			msg+=ONG.montar.validaVazio("Nome Evento ", $("#nomeEvento").val());
			msg+=ONG.montar.validaVazio("Data Evento ", $("#dataEvento").val());
			msg+=ONG.montar.validaVazio("Horario ", $("#horario").val());

			if(!$("#telefone1").val().match(/^\d{10,11}$/)){	
		    	
				extressao += "Telefone invalido ! </br> " + "</br>"+
			     		"ex: DD xxxx-xxxx" + "</br>" + "</br>";
			}

			if(msg==null || msg ==""){
				if(expressao == ""){

					newCont.id=$("#id").val();
					newCont.nomeEvento=$("#nomeEvento").val();
					newCont.dataEvento=$("#dataEvento").val();
					newCont.horario=$("#horario").val();
					newCont.valorLista=valorLista;

					ONG.montar.confirmaEditar(newCont);
				}else{
					bootbox.alert("Caro usuário gentileza verificar o seguinte campo: <br>" + extressao);
				}
			}else{
				bootbox.alert("Caro usuário gentileza verificar o seguinte campo: <br>"+msg);
			}
		}

	}
	
	ONG.montar.confirmaEditar = function(newCont){
		bootbox.confirm({
		    message: "Você Desejea confirmar essa edição ?",
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
		        	$.ajax({
		    			
		        		type: 'POST',
		    			url: ONG.contextPath + "/rest/eventos/editarEvento/",
		    			data: JSON.stringify(newCont),
		    			dataType:'text',
		    			contentType:'application/json',
		    			success:function(data){	    				
		    				bootbox.alert(data);
		    				setTimeout(function(){
				    	    	location.reload();
				    	    }, 4000);
		    			},
		    			error: function(err){		
		    				bootbox.alert( err.responseText); 
		    			}
		    		});
 		        }
		    }
		});	
	}

	ONG.montar.confirmaExclusaoEventos = function(id){
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
		        	
		            ONG.montar.excluirEvento(id);
		        }
		    }
		});		
	}


	ONG.montar.excluirEvento = function(id){
		var cfg = {
			url:  ONG.contextPath + "/rest/eventos/inativar/"+id,
			success: function (data){
				
				bootbox.alert(data);
			
				setTimeout(function(){
	    	    	location.reload();
	    	    }, 4000);
			},
			error: function (err){
				bootbox.alert("Erro ao deletar o contato: " + err.responseText);
			}
		};
		
		ONG.ajax.delet(cfg);
	}
});