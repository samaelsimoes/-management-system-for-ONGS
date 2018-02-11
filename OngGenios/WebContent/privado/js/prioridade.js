ONG.gerenciar = new Object();
ONG.prioridade = {};

$(document).ready(function(){

	ONG.gerenciar.membro=function(){
		var cfg={
	    		 
    	    type: "GET",
    	    url: ONG.contextPath+"/rest/membroLogado/buscar", 
    		     		 
    	    success: function(msg) {
    		   ONG.gerenciar.retorno(msg);
    	    },
    	   
    	    error: function(err) {
    			bootbox.alert(" Erro ao localizar o membro logando." + " Entrar em contato com o Adm do sistema ");    	   
    	    }
    	};
		ONG.ajax.get(cfg);
	}

	ONG.gerenciar.retorno=function(msg){
		if(msg.nome==null){			
			bootbox.alert("<br> <br> Erro no sistema por gentileza SAIR e Entrar novamente!"); 
		}else{
			
			$('#nome').val(msg.nome + " " + msg.sobreNome);
			$('#idmembro').val(msg.id);
		}	
	}
 // ==========================================================================================================================
// ==========================================================================================================================

	ONG.gerenciar.validaVazio = function(campo, valor){
		var msg = "";
		if(valor == null ||  valor.trim() == ""){
			msg += "- campo: " + campo + " Está Vazio. </br>";
		}
		return msg;
	};

	ONG.gerenciar.cadastrar=function(){
		var msg="";
		msg+=ONG.gerenciar.validaVazio("Prioridade ", $("#prioridade").val());

 		var newCont=new Object();
 		var responsavel = new Object();

		if(msg==""|| msg==null){
			newCont.nomePrioridade=$("#prioridade").val();
			responsavel.id=$("#idmembro").val()

			newCont.responsavelCadastro=responsavel;
			var cfg={
				url: ONG.contextPath+"/rest/prioridade/adicionar",
				data: newCont,    
				success: function(msg){

					bootbox.alert(msg);
					setTimeout(function(){
    	    	    	location.reload();
    	    	    }, 2000);
				},
				error: function(err){

					bootbox.alert(err);
				}
			};
			ONG.ajax.post(cfg);
		}else{
			bootbox.alert(" Caro usuári gentileza verificar o seguinte campo: <br>" + msg);
		}
	}

	//================================================================================================================
   //================================================================================================================

	ONG.gerenciar.prioridade=function(){
		ONG.gerenciar.Buscaprioridade();
	}

	ONG.gerenciar.Buscaprioridade=function(){
		
		var valorBusca = null;

		var cfg={

			url: ONG.contextPath+"/rest/prioridade/buscarprioridade/"+valorBusca,

			success: function(listaPrioridade){
				ONG.gerenciar.montarSelectPrioridade(listaPrioridade);
			},

			error: function(err){
				alert("ERROOOUU");
			}
		};
		ONG.ajax.get(cfg);
	}

	ONG.gerenciar.montarSelectPrioridade = function(prioridade){
		if(prioridade != undefined && prioridade.length > 0 && prioridade[0].id != undefined || prioridade != ""){
			for(var i = 0; i < prioridade.length; i++){
				var option = $("<option></option>").appendTo($('#prioridade'));
				option.attr("value", prioridade[i].id);
				option.html(prioridade[i].nomePrioridade);
			}
		}else{
			bootbox.alert("Nenhuma prioridade cadastrar, por gentileza realizar cadastrar para poder continuar o processo");
		}
	}

	 // ============================================================================================================================
	// ===============================================================================================================================
   // =================================================================================================================================
  // ===================================================================================================================================

	ONG.gerenciar.consultar=function(){

		var dados=$("#consultarPrioridade").val();
		ONG.gerenciar.montaRelatorio(undefined,dados);
	}

	ONG.gerenciar.montaRelatorio=function(listaprioridade, dados){

 		var html = "<p> Prioridade</p> </br><table class='table'>";
		html += "<tr> </br> <th> Nome Prioridade </th></tr>";	

		if(listaprioridade != undefined && listaprioridade.length > 0 && listaprioridade[0].id != undefined){

		 	for(var i = 0; i < listaprioridade.length; i++){
			  
				html += "<tr>";
				html += "<td>" + listaprioridade[i].nomePrioridade + "</td>";
				html += "<td>";
				html += "<button type='button' class='btn btn-pencil' onclick='ONG.gerenciar.pesquisaId("+listaprioridade[i].id+")'>Editar</button>"
				html += "</td>";
				html += "<td>";
				html += "<button type='button'class='btn btn-trash' onclick='ONG.gerenciar.excluirPrioridade("+listaprioridade[i].id+")'>Excluir</button>"
				html += "</td>";
				html += "</tr>";  
		 	}
	  	}else{  
		  	if(listaprioridade == undefined || (listaprioridade != undefined && listaprioridade.length > 0)){
				if(dados ==""){
					dados=null;
				}
				var cfg={
											
					url: ONG.contextPath+"/rest/prioridade/buscarprioridade/"+dados,
				
					success: function(listaPrioridade){							
						ONG.gerenciar.montaRelatorio(listaPrioridade,dados);
					},
					error: function(err){	
						bootbox.alert("Erro ao consultar as Prioridades: "+err.responseText);
					}
				};
				
				ONG.ajax.get(cfg);
			}else{
				html += "<tr><td colspan='3'>Nenhum registro encontrado</td></tr>";
			}
	    }
		  html += "</table>";
		  $("#resultadoPrioridade").html(html);
	}

	ONG.gerenciar.montaRelatorio(undefined, "");

	ONG.gerenciar.excluirPrioridade = function(id){

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

		        	var cfg = {
					url:  ONG.contextPath + "/rest/prioridade/inativar/"+id,
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
		    }
		});		
	}

	ONG.gerenciar.pesquisaId = function(id){

		var url = ONG.contextPath + "/rest/prioridade/buscarid/" + id;	

		$.ajax({
			  
			type:'GET',
			url: url,
			data: JSON.stringify(id),
			dataType:'Json',
			contentType:'application/json',
			success:function(valorLista){
				ONG.gerenciar.formPrioridade(valorLista);
			},
			
			error: function(err){				
				bootbox.alert("Ocorreu erro ao chamar os dados da categoria para o Formulário ");
			}
		});
	}

	ONG.gerenciar.formPrioridade = function(valorLista){

		$("#form").load("formularios/formPrioridade.html",function(){
			$('#resultadoPrioridade').html("");

			if(valorLista!=undefined && valorLista!=null){
				$("#id").val(valorLista.id);
				$("#prioridade").val(valorLista.nomePrioridade);
			}
		});
	}

	ONG.gerenciar.inativar=function(id){

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
					url:  ONG.contextPath + "/rest/prioridade/inativar/"+id,
					success: function (data){
						
						bootbox.alert(data);
					
						setTimeout(function(){
			    	    	location.reload();
			    	    }, 4000);
					},
					error: function (err){
						bootbox.alert("Erro ao deletar a Prioridade: " + err.responseText);
					}
				};
				
				ONG.ajax.delet(cfg);
		        }
		    }
		});	
	}

	ONG.gerenciar.salvarPrioridade = function(){

		var newCont = new Object();
		var msg="";

		msg+=ONG.gerenciar.validaVazio("Prioridade ", $("#prioridade").val());

		if(msg==""|| msg==null){

			newCont.id=$("#id").val();
			newCont.nomePrioridade=$("#prioridade").val();

			$.ajax({
			    			
	    		type: 'POST',
				url: ONG.contextPath + "/rest/prioridade/editar/",
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

			bootbox.alert(" Caro usuário, gentileza verificar o seguinte campo, não pode ser vazio: <br>" + msg);
		}
	}
});