ONG.contato = new Object();
ONG.sonho = {};

$(document).ready(function(){
	
	ONG.contato.buscarMembroEBeneficiario = function(){
				
		var nomeMembro = $("#nome").val();
	    var nomeBene = $("#beneficiario").val();
	 
	    if(nomeMembro.trim()=="" || nomeMembro==null){
	    	ONG.contato.buscaMembro();
	    } 
	    if(nomeBene==null){    	
	    	ONG.contato.buscaBene();
	    }
	}
	
	ONG.contato.buscaMembro=function(){
		
		var cfg={   

    	   type: "GET",
    	   url: ONG.contextPath + "/rest/membroLogado/buscar", 	 

    	   success: function(msg) {    	
    		   	ONG.contato.retorno(msg);
    	   },
    	   error: function(err) {    	   	
    			bootbox.alert(" Erro ao localizar o membro logando." + " Entrar em contato com o Administrador do sistema! ");    	   
    	   }
    	};
		
		ONG.ajax.get(cfg);
	}
	
	ONG.contato.retorno = function(msg){
		if(msg == null){
			
			bootbox.alert("<br> <br> Erro no sistema por gentileza SAIR e Entrar novamente se persistir entrar em contato com o Administrador!"); 
		}else{
			
			$('#nome').val(msg.nome + " " + msg.sobreNome);
			$('#id').val(msg.id);
		}	
	}
	
	ONG.contato.buscaBene = function(){
		
		var valorBusca = null;

		var cfg = {
				
			url: ONG.contextPath + "/rest/beneficiarios/consultarNome/"+valorBusca,
			
			success: function(listaBeneficiario){
				if(listaBeneficiario != null && listaBeneficiario != "" ){				
					ONG.contato.montarSelect(listaBeneficiario);
				}else{
					bootbox.alert("Sem beneficiario cadastrado no Sistema por gentileza " +
						" Cadastrar Beneficiarios para continuar este processo.")
				}
			},
			error: function(err){
				bootbox.alert("Erro ao Buscar Beneficiarios, entrar em contato com o Administrador!");
			}
		};	
		ONG.ajax.get(cfg);
	}
	
	ONG.contato.montarSelect = function(listaBeneficiario){
		
		 if(listaBeneficiario!= undefined && listaBeneficiario.length > 0 && listaBeneficiario[0].id != undefined){
			for(var i=0; i < listaBeneficiario.length; i++){
				 
				var option=$("<option></option>").appendTo($('#beneficiario'));
				option.attr("value", listaBeneficiario[i].id);
				option.html(listaBeneficiario[i].nome + " " + listaBeneficiario[i].sobreNome);

				ONG.contato.Buscaprioridade();
			}
		}
	}

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
	 // =========================================================================================================================
	// =============================================================================================================================
	
	ONG.contato.validaVazio = function(campo, valor){

		var msg = "";

		if(valor == null ||  valor.trim() == ""){

			msg += " - campo: " + campo + " Está Vazio. </br>";
		}

		return msg;
	};

	ONG.contato.sonho = function(){
		
		var msg = "";

		msg+=ONG.contato.validaVazio("Nome Responsavel ", $("#id").val());
		msg+=ONG.contato.validaVazio("Beneficiario ", $("#beneficiario").val());
		msg+=ONG.contato.validaVazio("Sonho ", $("#sonho").val());
		msg+=ONG.contato.validaVazio("Prioridade ", $("#prioridade").val());

		if(msg == ""){

			var newCont = new Object();
			var doacao = new Object();
			var beneficiario = new Object();
			var responsavel = new Object();
			var prioridade =new Object();

			beneficiario.id = $("#beneficiario").val();
			newCont.beneficiario = beneficiario;
			newCont.sonho = $("#sonho").val();
			responsavel.id = $("#id").val();
			newCont.responsavel = responsavel;
			prioridade.id =  $("#prioridade").val();
			newCont.prioridade =  prioridade;
			
			ONG.contato.cadastrar(newCont);

		}else if(msg!=""){
			bootbox.alert("Caro usuário, gentileza verificar os seguintes campos: <br>" + msg);
		}
	};

	ONG.contato.cadastrar = function(newCont){

		var cfg = {
	    		 
    		type: "POST",
    		url: ONG.contextPath + "/rest/sonhos/adicionar", 
    		data: newCont,
    		     		 
    	    success: function(msg) {

    	    	var dialog = bootbox.dialog({
    	    		
    	    	    title: 'Cadastrando Sonho',
    	    	    message: '<p><i class="fa fa-spin fa-spinner"></i> Carregando...</p>'
    	    	});
    	    	
    	    	dialog.init(function(){
    	    		
    	    	    setTimeout(function(){

    	    	        dialog.find('.bootbox-body').html('Sonho cadastrado com sucesso');
    	    	    }, 2000);
    	    	    setTimeout(function(){
    	    	         location.reload();
    	    	    }, 3000);
    	    	});
    	    	
    	    	$(this).dialog("close");
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