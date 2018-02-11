
ONG.contato = new Object();
ONG.login = {};

$(document).ready(function(){
	
	ONG.contato.login = function(){
		
		var msg= "";
		
		msg+=ONG.contato.validaVaziu("Valida Login ", $("#login").val());
		msg+=ONG.contato.validaVaziu("Valida Senha ", $("#senha").val());	

		var criptbase64=btoa($("#senha").val());
		
		$("#senha1").val(criptbase64);
		
		if(msg != ""){
            bootbox.alert(msg);
		}else{
			$.ajax({	
                type:"POST",				
                url: ONG.contextPath + "/ServletLogin",  
                data: $("#formularioLogin").serialize(),
                
                success: function(msg){
                	var dialog = bootbox.dialog({
        			    title: 'Verificando Dados',
        			    message: '<p><i class="fa fa-spin fa-spinner"></i> Carregando...</p>'
        			});
                	dialog.init(function(){
        	            setTimeout(function(){
        	            	dialog.find('.bootbox-body').html("Acesso Permitido!");  
        	            }, 3000);
        	        });
                	
                	var intervalo = window.setInterval(function() {	}, 50);

                	window.setTimeout(function() {	
                	    clearInterval(intervalo);
            			$(location).attr('href', '../privado/paginausuario.html');
                	}, 5000);
                },
                
                error: function(err){
            		console.log(err);
                	bootbox.alert("Login Invalido ! " );
                }
        	});	
		}
	};
	
	ONG.contato.validaVaziu = function(campo, valor){
		var msg = "";
		if(valor == null ||  valor.trim() == ""){
			msg += " O campo: " + campo + " Está Vazio. </br>";
		}
		return msg;
	};
});




