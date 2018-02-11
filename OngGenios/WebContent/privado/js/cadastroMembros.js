ONG.contato = new Object();

ONG.cadastroMembro = {};

$(document).ready(function(){
	
	ONG.contato.validaVazio = function(campo, valor){

		var msg = "";
		if(valor == null ||  valor.trim() == ""){
			msg += "- " + campo + " Está Vazio. </br>";
		}
		return msg;
	};
	
	ONG.contato.cadastrar = function(){

		var msg="";
		var expressao="";

		msg+=ONG.contato.validaVazio("Nome: ", $("#nome").val());
		msg+=ONG.contato.validaVazio("Sobrenome: ", $("#sobrenome").val());
		msg+=ONG.contato.validaVazio("E-mail: ", $("#email").val());
		msg+=ONG.contato.validaVazio("Telefone Residencial: ", $("#telefone1").val());
		msg+=ONG.contato.validaVazio("Telefone Celular: ", $("#telefone2").val());
		msg+=ONG.contato.validaVazio("Endereço: ", $("#endereco").val());		
		msg+=ONG.contato.validaVazio("Data Nascimento: ", $("#dataNasc").val());
		msg+=ONG.contato.validaVazio("Login: ", $("#login").val());
		msg+=ONG.contato.validaVazio("Cep: ", $("#cep").val());

		var valdsenha=ONG.contato.validaVazio("Valida Senha: ", $("#senha").val());
		var valdconfsenha=ONG.contato.validaVazio("Valida Cconfirma Senha: ", $("#confsenha").val());
		
		 if(msg=="" || msg==null){
			if(!$("#nome").val().match(/^[A-Za-z\s]+$/)){
				expressao+="Nome invalido ! </br>";
			}
			if(!$("#sobrenome").val().match(/^[A-Za-z\s]+$/)){
				expressao+="Sobrenome invalido ! </br>";
			}
			if($("#email").val().indexOf("@")==-1 || //valida se existe o @
	    		$("#email").val().indexOf(".")==-1 || //valida se existe o .
	    		$("#email").val().indexOf("@")==0 || //valida se tem texto antes do @
	    		$("#email").val().lastIndexOf(".") +1 ==email.length || //valida se tem texto depois do ponto
	    		($("#email").val().indexOf("@")+1== $("#email").val().indexOf("."))){ //valida se tem texto entre o @ e o .{
				expressao += "E-mail invalido"+"</br>"
				+ "ex: teste_@teste.com.br"
				document.getElementById("email").focus();
	    	}
	    	if(!$("#telefone1").val().match(/^\d{10,13}$/)){				    	
				expressao+="Telefone Residencial invalido ! </br> " + 
			     		"ex: 4791088783" + "</br>" + "</br>";
			}
	    	if(!$("#telefone2").val().match(/^\d{10,13}$/)){				
				expressao+="Telefone Celular invalido ! </br> " + "</br>"+
			     		"ex: 4791088783" + "</br>" + "</br>";
			}
	    	if(!$("#cep").val().match(/^[0-9]{8}$/)){
	    		expressao+="<br> Cep invalido por gentileza informar cep correto" + "</br>";
	    	}
			if(valdsenha != "" || valdsenha != null){
				expressao+=valdsenha;
			}
			if(valdconfsenha != "" || valdconfsenha != null){
	        	expressao+=valdconfsenha;
	        }
	       if($("#senha").val()!=$("#confsenha").val()){
				expressao+="<br> Senhas não conferem "+" </br>";
			}else{
				if(senha.length < 4 || senha.length > 8){
					expressao+="<br> senha está com tamanho incorreto " + "</br>";
				}
			}

	  		if(expressao==""){

		   	   var login=$("#login").val() + ".OngGenios";
			   
			   var newCont=new Object();
			   
	    	   newCont.nome=$("#nome").val();
	    	   newCont.sobreNome=$("#sobrenome").val();
	    	   newCont.email=$("#email").val();
	    	   newCont.telCelular=$("#telefone1").val();
	    	   newCont.telResidencial=$("#telefone2").val();
	    	   newCont.endereco=$("#endereco").val();
	    	   newCont.dataNascimento=$("#dataNasc").val();
	    	   newCont.cep=$("#cep").val()
	    	   newCont.login=login;
	    	   
	    	   var criptbase64=btoa($("#senha").val());
	    	   newCont.senha=criptbase64;
	    	   ONG.contato.enviaServidor (newCont);
	    	}else{

	    		bootbox.alert(expressao);
	    	}
        }else{

         	bootbox.alert(" Caro usuário, gentileza verificar os seguintes campos: </br>" + msg);
        }
	};
	
	ONG.contato.enviaServidor = function(newCont){
		var cfg = {
	    		 
    		type: "POST",
    		url: ONG.contextPath + "/rest/membros/adicionar", 
    		data: newCont,
    		     		 
    	    success: function(msg) {

    	    	var dialog = bootbox.dialog({
    	    		
    	    	    title: 'Cadastrando usuario',
    	    	    message: '<p><i class="fa fa-spin fa-spinner"></i> Carregando...</p>'
    	    	});
    	    	dialog.init(function(){
    	    		
    	    	    setTimeout(function(){
    	    	        dialog.find('.bootbox-body').html('Membro cadastrado com sucesso');
    	    	    }, 2000);
    	    	    setTimeout(function(){
    	    	    	location.reload();
    	    	    }, 5000);
    	    	});
    	    },
    	    error: function(err) {    	    	
    	    	bootbox.alert({

				    message: err.responseText,
				    callback: function () {

				        console.log('Erro no sistema por gentileza entrar em contato com o Administrador!');
				    }
				}); 
    	    }
    	 };
		ONG.ajax.post(cfg);
	}
});