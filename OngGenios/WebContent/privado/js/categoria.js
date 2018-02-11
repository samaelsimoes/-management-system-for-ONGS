
ONG.gerenciar = new Object();
ONG.categoria = {};

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

	//====================================================================================================
   //=====================================================================================================
	ONG.gerenciar.validaVazio = function(campo, valor){

		var msg = "";
		if(valor == null ||  valor.trim() == ""){
			msg += " O campo: " + campo + " Está Vazio. </br>";
		}
		return msg;
	};

	ONG.gerenciar.cadastrar =function(){

		var msg="";
		msg+=ONG.gerenciar.validaVazio("Categoria", $("#categoria").val());

		var newCont= new Object();
		var responsavelCadastro = new Object();
		
		if(msg=="" || msg==null){

			newCont.tipoCategoria=$("#categoria").val();
			responsavelCadastro.id=$("#idmembro").val();
			newCont.responsavelCadastro=responsavelCadastro;

			var cfg={

				url: ONG.contextPath+"/rest/categoria/adicionar",
				data: newCont,    
				success: function(msg){

					bootbox.alert(msg);
				},

				error: function(err){

					alert(err);
				}
			};
			ONG.ajax.post(cfg);
		}else{

			bootbox.alert(msg);
		}
	}

 //=================================================================================================================================
//=================================================================================================================================

	ONG.gerenciar.consultarCategoria = function(){

		var dados=$("#buscaCategoria").val();

		ONG.gerenciar.montaRelatorio(undefined,dados);
	}

	ONG.gerenciar.montaRelatorio = function(listaCategoria,dados){

		var html="<table class='table'>";
		
		html+="<tr><p></p><p> Categoria </p>  </br><th> Tipo Categoria </th></tr>";					
		
		    if(listaCategoria != undefined && listaCategoria.length > 0 && listaCategoria[0].id != undefined){
			  
			  	for(var i = 0; i < listaCategoria.length; i++){
				  
					  html += "<tr>";
					  html += "<td>" + listaCategoria[i].tipoCategoria + "</td>";
					  html += "<td>";
					  html += "<button type='button' class='btn btn-pencil' onclick='ONG.gerenciar.pesquisaIdEventos("+listaCategoria[i].id+")'>Editar</button>"
					  html += "</td>";
					  html += "<td>";
					  html += "<button type='button'class='btn btn-trash' onclick='ONG.gerenciar.excluirCategoria("+listaCategoria[i].id+")'>Excluir</button>"
					  html += "</td>";
					  html += "</tr>";  
			    }
		    }else{
			    if(listaCategoria == undefined || (listaCategoria != undefined && listaCategoria.length > 0)){
					
					var cfg={
							
						url: ONG.contextPath+"/rest/categoria/buscar/"+dados,
						
						success: function(listaCategoria){

							ONG.gerenciar.montaRelatorio(listaCategoria);				
						},
						error: function(err){							
							bootbox.alert("Erro ao Buscar Categoria, entrar em contato com o Administrador!");
						}
					};
					
					ONG.ajax.get(cfg);
				}else{					
					html += "<tr><td colspan='3'>Nenhum registro encontrado</td></tr>";
				}
		  }
		  
		  html +="</table>";
		  $("#resultadoCategoria").html(html);
	}
	
	ONG.gerenciar.montaRelatorio(undefined, "");

	ONG.gerenciar.pesquisaIdEventos = function(id){

		var url = ONG.contextPath + "/rest/categoria/consultarPorId/" + id;	

		$.ajax({
			  
			type:'GET',
			url: url,
			data: JSON.stringify(id),
			dataType:'Json',
			contentType:'application/json',
				  
			success:function(data){
				ONG.gerenciar.formCategoria(data);
			},
			
			error: function(err){				
				bootbox.alert("Ocorreu erro ao chamar os dados da categoria para o Formulário ");
			}
		});
	}

	ONG.gerenciar.formCategoria = function( valorLista){

		$("#form").load("formularios/formCategoria.html",function(){
			$('#resultadoCategoria').html("");

			if(valorLista!=undefined && valorLista!=null){
				  
				$("#id").val(valorLista.id);
				$("#categoria").val(valorLista.tipoCategoria);
			}
		});
	};

	ONG.gerenciar.salvarCategoria = function(){

		var msg="";
		var newCont=new Object();

		msg+=ONG.gerenciar.validaVazio("Categoria ", $("#categoria").val());

		if(msg=="" || msg ==null){

			newCont.id=$("#id").val();
			newCont.tipoCategoria=$("#categoria").val();

			$.ajax({
			    			
	    		type: 'POST',
				url: ONG.contextPath + "/rest/categoria/editarCategoria/",
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

	ONG.gerenciar.excluirCategoria = function(id){

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
					url:  ONG.contextPath + "/rest/categoria/inativar/"+id,
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
});