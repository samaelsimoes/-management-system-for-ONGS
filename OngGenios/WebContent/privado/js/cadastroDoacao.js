
ONG.contato = new Object();
ONG.doacao = {};

$(document).ready(function(){
	
	ONG.contato.buscarMembroCategoria=function(){    
	    if($("#nome").val()==null || $("#nome").val()==""){	    	
	    	ONG.contato.buscaMembro();
	    } 
	}

	ONG.contato.buscaMembro=function(){
		
		var cfg = {
	    		 
    	    type: "GET",
    	    url: ONG.contextPath+"/rest/membroLogado/buscar", 
    		     		 
    	    success: function(msg) {
    		   ONG.contato.retorno(msg);
    	    },
    	   
    	    error: function(err) {
    			bootbox.alert(" Erro ao localizar membro logando." + " Entrar em contato com o Adm do sistema ");    	   
    	    }
    	};
		
		ONG.ajax.get(cfg);
	}
	
	ONG.contato.retorno = function(msg){
		
		if(msg.nome == null){			
			bootbox.alert("<br> <br> Erro no sistema por gentileza SAIR e Entrar novamente!"); 
		}else{
			
			$('#nome').val(msg.nome + " " + msg.sobreNome);
			$('#id').val(msg.id);
		}	
		
	    if($("#categoria").val() == null || $("#categoria").val() == ""){
	    	
	    	ONG.contato.buscaCategoria();
	    }
	}

	ONG.contato.buscaCategoria = function(){
		var cfg = {
    		 
    	   type: "GET",
    	   url: ONG.contextPath + "/rest/categoria/buscar", 
    		     		 
    	   success: function(listaCategoria) {
    		   ONG.contato.montarSelect(listaCategoria);
    	   },
    	   
    	   	error: function(err) {
    	   		bootbox.alert(" Erro ao buscar a lista de categoria " + 
    	   				      " Entrar em contato com o Administrador do Sistema");
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
	ONG.contato.validaVazio = function(campo, valor){

		var msg = "";

		if(valor==null ||  valor.trim()==""){

			msg+=" - campo: "+campo+" Está Vazio. </br>";
		}

		return msg;
	};	
	ONG.contato.cadastroDoacao = function(){
		    	
    	msg="";

		msg+=ONG.contato.validaVazio("Doação ", $("#doacao").val());
		msg+=ONG.contato.validaVazio("Quantidade ", $("#quantidade").val());
		msg+=ONG.contato.validaVazio("Categoria ", $("#categoria").val());
		msg+=ONG.contato.validaVazio("responsavel ", $("#nome").val());	
  	
		if(msg=="" || msg==null ){
			if($("#quantidade").val() > 0){	
				
	    		var newCont=new Object();
	    		var categoria=new Object();
	    		var responsavel=new Object();

	    		categoria.id=$("#categoria").val();
	    		responsavel.id=$("#id").val();

	    		newCont.doacao=$("#doacao").val();
	    		newCont.categoria=categoria;
	    		newCont.quantidade=$("#quantidade").val();
	    		newCont.responsavel=responsavel;
	    		
	    		ONG.contato.cadastrar(newCont);
	    	}else{    
	    		bootbox.alert("Por gentileza informar a Quantidade do item doado ex: 2");
	    	}	
        }else{
        	bootbox.alert("Caro usuário por gentileza ajustar os seguintes campos: <br>"+msg);
        }
	}
	
	ONG.contato.cadastrar=function(newCont){
		
		var cfg = {
    		 
    	   url: ONG.contextPath + "/rest/doacao/adicionar", 
    	   data: newCont,     
    	   
    	   success: function(msg) {
    		   
    		   var dialog = bootbox.dialog({
    			   
    			    title: 'Cadastrando por gentileza aguardar',
    			    message: '<p><i class="fa fa-spin fa-spinner"></i> Carregando...</p>'
    			});
    			dialog.init(function(){
    			    setTimeout(function(){
    			    	
    			        dialog.find('.bootbox-body').html("Doação cadastrado com Sucesso!");
    			    }, 2000);
    			    setTimeout(function(){
    	    	    	location.reload();
    	    	    }, 5000);
    			});
    	   },
    	   
    	   error: function(err) {
    	   	
    	   	bootbox.alert("Erro ao cadastrar Doação");
    	   }
    	};
		
		ONG.ajax.post(cfg);
	}
});