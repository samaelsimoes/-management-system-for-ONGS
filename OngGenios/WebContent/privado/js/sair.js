ONG.sessao = new Object();

$(document).ready(function(){
	ONG.sessao.sair = function(){	
		$.ajax({
    		
            type:"POST",
              					
            url: ONG.contextPath + "/ServletLogin",  
            data: $("#formularioLogin").serialize(),
            
            success: function(msg){
            	
            	var intervalo = window.setInterval(function() {	}, 50);

            	window.setTimeout(function() {
            		
            	    clearInterval(intervalo);
        			$(location).attr('href', '../privado/paginausuario.html');
            	}, 3000);
            	
            },
            
            error: function(err){
            	
            	bootbox.alert("Login Invalido ! 123 " + err);
            }
    	});	
	}
});
