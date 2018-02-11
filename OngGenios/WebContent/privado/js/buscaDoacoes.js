ONG.contato = new Object();

$(document).ready(function(){
	ONG.contato.buscrCategoria = function(){
		
	  	var valorCategoria = $("#categoria").val();
	  
	  	if(valorCategoria == null){
	    	ONG.contato.buscaCategorias();
	  	}
	}
	ONG.contato.buscaCategorias = function(){
		var cfg={
			 
		    type: "GET",
		    url: ONG.contextPath+"/rest/categoria/buscar", 
			     		 
		   	success: function(listaCategoria){
			   ONG.contato.montarSelect(listaCategoria);
		  	},		   
		   	error: function(err){
		   		bootbox.alert(" <br> <br> Erro ao cadastrar um novo contato: " + err.responseText);
		   	}
		};
		ONG.ajax.get(cfg);
	}
	
	ONG.contato.montarSelect = function(listaCategoria){
	   	if(listaCategoria != undefined && listaCategoria.length > 0 && listaCategoria[0].id != undefined){
		 	for(var i = 0; i < listaCategoria.length; i++){
			 
				var option = $("<option></option>").appendTo($('#categoria'));
	        	option.attr("value", listaCategoria[i].id);
		    	option.html(listaCategoria[i].tipoCategoria);
		 	}
	   	}
	}
	
	/*
		=====================================================================================================
		=====================================================================================================
	*/

	ONG.contato.montaTabela = function(){	    	    
	   var valorBusca=$("#consultarDoacoes").val();
	   ONG.contato.montaTabelaDoacao(undefined, valorBusca);
	}		
	
	ONG.contato.montaTabelaDoacao = function(listaDoacao, valorBusca){
		if(listaDoacao != undefined && listaDoacao.length > 0 && listaDoacao[0].id != undefined){
			
			var html = "<p> Doações</p> </br><table class='table'>";
			html += "<tr> </br> <th> Doação </th><th> Nome </th><th> SobreNome </th><th> categoria </th><th> Quantidade </th></tr>";	
	  		
		 	for(var i = 0; i < listaDoacao.length; i++){
			  
			  html += "<tr>";
			  html += "<td>" + listaDoacao[i].doacao + "</td>";
			  html += "<td>" + listaDoacao[i].responsavel.nome + "</td>";
			  html += "<td>" + listaDoacao[i].responsavel.sobreNome + "</td>";
			  html += "<td>" + listaDoacao[i].categoria.tipoCategoria + "</td>";
			  html += "<td>" + listaDoacao[i].quantidade + "</td>";
			  html += "<td>";
			  html += "<button type='button' class='btn btn-pencil' onclick='ONG.contato.pesquisarPorIdDoacao("+listaDoacao[i].id+")'>Editar</button>"
			  html += "</td>";
			  html += "<td>";
			  html += "<button type='button'class='btn btn-trash' onclick='ONG.contato.confirmaexclusaoDoacao("+listaDoacao[i].id+")'>Excluir</button>"
			  html += "</td>";
			  html += "</tr>";  
		 	}
	  	}else{  
		  	if(listaDoacao == undefined || (listaDoacao != undefined && listaDoacao.length > 0)){
		  		
			  	if(valorBusca == ""){
					valorBusca=null;
				}

			  	var categoria = $("#categoria").val();
			  
			  	if(categoria==""){
					categoria=null;
				}	 
				var cfg ={
											
					url: ONG.contextPath + "/rest/doacao/consultarDoacao/" + valorBusca + "/" + categoria,
				
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
	
	ONG.contato.pesquisarPorIdDoacao= function(id){
				  
		var url = ONG.contextPath + "/rest/doacao/consultarPorId/" + id;		  
		$.ajax({
			  
			type:'GET',
			url: url,
			dataType:'Json',
			contentType:'application/json',
				  
			success:function(doacao){				
				ONG.contato.montarDadosFormulario(doacao);
			},
			
			error: function(err){
				bootbox.alert(err.responseText);
			}
		});
	}

	ONG.contato.montarDadosFormulario = function(doacao){

		var cfg = {
			 
		   type: "GET",
		   url: ONG.contextPath + "/rest/categoria/buscar", 
			     		 
		   success: function(listaCategoria) {
			   ONG.contato.chamaFormularioDoacao(doacao, listaCategoria);
		   },
		   
		  	error: function(err) {
		   	
		  		bootbox.alert("Erro ao Montar o Formulário!");
		   	}
		};
		
		ONG.ajax.get(cfg);
	}

	ONG.contato.chamaFormularioDoacao = function(doacao, listaCategoria){
		$("#form").load("formularios/formDoacao.html",function(){
			$('#resultadoDoacoes').html("");
			if(doacao != undefined && doacao != null){

			  	if(listaCategoria != undefined && listaCategoria.length > 0 && listaCategoria[0].id != undefined){
		 
					for(var i = 0; i < listaCategoria.length; i++){
						 
						var option = $("<option></option>").appendTo($('#categoria2'));
				        option.attr("value", listaCategoria[i].id);
					    option.html(listaCategoria[i].tipoCategoria);
					}
				}  
			  	$("#id").val(doacao.id);
			  	$('#doacao').val(doacao.doacao);
			  	$('#categoria2').val(doacao.categoria.id);
			  	$('#quantidade').val(doacao.quantidade);
			}
		});
	}
	ONG.contato.validaVazio = function(campo, valor){

		var msg = "";
		if(valor == null ||  valor.trim() == ""){
			msg += "- campo: " + campo + " Está Vazio. </br>";
		}
		return msg;
	};  	
	ONG.contato.editarDoacao = function(){

		var msg="";

		msg+=ONG.contato.validaVazio("Doacao ", $("#doacao").val());
		msg+=ONG.contato.validaVazio("Quantidade ", $("#quantidade").val());

		if(msg==""||msg==null){

			var newCont = new Object();
			var categoria = new Object();
					
			categoria.id = $("#categoria2").val();
			newCont.id =  $("#id").val(),
			newCont.doacao = $("#doacao").val();
			newCont.categoria = categoria;
			newCont.quantidade = $("#quantidade").val();
					
			$.ajax({
		
				type: 'POST',
				url:ONG.contextPath + "/rest/doacao/editarDoacao/",
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
		}else{
			bootbox.alert("Caro usuário gentileza ajustar os seguintes campos: <br>"+msg);
		}
	}
	
	ONG.contato.confirmaexclusaoDoacao = function(id){
		
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
		        if(result==true){
		        	ONG.contato.excluirDoacao (id);
		        }
		    }
		});		
	}
	
	ONG.contato.excluirDoacao = function(id){
		
		var cfg = {

				url:  ONG.contextPath + "/rest/doacao/inativar/" + id,
				
				success: function (data){

					bootbox.alert(data);
					setTimeout(function(){
		    	    	location.reload();
		    	    }, 4000);
				},
				
				error: function (err){
					bootbox.alert("Erro Ao Excluir! ");
				}
			};
			
		ONG.ajax.delet(cfg);   
	}
});





