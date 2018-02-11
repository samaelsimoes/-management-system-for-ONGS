
ONG.eventos = new Object();

$(document).ready(function(){
	ONG.eventos.buscaMembro = function(){
		var cfg = {
	    		 
    	   type: "GET",
    	   url: ONG.contextPath + "/rest/membroLogado/buscar", 
    		     		 
    	   success: function(msg) {
    		   ONG.eventos.retorno(msg);
    	   },   
    	   error: function(err) {
    			bootbox.alert(" Erro ao localizar membro logando." + " Entrar em contato com o Administrador do sistema ");    	   
    	   }
    	};
		ONG.ajax.get(cfg);
	}

	ONG.eventos.retorno = function(msg){
		
		if(msg.nome == null){
			
			bootbox.alert("<br> <br> Erro no sistema por gentileza SAIR e Entrar novamente! <br> Se continuar entrar em contato com o Administrador"); 
		}else{
			
			$('#nome').val(msg.nome + " " + msg.sobreNome);
			$('#id').val(msg.id);
		}	
	}

	ONG.eventos.validaVazio = function(campo, valor){

		var msg = "";

		if(valor==null ||  valor.trim()==""){
			msg += "  - campo: " + campo + " Está Vazio. </br>";
		}

		return msg;
	};

	ONG.eventos.chamaDados = function(){

		var msg="";
		var expressao="";

	    msg+=ONG.eventos.validaVazio("Endereço ", $("#txtEndereco").val());
	    msg+=ONG.eventos.validaVazio("Latitude ", $("#txtLatitude").val());
	    msg+=ONG.eventos.validaVazio("Longetude ",$("#txtLongitude").val());
	    msg+=ONG.eventos.validaVazio("Contato ",$("#txtcontato").val());
	    msg+=ONG.eventos.validaVazio("Data Inicio de Evento ", $("#data").val());
	    msg+=ONG.eventos.validaVazio("Hora inicio Evento ",$("#horario").val());
	    msg+=ONG.eventos.validaVazio("Membro ", $("#id").val());
	    msg+=ONG.eventos.validaVazio("Informações evento ", $("#mensagem").val());

	    if(msg==""){
	    	if(!$("#txtcontato").val().match(/^\d{10,13}$/)){	
		    	
				expressao += "Telefone invalido ! </br> " + "</br>"+
			     	   " informar no modelo ex: 04791088783" + "</br>" + "</br>";
			}
	    	if(expressao == null || expressao==""){

			    var newCont=new Object();
			    var responsavel=new Object();

			    newCont.endereco=$("#txtEndereco").val();
			    newCont.nomeEvento=$("#txtnome").val();
			    newCont.latitude=$("#txtLatitude").val();
			    newCont.longetude=$("#txtLongitude").val();
			    newCont.contato=$("#txtcontato").val();
			    responsavel.id=$("#id").val();
			    newCont.responsavel=responsavel;
			    newCont.dataEvento=$("#data").val();
			    newCont.horario=$("#horario").val();
			    newCont.mensagem=$("#mensagem").val();

			    ONG.eventos.cadastrar(newCont);
			}else{

				bootbox.alert(expressao);
			}
		}else{

			bootbox.alert("Caro usuário, gentileza verificar os seguintes campos: </br>" + msg);
		}
	}

	ONG.eventos.cadastrar = function(newCont){
		var cfg = {
    		 
    	   url: ONG.contextPath+"/rest/eventos/adicionar", 
    	   data: newCont,     
    	   
    	   success: function(msg) {
    		   
    		   var dialog = bootbox.dialog({
    			   
    			    title: 'Cadastrando por gentileza aguardar',
    			    message: '<p><i class="fa fa-spin fa-spinner"></i> Carregando...</p>'
    			});
    			dialog.init(function(){
    			    setTimeout(function(){
    			        dialog.find('.bootbox-body').html(msg);
    			    }, 3000);
    			    setTimeout(function(){
    			    	location.reload();
    			    },3000);
    			});
    	   },
    	   
    	   error: function(err) {
    	   	
    	   	bootbox.alert("Erro ao cadastrar Eventos " +err);
    	   }
    	};
		
		ONG.ajax.post(cfg);
	}
});