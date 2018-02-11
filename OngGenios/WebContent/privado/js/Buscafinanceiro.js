
ONG.financeiro = new Object();

$(document).ready(function(){
 	ONG.financeiro.buscar = function(){
		if($("#categoria").val()!="0" && $("#categoria").val()!=null && $("#categoria").val()!=""){
			ONG.financeiro.relatorio(undefined);
		}else{
			bootbox.alert(" Selecione uma das categorias");
		}
	}
	ONG.financeiro.relatorio=function(valorTotal){

		if(valorTotal!=undefined ){
			ONG.financeiro.dados(valorTotal, undefined);
		}else{
			var categoria = $("#categoria").val();
			var url = ONG.contextPath+"/rest/financeiro/listatotal/" + categoria;
			$.ajax({

				type:'GET',
				url: url,
				
				dataType:'Json',
				contentType:'application/json',

	    	    success: function(valorTotal) {
					ONG.financeiro.relatorio(valorTotal.toFixed(2));
	    	    },
	    	    error: function(err) {
	    			bootbox.alert( " Entrar em contato com o Adm do sistema ");    	   
	    	    }
	    	});
		}
	}
	ONG.financeiro.dados = function(valorTotal, dados){
		if(dados!=undefined){
			if($("#categoria").val()=="1"){
				ONG.financeiro.deposito(valorTotal,dados);
			}else if($("#categoria").val()=="2"){
				ONG.financeiro.saque(valorTotal,dados);
			}else{
				bootbox.alert(" Por gentileza selecione uma das opções")
			}
		}else{
			var cfg ={							
				url:  ONG.contextPath+"/rest/financeiro/buscar/"+$("#categoria").val(),
				success: function(dadosRelatorio){
					ONG.financeiro.dados(valorTotal, dadosRelatorio);
				},
				error: function(err){
					bootbox.alert("Erro ao consultar o relatório:"+err.responseText);
				}
			};
			ONG.ajax.get(cfg);
		}
	}

	ONG.financeiro.deposito=function(valorTotal, listdados){

        var totalValor= valorTotal.toString().replace(".", ",");

		var html="<p> Financeiro</p> </br><table class='table'>";
			html+=" <tr> </br> <th> Deposito </th> <th> Data Deposito </th> " +
				  " <th> Nome Responsavel </th> <th> Sobre Nome  </th>  </tr>";	

	 	for(var i = 0; i < listdados.length; i++){
		 	html+="<tr>";
			if(listdados[i].deposito != null ){
			 	html+="<td>" + "R$ " + listdados[i].deposito + "</td>";
			}else{
				html += "<td>" + " " + "</td>";
			} 
		 	if(listdados[i].dataDeposito != null ){
		 	 	html += "<td>" + listdados[i].dataDeposito + "</td>";
		 	}else{
		 		html += "<td>" + " " + "</td>";
		 	}
			if(listdados[i].id_responsavel.nome != null){
				html += "<td>" + listdados[i].id_responsavel.nome + "</td>";	
			}else{
				html += "<td>" + " " + "</td>";				
			}
			if(listdados[i].id_responsavel.sobreNome != null){
				html += "<td>" + listdados[i].id_responsavel.sobreNome + "</td>";
			}else{
				html += "<td>" + " " + "</td>";					
			}
		
			html += "<td>";
			html += "<button type = 'button' class='btn btn-pencil' onclick='ONG.financeiro.pesquisaID("+listdados[i].id+")'>Editar</button>"
			html += "</td>";
			html += "<td>";
			html += "<button type = 'button'class='btn btn-trash' onclick='ONG.financeiro.excluir("+listdados[i].id+',"' +listdados[i].id_responsavel.id +'"' +")'>Excluir</button>"
			html += "</td>";
			html += "</tr>";  


	 	}
 		html += "<tr>"+ "<td> total: R$ " + totalValor + "</td>";	 
	 	html += "</table>";
		$("#resultadoFinanceiro").html(html);
	}
	ONG.financeiro.saque = function(valorTotal, listdados){
		 var totalValor= valorTotal.toString().replace(".", ",");

		var html = "<p> Financeiro</p> </br><table class='table'>";
			html += " <th> Saque </th><th> Data Saque </th> "+
					" <th> Nome Responsavel </th> <th> Sobre Nome  </th>  ";
		for(var i = 0; i < listdados.length; i++){
		    html += "<tr>";

		    	if(listdados[i].saque  != null){
					html += "<td>" +" R$ " +listdados[i].saque + "</td>";
				}else{
					html += "<td> " + " " + "</td>";	
				}
			 	if(listdados[i].dataSaque  != null){
					html += "<td>" + listdados[i].dataSaque + "</td>";
				}else{
					html += "<td> " + " " + "</td>";	
				}
				if(listdados[i].id_responsavel.nome != null){
					html += "<td>" + listdados[i].id_responsavel.nome + "</td>";	
				}else{
					html += "<td> " + " " + "</td>";	
				}
				if(listdados[i].id_responsavel.sobreNome != null){
					html += "<td>" + listdados[i].id_responsavel.sobreNome + "</td>";
				}else{
					html += "<td> " + " " + "</td>";	
				}
			 	
				if(listdados[i].motivo != null){
					html += "<td>";
					html += "<button type = \"button\" class=\"btn btn-trash\" onclick=\"ONG.financeiro.abrirMotivo(\'"+listdados[i].motivo+"\')\">Motivo</button>"
					html += "</td>";
				}else{

					html += "<td>" + " " + "</td>";					
				}
				
				html += "<td>";
				html += "<button type = 'button' class='btn btn-pencil' onclick='ONG.financeiro.pesquisaID("+listdados[i].id+")'>Editar</button>"
				html += "</td>";

				html += "<td>";
				html += "<button type = 'button'class='btn btn-trash' onclick='ONG.financeiro.excluir("+listdados[i].id+ ',"' +listdados[i].id_responsavel.id +'"' +")'>Excluir</button>"
				html += "</td>";
		    html += "</tr>"; 
	 	}	


 html += "<tr>"+ "<td> total: R$ " + totalValor + "</td>";	 +"</tr>";
	 	html += "</table>";
		$("#resultadoFinanceiro").html(html);
	}
	ONG.financeiro.abrirMotivo = function(motivo){
		if (motivo != null && motivo != "") {
			$("#form").load("formularios/formMotivo.html",function(){
				$('#resultadoFinanceiro').html("");
			  	$("#motivo").val("teste " + motivo);	
			});
		}else{
			Bootbox.alert(" Sem motivo ");
		}
	}
	ONG.financeiro.excluir = function(id, id_responsavel){
		var cfg = {
	    		 
    	    type: "GET",
    	    url: ONG.contextPath + "/rest/membroLogado/buscar",  
    	    success: function(membroLogado) {
    		      
    		  ONG.financeiro.excluirDados(id, id_responsavel, membroLogado.id);
    	    },
    	    error: function(err) {
    	   	
    			bootbox.alert(" Erro ao localizar o membro logando." + " Entrar em contato com o Adm do sistema ");    	   
    	    }
    	};
		ONG.ajax.get(cfg);
	}

	ONG.financeiro.excluirDados = function(id, id_responsavel, membroLogado){
		if( membroLogado ==id_responsavel){
			bootbox.confirm({
			    message: "Você Desejea Excluir?",
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
							url:  ONG.contextPath + "/rest/financeiro/inativar/" + id,
							success: function (data){	

								bootbox.alert(data);

								var intervalo = window.setInterval(function() {
				            	}, 50);	
				            	window.setTimeout(function() {
				            	    clearInterval(intervalo);
				            	    ONG.financeiro.buscar();	
				            	}, 3000);
							},
							error: function (err){
								bootbox.alert("Erro ao deletar: " + err.responseText);
							}
						};
						ONG.ajax.delet(cfg);
			        }
			    }
			});
		}else{

			bootbox.alert("Usuário sem permissão para este processo.");
		}
	}

	ONG.financeiro.pesquisaID = function(id,tipo){

		var tipo = $("#categoria").val();
		var url = ONG.contextPath + "/rest/financeiro/buscarPorId/" + id +"/"+ tipo;		  
		$.ajax({
			type:'GET',
			url: url,
			dataType:'Json',
			contentType:'application/json',
			success: function(dados){
				ONG.financeiro.MontarDadosPesquisa(dados, undefined);
			},
			error: function(err){
				bootbox.alert(err.responseText);
			}
		});
	}



	ONG.financeiro.MontarDadosPesquisa = function(dados, membroLogado){

		if(membroLogado != undefined){
			if(dados.id_responsavel.id == membroLogado.id){

				ONG.financeiro.formulario(dados,membroLogado);
			}else{
				bootbox.alert("Sem permissão para realizar este processo");
			}
		}else if(membroLogado == undefined){

			var cfg = {
	    		 
	    	    type: "GET",
	    	    url: ONG.contextPath + "/rest/membroLogado/buscar",  
	    	    success: function(membroLogado) {
	    		      
	    		  ONG.financeiro.MontarDadosPesquisa(dados, membroLogado);
	    	    },
	    	    error: function(err) {
	    	   	
	    			bootbox.alert(" Erro ao localizar o membro logando." + " Entrar em contato com o Adm do sistema ");    	   
	    	    }
	    	};
			ONG.ajax.get(cfg);
		}
	}

	ONG.financeiro.formulario = function(dados, membroLogado){
		if($("#categoria").val() == 1){
			$("#form").load("formularios/formFinanceiroDeposito.html",function(){
				$('#resultadoFinanceiro').html("");
					  
				$("#id").val(dados.id);
				var deposito = dados.deposito;

           	    valordeposito= deposito.toString().replace(".", ",");

				$('#deposito').val(valordeposito);
				$('#motivo').val(dados.motivo);

			});
		}
	    if($("#categoria").val() == 2){
	    	$("#form").load("formularios/formFinanceiroSaque.html",function(){
				$('#resultadoFinanceiro').html("");
  
				$("#id").val(dados.id);

				var saque = dados.saque;

           	    valorsaque= saque.toString().replace(".", ",");

				$('#saque').val(valorsaque);
				$('#motivo').val(dados.motivo);
			});
		}	    
	}
	ONG.financeiro.editar=function(){

		if($("#categoria").val()=="1"){
			if($("#motivo").val()!=""){

				var newCont = new Object();

				newCont.id=$("#id").val();
				var deposito=$("#deposito").val();
            	newCont.deposito=deposito.toString().replace(",", ".");
				newCont.motivoEditacao=$("#motivo").val();
				newCont.categoria=$("#categoria").val();

				$.ajax({
				
					type: 'POST',
					url:ONG.contextPath + "/rest/financeiro/editar/",
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
			}else{
				bootbox.alert("Campo Motivo ObrigaTório");
			}
		}else if($("#categoria").val()=="2"){
			if($("#motivo").val()!=""){

				var newCont=new Object();

				newCont.id=$("#id").val();

				var saque = $("#saque").val();
           		newCont.saque= saque.toString().replace(",", ".");

				newCont.motivoEditacao =$("#motivo").val();
				newCont.categoria=$("#categoria").val();
				
				$.ajax({
				
					type: 'POST',
					url:ONG.contextPath + "/rest/financeiro/editar/",
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
			}else{
				bootbox.alert("Campo Motivo ObrigaTório");
			}
		}
	}

	ONG.financeiro.abrirMotivo = function(motivo){

		if (motivo!=null && motivo!="") {

			$("#form").load("formularios/formMotivo.html",function(){

				$('#resultadoDoacoes').html("");
			  	$("#motivo").val("teste " + motivo);	
			});
		}else{

			Bootbox.alert(" Sem motivo ");
		}
	}
})

