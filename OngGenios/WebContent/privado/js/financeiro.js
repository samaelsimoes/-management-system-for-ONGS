ONG.financeiro = new Object();

$(document).ready(function(){
	ONG.financeiro.buscaMembro = function(){
 		var cfg = {
	    		 
    	    type: "GET",
    	    url: ONG.contextPath + "/rest/membroLogado/buscar",  
    	    success: function(msg) {
    		   ONG.financeiro.retorno(msg);
    	    },
    	    error: function(err) {
    			bootbox.alert(" Erro ao localizar o membro logando." + " Entrar em contato com o Adm do sistema ");    	   
    	    }
    	};
		ONG.ajax.get(cfg);
	}
	ONG.financeiro.retorno = function(msg){
		if(msg.nome == null){
			bootbox.alert("<br> <br> Erro no sistema por gentileza SAIR e Entrar novamente!"); 
		}else{
			$('#nome').val(msg.nome + " " + msg.sobreNome);
			$('#id').val(msg.id);
		}	
	}

	ONG.financeiro.deposito = function(){

		$("#nome").val()
		$("#deposito").val()

		var alerta="";

		if($("#nome").val() == null || $("#nome").val() ==""){
			alerta += " Responsavel invalido <br>";
		}
		if($("#deposito").val() == null || $("#deposito").val() == ""){
			alerta += " Campo Deposito obrigatorio <br>";
		}
		if(alerta == null || alerta == ""){
		
			momentoAtual = new Date();	
				
			var vhora = momentoAtual.getHours();
			var vminuto = momentoAtual.getMinutes();
			var vsegundo = momentoAtual.getSeconds();
			var vdia = momentoAtual.getDate();
			var vmes = momentoAtual.getMonth() + 1;
			var vano = momentoAtual.getFullYear();
			
			if (vdia < 10){ vdia = "0" + vdia;}
			if (vmes < 10){ vmes = "0" + vmes;}
			if (vhora < 10){ vhora = "0" + vhora;}
			if (vminuto < 10){ vminuto = "0" + vminuto;}
			if (vsegundo < 10){ vsegundo = "0" + vsegundo;}

			dataFormat =  vano + "-" + vmes + "-" + vdia;
			horaFormat = vhora + ":" + vminuto + ":" + vsegundo;
			horario =  dataFormat + " " + horaFormat ;
			
			var responsavel = $("#nome").val();
			var newCont = new Object();
			var responsavel = new Object();

			responsavel.id = $("#id").val();		
			newCont.id_responsavel = responsavel;
			newCont.dataDeposito = horario;
			var deposito = $("#deposito").val();
            newCont.deposito= deposito.toString().replace(",", ".");
			
			ONG.financeiro.depositar(newCont);
		}else{

			bootbox.alert(alerta);
		}
	}

	ONG.financeiro.depositar = function(newCont){

		var cfg = {
	    		 
    		type: "POST",
    		url: ONG.contextPath + "/rest/financeiro/depositar", 
    		data: newCont,
    		     		 
    	    success: function(msg) {
    	    	bootbox.alert(msg);
	    	    setTimeout(function(){
	    	         location.reload();
	    	    }, 3000);
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
	ONG.financeiro.saque = function(){

		var alerta="";
		
		if($("#saque").val() == null || $("#saque").val() == ""){
			alerta += "Campo saque Obrigatório" + "<br>";
		}
		if($("#mensagem").val() == null || $("#mensagem").val() == ""){
			alerta += "Campo Motivo obrigatório" + "<br>";
		}

		if(alerta == ""){
			momentoAtual = new Date();	
				
			var vhora = momentoAtual.getHours();
			var vminuto = momentoAtual.getMinutes();
			var vsegundo = momentoAtual.getSeconds();
			
			var vdia = momentoAtual.getDate();
			var vmes = momentoAtual.getMonth() + 1;
			var vano = momentoAtual.getFullYear();
			
			if (vdia < 10){ vdia = "0" + vdia;}
			if (vmes < 10){ vmes = "0" + vmes;}
			if (vhora < 10){ vhora = "0" + vhora;}
			if (vminuto < 10){ vminuto = "0" + vminuto;}
			if (vsegundo < 10){ vsegundo = "0" + vsegundo;}

			dataFormat = vdia + "-" + vmes + "-" + vano;
			horaFormat = vhora + ":" + vminuto + ":" + vsegundo;
			dataHorario = dataFormat + " " +horaFormat;

			var responsavel = new Object();
			var newCont = new Object();
			responsavel.id = $("#id").val();
			
			newCont.id_responsavel = responsavel;
			newCont.dataSaque = dataHorario;
			var saque = $("#saque").val();
            newCont.saque= saque.toString().replace(",", ".");
			newCont.motivo = $("#mensagem").val();

			ONG.financeiro.sacar(newCont);F
		}else{
			bootbox.alert(alerta);
		}
	}

	ONG.financeiro.sacar = function(newCont){
		var cfg = {	 
    		type: "POST",
    		url: ONG.contextPath + "/rest/financeiro/sacar", 
    		data: newCont,   		 
    	    success: function(msg) {
    	    	bootbox.alert(msg);
    	    	setTimeout(function(){
	    	         location.reload();
	    	    }, 3000);
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
})
