
ONG.montar = new Object();
ONG.consultarEvento = {};

$(document).ready(function(){
	ONG.montar.buscaEventos = function(listaEvento){
		
		var valorBusca = "";

		var html = "<div class='text-center'><table class='table '> Eventos <br>";
		
		html += "<th>00 </th><th>Nome do Evento </th> <th> DataEvento </th><th> Horario </th></tr>";
									
	    if(listaEvento != undefined && listaEvento.length > 0 && listaEvento[0].id != undefined){  
		    for(var i = 0; i < listaEvento.length; i++){
			  
				html += "<tr>";

				html += "<td>" + listaEvento[i].id + "</td>";
				html += "<td>" + listaEvento[i].nomeEvento + "</td>";
				html += "<td>" + listaEvento[i].dataEvento + "</td>";
				html += "<td>" + listaEvento[i].horario + "</td>";
				html += "<td>";																		
				html += "<button type='button' class='btn btn-pencil' onclick='ONG.montar.maisInformacoes("+listaEvento[i].id+")'>Mais Informações</button>"
				html += "</td>";
				html += "</tr></div>";  
		    }
		}else{
			if(listaEvento == undefined || (listaEvento != undefined && listaEvento.length > 0)){
					
				if(valorBusca == ""){
					valorBusca = null;
				}

				var valorLista = "5";

				var cfg={

					url:  ONG.contextPath + "/rest/eventos/consultarEvento/"+ valorBusca + "/" + valorLista,
					
					success: function(listaEvento){

						
						ONG.montar.buscaEventos(listaEvento,valorLista);
					},
					
					error: function(err){
						
						bootbox.alert("Erro ao Buscar Beneficiarios dos Eventos, entrar em contato com o Administrador!");
					}
				};
				
				ONG.ajax.get(cfg);
			}else{
				
				html += "<tr><td colspan='3'>Nenhum registro encontrado</td></tr>";
			}
	    }
		  
		html +="</table>";
		$("#buscaEventos").html(html);
	}

	ONG.montar.maisInformacoes = function(id){

		var categoria = "3";

		var url = ONG.contextPath + "/rest/eventos/consultarPorId/" + id + "/" + categoria;

		$.ajax({
			  
			type:'GET',
			url: url,
			data: JSON.stringify(id),
			dataType:'Json',
			contentType:'application/json',
				  
			success:function(dadosEvento){
				ONG.montar.informacoesEvento(dadosEvento);
			},
			
			error: function(err){
				
				bootbox.alert("Ocorreu erro ao chamar os dados do Beneficiario para o Formulário ");
			}
		});
	}

	ONG.montar.informacoesEvento = function(dadosEvento){

		if(dadosEvento != "" && dadosEvento != null){

			$("#form").load("formularios/formEventos.html",function(){	
				$('#buscaEventos').html("");

				$('#id').val(dadosEvento.id);
				$('#txtEndereco').val(dadosEvento.endereco);
				$('#txtLatitude').val(dadosEvento.latitude);
				$('#txtLongitude').val(dadosEvento.longetude);
				$('#contato').val(dadosEvento.contato);
				$('#dataEvento').val(dadosEvento.dataEvento);
				$('#horario').val(dadosEvento.horario);
				$('#nomeEvento').val(dadosEvento.nomeEvento);
				$('#mensagem').val(dadosEvento.mensagem);
			});
		}else{

			bootbox.alert("Error ao listar informações Dos Eventos");
		}
	}
});
