ONG.contato1 = new Object();

ONG.cadastroBeneficiario={};

$(document).ready(function(){
    ONG.cadastroBeneficiario.validaVazio = function(campo, valor){

        var msg = "";

        if(valor == null ||  valor.trim() == ""){
            msg += "- " + campo + " Está Vazio. </br>";
        }
        return msg;
    };
	
	ONG.contato1.cadastrar = function(){
		
		var msg="";
		var expressao="";

		msg+=ONG.cadastroBeneficiario.validaVazio("Nome ", $("#nome").val());
		msg+=ONG.cadastroBeneficiario.validaVazio("SobreNome ", $("#sobreNome").val());
		msg+=ONG.cadastroBeneficiario.validaVazio("Valida Email ", $("#email").val());
		msg+=ONG.cadastroBeneficiario.validaVazio("Valida Telefone Residencial ", $("#telefone1").val());
		msg+=ONG.cadastroBeneficiario.validaVazio("Valida Telefone Celular ", $("#telefone2").val());
		msg+=ONG.cadastroBeneficiario.validaVazio("Valida Endereço ", $("#endereco").val());		
		msg+=ONG.cadastroBeneficiario.validaVazio("Valida Data Nascimento", $("#datanasci").val());
		
        if(msg==null || msg==""){

            if($("#email").val().indexOf("@") == -1 || //valida se existe o @
                $("#email").val().indexOf(".") == -1 || //valida se existe o .
                $("#email").val().indexOf("@") == 0 || //valida se tem texto antes do @
                $("#email").val().lastIndexOf(".") + 1 == email.length || //valida se tem texto depois do ponto
                ($("#email").val().indexOf("@") + 1 == $("#email").val().indexOf("."))){ //valida se tem texto entre o @ e o .{
                    
                expressao+="E-mail invalido" +"</br>"
                + "ex: teste_@teste.com.br"
                document.getElementById("email").focus();
            }
            if(!$("#telefone1").val().match(/^\d{10,13}$/)){    
                expressao+="Telefone Residencial invalido ! </br> " + "</br>";
            }
            if(!$("#renda").val().match(/^[0-9]{0,15}[,]{0,1}[0-9]{0,4}$/)){
                expressao+=" Gentileza informar a renda em formato numerico " + "<br>" + "<br>";
            }
            if(!$("#tamanhoFamilia").val().match(/^[0-9]$/)){
                expressao+=" Gentileza informar tamanho total da familia por exemplo '4' "+ "<br>" + "<br>";
            }
            if(!$("#cep").val().match(/^[0-9]{8}$/)){
                expressao+=" Cep invalido por gentileza informar cep correto" + "<br>"+ "<br>";
            }   
            if(!$("#telefone2").val().match(/^\d{10,13}$/)){        
                expressao+="Telefone Celular invalido ! </br> ";
            }
            
            if(expressao==""){

            	var newCont=new Object();
            	
            	newCont.nome=$("#nome").val();
            	newCont.sobreNome=$("#sobreNome").val();
            	newCont.email=$("#email").val();
            	newCont.telCelular=$("#telefone1").val();
            	newCont.telResidencial=$("#telefone2").val();
            	newCont.endereco=$("#endereco").val();

            	newCont.dataNascimento=$("#datanasci").val();
            	var renda = $("#renda").val();
            	newCont.renda= renda.toString().replace(",", ".");
            	newCont.tamanhoFamilia=$("#tamanhoFamilia").val();
            	newCont.cep=$("#cep").val(); 
            	
        	   ONG.contato1.enviaServidor(newCont);
            }else{
                bootbox.alert(expressao);
            }
        }else{
            bootbox.alert("Caro usuário, gentileza verificar os seguintes campos: <br> " + msg);
        }
	};

	ONG.contato1.enviaServidor = function(newCont){

		var cfg = {
	    		 
    		 type: "POST",
    		 url: ONG.contextPath + "/rest/beneficiarios/adicionar", 
    		 data: newCont,
    		     		 
    	    success: function(msg) {    	    	
    	    	var dialog = bootbox.dialog({
    	    		
    	    	    title: 'Cadastrando usuario',
    	    	    message: '<p><i class="fa fa-spin fa-spinner"></i> Carregando...</p>'
    	    	});
    	    	dialog.init(function(){
    	    		
    	    	    setTimeout(function(){
    	    	        dialog.find('.bootbox-body').html('Beneficiario cadastrado com sucesso');
    	    	    }, 2000);
    	    	    setTimeout(function(){
    	    	         location.reload();
    	    	    }, 3000);
    	    	});

    	    },
    	    error: function(err) {
    	    
    	    	bootbox.alert("Ocorreu Algum erro entrar em contato com o Administrador!");
    	    }
    	 };
		
		ONG.ajax.post(cfg);
	};
})