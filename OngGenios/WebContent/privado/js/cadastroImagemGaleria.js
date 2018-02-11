
ONG.galeria = new Object();

$(document).ready(function(){
	ONG.galeria.validaVazio=function(campo, valor){

		var msg="";

		if(valor==null||valor.trim()==""){
			msg+=" - Campo: "+campo+" Está Vazio. </br>";
		}

		return msg;
	};

	ONG.galeria.cadastrarImagem = function(){

		var msg="";
		
		msg+=ONG.galeria.validaVazio("ID galeria: ", $("#galeria").val());
		msg+=ONG.galeria.validaVazio("Evento", $("#informacaoImagem").val());
		msg+=ONG.galeria.validaVazio("Imagem ", $("#file").val());

		if(msg==""||msg==null){
			
        	var newCont=new Object();

	    	newCont.galeria_id=$("#galeria").val();
	    	newCont.informacaoImagem=$("#informacaoImagem").val()

			var formData=new FormData($("#formulario")[0]);
	    	    	
			$.ajax({
	
				url: ONG.contextPath + "/rest/uploadGaleria/",
				type: "POST",
				data: formData,
				contentType: false,
				processData: false,
				
				success: function(data){
					
					newCont.nome = data;
					
					$("#result").html("");
					$("<img>").attr("src",ONG.contextPath +"/rest/DownloadGaleriaImagem?file="+data).appendTo("#result");
					
					if(data != "" & data != null){
						
						ONG.galeria.cadastroImagemGaleria(newCont);
					}
				},
				error: function(err){			
					bootbox.alert("Ocorreu Algum erro entrar em contato com o Administrador! " + err);
				}
			});	
		}else{
			bootbox.alert("Caro usuário Gentileza ajustar os seguintes campos: <br>"+msg);
		}
	}
	
	ONG.galeria.cadastroImagemGaleria = function(newCont){

		var cfg={
	    		 
    		type: "POST",
    		url: ONG.contextPath + "/rest/imagem/adicionar", 
    		data: newCont,
    		     		 
    	    success: function(msg) {
    	    	
    	    	var dialog = bootbox.dialog({
    	    	    
    	    		title: 'Cadastrando Imagem',
    	    	    message: '<p><i class="fa fa-spin fa-spinner"></i> Carregando...</p>'
    	    	});
    	    	dialog.init(function(){
    	    	    setTimeout(function(){
    	    	    	
    	    	        dialog.find('.bootbox-body').html('Imagem da Galeria cadastrado com sucesso!');
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

	ONG.galeria.chamaGaleria = function(){

		var valorBusca="";

		if(valorBusca==""){			
			valorBusca=null;
		}

		var valorLista=1;
		
		var cfg={
							
			url:  ONG.contextPath + "/rest/galeria/consultarGaleria/" + valorBusca+ "/" + valorLista,
			success: function(listaGaleria){
				if(listaGaleria!=null || listaGaleria !=""){					
					ONG.galeria.montarSelect(listaGaleria);
				}else{

					bootbox.alert("Caro usuário por gentileza cadastre alguma galeria para continuar este cadastro");
				}
			},
			error: function(err){
				bootbox.alert("Erro ao Buscar Eventos, entrar em contato com o Administrador!");
			}
		};
		
		ONG.ajax.get(cfg);
	}

	ONG.galeria.montarSelect = function(listaGaleria){
		
		if(listaGaleria != undefined && listaGaleria.length > 0 && listaGaleria[0].id != undefined){	 
			for(var i = 0; i < listaGaleria.length; i++){
				 
				var option = $("<option></option>").appendTo($('#galeria'));
				option.attr("value", listaGaleria[i].id);
				option.html( listaGaleria[i].nome);
			}
		}
	}
});
