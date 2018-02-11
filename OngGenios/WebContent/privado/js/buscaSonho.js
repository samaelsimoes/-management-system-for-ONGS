ONG.contato = new Object();

$(document).ready(function(){

	ONG.contato.Buscaprioridade=function(){
		var valoBusca=null;
		var cfg = {
				
			url:  ONG.contextPath + "/rest/prioridade/buscarprioridade/"+valoBusca,
			
			success: function(prioridade){
				if(prioridade != null || prioridade != ""){
					ONG.contato.montarSelectPrioridade(prioridade);
				}else{
					bootbox.alert("Caro usuário nenhuma prioridade cadastrado no sistema, gentileza cadastre e nisso poderá continuar este processo.");
				}
			},
			error: function(err){				
				bootbox.alert("Erro ao Buscar prioridade, entrar em contato com o Administrador!");
			}
		};
		ONG.ajax.get(cfg);
	}
	ONG.contato.montarSelectPrioridade = function(prioridade){
		if(prioridade != undefined && prioridade.length > 0 && prioridade[0].id != undefined){
			for(var i = 0; i < prioridade.length; i++){
				var option = $("<option></option>").appendTo($('#prioridade'));
				option.attr("value", prioridade[i].id);
				option.html(prioridade[i].nomePrioridade);
			}
		}
	}

 //=============================================================================================================================
//==============================================================================================================================
	
	ONG.contato.montaTabela = function(){

	   var busca = $("#consultarSonho").val();	   
	   ONG.contato.montaTabelaDoacao(undefined, busca);
	}		
	
	ONG.contato.montaTabelaDoacao = function(listSonho, busca){
		if(listSonho != undefined && listSonho.length > 0 && listSonho[0].id != undefined){

			var html = "<p> </br><table class='table'></p>";
			html += "<tr> </br> <th> Nome Beneficiario </th><th> SobreNome </th><th> Sonho </th><th> Prioridade </th><th> Responsavel </th></tr>";	
	  		
		 	for(var i = 0; i < listSonho.length; i++){
			  
			  html+="<tr>";
			  html+="<td>" + listSonho[i].beneficiario.nome + "</td>";
			  html+="<td>" + listSonho[i].beneficiario.sobreNome + "</td>";
			  html+="<td>" + listSonho[i].sonho + "</td>";
			  html+="<td>" + listSonho[i].prioridade.nomePrioridade + "</td>";
			  html+="<td>" + listSonho[i].responsavel.nome + "</td>";
			  html+="<td>";
			  html+="<button type = 'button' class='btn btn-pencil' onclick='ONG.contato.pesquisarPorIdDoacao("+listSonho[i].id+")'>Editar</button>"
			  html+="</td>";
			  html+="<td>";
			  html+="<button type = 'button'class='btn btn-trash' onclick='ONG.contato.confirmaexclusaoSonho("+listSonho[i].id+")'>Excluir</button>"
			  html+="</td>";
			  html+="</tr>";  
		 	}
	  	}else{		  
		  	if(listSonho == undefined || (listSonho != undefined && listSonho.length > 0)){	
		  		if(busca==""){
		  			busca=null;
		  		}
				var cfg={
												
					url: ONG.contextPath + "/rest/sonhos/consultarSonho/" + busca + "/" + $("#prioridade").val(),
				
					success: function(listaDoacao){
						ONG.contato.montaTabelaDoacao(listaDoacao);
					},
					error: function(err){						
						bootbox.alert("Erro ao consultar os contatos:"+err.responseText);
					}
				};
				
				ONG.ajax.get(cfg);
			}else{				
				html+="<tr><td colspan='3'>Nenhum registro encontrado</td></tr>";
			}
	    }		  
		html+="</table>";
		$("#resultadoDoacoes").html(html);
	}
	
	ONG.contato.montaTabelaDoacao(undefined,"");
	
	ONG.contato.pesquisarPorIdDoacao=function(id){
				  
		var url=ONG.contextPath+"/rest/sonhos/consultarPorId/"+id;	
		
		$.ajax({
			  
			type:'GET',
			url: url,
			dataType:'Json',
			contentType:'application/json',
				  
			success:function(sonho){				
				ONG.contato.montarDadosFormulario(sonho);
			},
			
			error: function(err){				
				bootbox.alert(err.responseText);
			}
		});
	}

	ONG.contato.montarDadosFormulario = function(sonho){		
		$("#form").load("formularios/formSonho.html",function(){

			$('#resultadoDoacoes').html("");
			
		  	$("#id").val(sonho.id);
		  	$('#nome').val(sonho.beneficiario.nome);
		  	$('#sobreNome').val(sonho.beneficiario.sobreNome);
		  	$('#sonho').val(sonho.sonho);

		  	$('#prioridade').val(sonho.prioridade.numePrioridade);

		  	$('#membro').val(sonho.responsavel.nome);
		});
		
		ONG.contato.buscarBeneficiario();
	}

	ONG.contato.validaVazio=function(campo, valor){

		var msg="";
		if(valor == null ||  valor.trim() == ""){
			msg += "- campo: " + campo + " Está Vazio. </br>";
		}		
		return msg;
	};
	  
	ONG.contato.editarSonho=function(){

		msg="";
		msg+=ONG.contato.validaVazio("Nome ", $("#beneficiario").val());
		msg+=ONG.contato.validaVazio("Sonho ", $("#sonho").val());
		msg+=ONG.contato.validaVazio("Prioridade ", $("#nomeprioridade").val());
		if(msg=="" || msg==null){
				
			var newCont = new Object();
			var prioridade = new Object();
			var beneficiario = new Object();

			newCont.id = $("#id").val();
			beneficiario.id = $("#beneficiario").val();
			newCont.sonho = $("#sonho").val();
			prioridade.id = $("#nomeprioridade").val();
			newCont.prioridade = prioridade;
			newCont.beneficiario = beneficiario;

			if(newCont!=""){				
				$.ajax({
				
					type: 'PUT',
					url:ONG.contextPath + "/rest/sonhos/editar/",
					data: JSON.stringify(newCont),
					
					dataType:'text',
					contentType:'application/json',
					
					success:function(data){						
						bootbox.alert(data);
						setTimeout(function(){
	    	    	         location.reload();
	    	    	    }, 3000);
					},		
					error: function(err){						
						bootbox.alert( err.responseText); 
					}
				});
			}
		}else{
			bootbox.alert("Caro usuário gentileza ajustar os seguintes campos: <br> " +msg);
		}
	}
	
	ONG.contato.confirmaexclusaoSonho = function(id){
		
		bootbox.confirm({

		    message: "Você Deseja Excluir Está Doação?",
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
		    	
		        console.log('Excluido com Sucesso: ' + result);
		        
		        if(result == true){
		        	
		        	ONG.contato.excluirSonho (id);
		        }
		    }
		});		
	}
	
	ONG.contato.excluirSonho=function(id){
		
		var cfg = {

			url:ONG.contextPath + "/rest/sonhos/inativar/" + id,
			
			success: function (data){

				bootbox.alert(data);
				
				 setTimeout(function(){
	    	         location.reload();
	    	    }, 3000);			
			},
			
			error: function (err){
				
				bootbox.alert("Erro Ao Excluir! ");
			}
		};
		
		ONG.ajax.delet(cfg);   
	}

	ONG.contato.buscarBeneficiario = function(){
		
		var valorBusca = null;

		var cfg = {
				
			url: ONG.contextPath + "/rest/beneficiarios/consultarNome/"+valorBusca,
			
			success: function(listaBeneficiario){
										
				ONG.contato.montarSelect(listaBeneficiario);
			},
			
			error: function(err){
				
				bootbox.alert("Erro ao Buscar Beneficiarios, entrar em contato com o Administrador!");
			}
		};
		
		ONG.ajax.get(cfg);
	}
	ONG.contato.montarSelect = function(listaBeneficiario){
		if(listaBeneficiario != undefined && listaBeneficiario.length > 0 && listaBeneficiario[0].id != undefined){
			for(var i = 0; i < listaBeneficiario.length; i++){
				 
				var option = $("<option></option>").appendTo($('#beneficiario'));
				option.attr("value", listaBeneficiario[i].id);
				option.html(listaBeneficiario[i].nome + " " + listaBeneficiario[i].sobreNome);
			}
		}
		ONG.contato.montarSelectPrioridadeForm();
	}
	ONG.contato.montarSelectPrioridadeForm = function(){

		var valoBusca=null;
		var cfg={
				
			url: ONG.contextPath + "/rest/prioridade/buscarprioridade/"+valoBusca,
			success: function(prioridade){
				if(prioridade != null || prioridade != ""){
					if(prioridade != undefined && prioridade.length > 0 && prioridade[0].id != undefined){
						for(var i = 0; i < prioridade.length; i++){
							var option = $("<option></option>").appendTo($('#nomeprioridade'));
							option.attr("value", prioridade[i].id);
							option.html(prioridade[i].nomePrioridade);
						}
					}
				}else{
					bootbox.alert("Caro usuário nenhuma prioridade cadastrado no sistema, gentileza cadastre e nisso poderá continuar este processo.");
				}
			},
			error: function(err){				
				bootbox.alert("Erro ao Buscar prioridade, entrar em contato com o Administrador!");
			}
		};
		ONG.ajax.get(cfg);
	}
});





