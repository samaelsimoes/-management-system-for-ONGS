
ONG.galeria = new Object();

$(document).ready(function(){
	ONG.galeria.validaVazio = function(campo, valor){
		var msg = "";
		if(valor == null ||  valor.trim() == ""){
			msg += " O campo: " + campo + " Está Vazio. </br>";
		}
		return msg;
	};
	ONG.galeria.cadastrarGaleria = function(){

		var msg="";

	 	msg+=ONG.galeria.validaVazio("Nome galeria: ", $("#galeria").val());
		msg+=ONG.galeria.validaVazio("Evento ", $("#eventos").val());
		msg+=ONG.galeria.validaVazio("Informação Galeria: ", $("#informacaoGaleria").val());
		msg+=ONG.galeria.validaVazio("imagem inserida: ", $("#files").val());
				
		if(msg==""||msg==null){
			
        	var newCont = new Object();
    		var evento = new Object();

    		evento.id = $("#eventos").val();
    		newCont.id_evento = evento;
	    	newCont.nome = $("#galeria").val();
	    	newCont.informacaoGaleria = $("#informacaoGaleria").val();
			
			if(newCont != "" && newCont != null){
				
				var formData = new FormData($("#formulario")[0]);
		    	    	
				$.ajax({
		
					url: ONG.contextPath + "/rest/upload/",
					type: "POST",
					data: formData,
					contentType: false,
					processData: false,
					
					success: function(data){
						
						newCont.nomeImagem = data;
						
						$("#result").html("");
						$("<img>").attr("src",ONG.contextPath +"/rest/download?file="+data).appendTo("#result");
						
						if(data != "" & data != null){
							ONG.galeria.cadastraGaleria(newCont);
						}
					},
					error: function(err){	
						bootbox.alert("Ocorreu Algum erro entrar em contato com o Administrador! " + err);
					}
				});
			}	
		}else{
			bootbox.alert("Caro usuário pro gentileza preencher os seguintes campos: <br>"+msg);
		}
	}
	
	ONG.galeria.cadastraGaleria = function(newCont){
		
		var cfg = {
	    		 
    		type: "POST",
    		url: ONG.contextPath + "/rest/galeria/adicionar", 
    		data: newCont,
    		     		 
    	    success: function(msg) {    	    	
    	    	var dialog = bootbox.dialog({  
    	    		title: 'Cadastrando Galeria',
    	    	    message: '<p><i class="fa fa-spin fa-spinner"></i> Carregando...</p>'
    	    	});
    	    	dialog.init(function(){
    	    	    setTimeout(function(){
    	    	        dialog.find('.bootbox-body').html('Galeria cadastrado com sucesso');
    	    	    }, 2000);
    	    	    setTimeout(function(){
    	    	    	location.reload();
    	    	    }, 5000);
    	    	});

    	    	$(this).dialog("close");
    	    },
    	    error: function(err) {
    	    
    	    	bootbox.alert("Ocorreu Algum erro entrar em contato com o Administrador!");
    	    }
    	 };
		
		ONG.ajax.post(cfg);
	}	
	
	 // ================================================================================================================================================================================================
	// ================================================================================================================================================================================================

	ONG.galeria.chamaEvento = function(){

		var valorBusca = "";

		if(valorBusca == ""){
			valorBusca = null;
		}

		var valorLista = 1;
		
		var cfg ={
							
			url:  ONG.contextPath + "/rest/eventos/consultarEvento/" + valorBusca+ "/" + valorLista,
			success: function(listaEventos){
										
					
				if(listaEventos == null || listaEventos == ""){

					bootbox.alert(" Evento não cadastrado,"  +  
						" para continuar este processo por gentileza cadastrar algum evento no sistema");
				}else{

					ONG.galeria.montarSelect(listaEventos);
				}
			},
			error: function(err){
				
				bootbox.alert("Erro ao Buscar Eventos, entrar em contato com o Administrador!");
			}
		};
		
		ONG.ajax.get(cfg);
	}

	ONG.galeria.montarSelect = function(listaEventos){
		 if(listaEventos != undefined && listaEventos.length > 0 && listaEventos[0].id != undefined){
			for(var i = 0; i < listaEventos.length; i++){
				 
				var option = $("<option></option>").appendTo($('#eventos'));
				option.attr("value", listaEventos[i].id);
				option.html( listaEventos[i].nomeEvento + " " + ", " + " Data Evento: " + listaEventos[i].dataEvento);
			}
		}
	}
});
