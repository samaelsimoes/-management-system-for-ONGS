
ONG.montar = new Object();

ONG.montarTabela = {};

$(document).ready(function(){
		
	ONG.montar.montarTabela = function(){
		
	    var valorBusca = $("#consultarContato").val();
	    var valorLista = $("#lista").val();
	    
		if(valorLista==0){
			bootbox.alert("Escolha uma das categorias");
		}
	    if(valorLista==1){
		    ONG.montar.montaTabelaBeneficiarios(undefined, valorBusca);
		}
	    if(valorLista==2){
			ONG.montar.montaTabelaMembros(undefined, valorBusca);
		}	    
	}		
	
	ONG.montar.montaTabelaBeneficiarios = function(listaBeneficiario, valorBusca){
				
		var html = "<table class='table'>";
		
		html += "<tr><p></p><p> Beneficiarios </p>  </br><th> Nome </th><th> SobreNome </th><th> Telefone Residencial </th>" +
				"<th> Telefone Comercial </th><th> Renda </th> <th> Tamanho Familia </th></tr>";					

		if(listaBeneficiario != undefined && listaBeneficiario.length > 0 && listaBeneficiario[0].id != undefined){
			for(var i = 0; i < listaBeneficiario.length; i++){
				  
				html += "<tr>";
					html += "<td>" + listaBeneficiario[i].nome + "</td>";
					html += "<td>" + listaBeneficiario[i].sobreNome + "</td>";
					html += "<td>" + listaBeneficiario[i].telCelular + "</td>";
					html += "<td>" + listaBeneficiario[i].telResidencial + "</td>";

					var totalrenda = listaBeneficiario[i].renda.toFixed(2);

					html += "<td>" + totalrenda.toString().replace(".", ",") + "</td>";
					html += "<td>" + listaBeneficiario[i].tamanhoFamilhia + "</td>";
					html += "<td>";
					html += "<button type='button' class='btn btn-pencil' onclick='ONG.montar.pesquisarPorIdBene("+listaBeneficiario[i].id+")'>Editar</button>"
					html += "</td>";
					html += "<td>";
					html += "<button type='button'class='btn btn-trash' onclick='ONG.montar.confirmarExclusaoBene("+listaBeneficiario[i].id+")'>Excluir</button>"
					html += "</td>";
				html += "</tr>";  
			}			  
		  }else{
			  if(listaBeneficiario == undefined || (listaBeneficiario != undefined && listaBeneficiario.length > 0)){
					
					if(valorBusca == ""){						
						valorBusca = null;
					}

					var cfg ={
							
						url:ONG.contextPath+"/rest/beneficiarios/consultarNome/"+valorBusca,
						
						success: function(listaBeneficiario){
													
							console.log(listaBeneficiario);
							ONG.montar.montaTabelaBeneficiarios(listaBeneficiario);
						},
						
						error: function(err){
							
							bootbox.alert("Erro ao Buscar Beneficiarios, entrar em contato com o Administrador!");
						}
					};
					
					ONG.ajax.get(cfg);
				}else{
					
					html += "<tr><td colspan='3'>Nenhum registro encontrado</td></tr>";
				}
		  }
		  
		  html +="</table>";
		  $("#resultadoContatos").html(html);
	}

	ONG.montar.montaTabelaBeneficiarios(undefined, "");
	
	ONG.montar.pesquisarPorIdBene=function(id){
				  
		var url=ONG.contextPath+"/rest/beneficiarios/consultarPorId/"+id;	
		
		$.ajax({
			  
			type:'GET',
			url: url,
			data: JSON.stringify(id),
			dataType:'Json',
			contentType:'application/json',
				  
			success:function(data){
				
				ONG.montar.formularioBene(data);
			},
			
			error: function(err){
				
				bootbox.alert("Ocorreu erro ao chamar os dados do Beneficiario para o Formulário ");
			}
		});
	}
	ONG.montar.formularioBene = function(data){
		$("#form").load("formularios/formBeneficiario.html",function(){  
			$('#resultadoContatos').html("");
		    if(data != undefined && data != null){
			  
				$("#id").val(data.id);
				$('#nome').val(data.nome);
				$('#sobrenome').val(data.sobreNome);
				$('#email').val(data.email);
				$('#telefone1').val(data.telResidencial);
				$('#telefone2').val(data.telCelular);
				$('#endereco').val(data.endereco);
				$('#datanascimento').val(data.dataNascimento);
				$('#cep').val(data.cep);

				var renda =data.renda.toFixed(2);
           
				$('#renda').val(renda.toString().replace(".", ","));
				$('#tamanhoFamilia').val(data.tamanhoFamilhia);
			}
		});
	}
	

	ONG.montar.validaVazio=function(campo, valor){

		var msg = "";

		if(valor == null ||  valor.trim() == ""){
			msg += " - campo: " + campo + " Está Vazio. </br>";
		}
		return msg;
	};

	ONG.montar.salvarBenEdit = function(){

		var msg="";
		var expressao="";

		msg+=ONG.montar.validaVazio("Nome ", $("#nome").val());
		msg+=ONG.montar.validaVazio("Sobre nome ", $("#sobrenome").val());
		msg+=ONG.montar.validaVazio("Email ", $("#email").val());
		msg+=ONG.montar.validaVazio("Telefone ", $("#telefone1").val());
		msg+=ONG.montar.validaVazio("Telefone2 ", $("#telefone2").val());
		msg+=ONG.montar.validaVazio("Endereco ", $("#endereco").val());
		msg+=ONG.montar.validaVazio("Data Nascimento ", $("#datanascimento").val());
		msg+=ONG.montar.validaVazio("Cep ", $('#cep').val());
		msg+=ONG.montar.validaVazio("Renda ", $('#renda').val());
		msg+=ONG.montar.validaVazio("Quantidade Familia ", $('#tamanhoFamilia').val());

		if(msg == ""){

			if(!$("#telefone1").val().match(/^\d{10,13}$/)){		
			expressao += "Telefone invalido ! </br> " + "</br>"+
		     		"ex: 4791088783" + "</br>" + "</br>";
			}
			if(!$("#telefone2").val().match(/^\d{10,13}$/)){	
				expressao+="Telefone invalido ! </br> " + "</br>"+
			     		"ex: 4791088783" + "</br>" + "</br>";
			}
			if(!$("#renda").val().match(/^[0-9]{0,15}[,]{0,1}[0-9]{0,4}$/)){
	            expressao+=" Gentileza informar a renda em formato numerico " + "<br>" + "<br>";
	        }
			if($("#email").val().indexOf("@") == -1 || //valida se existe o @
	    		$("#email").val().indexOf(".") == -1 || //valida se existe o .
	    		$("#email").val().indexOf("@") == 0 || //valida se tem texto antes do @
	    		$("#email").val().lastIndexOf(".") + 1 == email.length || //valida se tem texto depois do ponto
	    		($("#email").val().indexOf("@") + 1 == $("#email").val().indexOf("."))){ //valida se tem texto entre o @ e o .{
				expressao += "E-mail invalido" +"</br>"
				+ "ex: teste_@teste.com.br"
				document.getElementById("email").focus();
	    	}
	    	if(!$("#cep").val().match(/^[0-9]{8}$/)){
	    		expressao+=" cep invalido por gentileza informar cep correto" + "<br>";
	    	}
	    	 if(!$("#tamanhoFamilia").val().match(/^[0-9]{01,03}$/)){
	            expressao+=" Gentileza informar tamanho total da familia por exemplo '4' "+ "<br>" + "<br>";
	        }

			if(expressao =="" || expressao == null){
				var newCont = new Object();

		    	newCont.id=$("#id").val();
				newCont.nome=$("#nome").val();
				newCont.sobreNome=$("#sobrenome").val();
				newCont.email=$("#email").val();
				newCont.telResidencial=$("#telefone1").val();
				newCont.telCelular=$("#telefone2").val();
				newCont.endereco=$("#endereco").val();
				newCont.dataNascimento=$("#datanascimento").val();
				newCont.cep=$('#cep').val();

				var renda = $("#renda").val();

				newCont.renda=renda.toString().replace(",", ".")
				newCont.tamanhoFamilia=$('#tamanhoFamilia').val();
				
				ONG.montar.editarbeneficiario(newCont);
			}else{
				bootbox.alert("Caro usuário gentileza ajustar os seguintes campos "+ expressao);
			}
		}else{
			bootbox.alert("Caro usuário ajustar os seguintes campos: " + msg);
		}
	}
	
	ONG.montar.editarbeneficiario = function(newCont){
		
		$.ajax({
			
			type: 'PUT',
			url: ONG.contextPath + "/rest/beneficiarios/editar/",
			data: JSON.stringify(newCont),
			
			dataType:'text',
			contentType:'application/json',
			
			success:function(data){				
				bootbox.alert(data);
				setTimeout(function(){
	    	    	location.reload();
	    	    }, 5000);
			},
			error: function(err){				
				bootbox.alert( err.responseText); 
			}
		});
	}
	
	ONG.montar.confirmarExclusaoBene = function(id){
		bootbox.confirm({

		    message: "Você Desejea EXCLUIr este beneficiario?",
		    buttons: {
		        confirm: {

		            label: 'Sim',
		            className: 'btn-success',
		        },
		        cancel: {

		            label: 'Não',
		            className: 'btn-danger'
		        }
		    },
		    
		    callback: function (result) {		        
		        if(result == true){
		        	
		            ONG.montar.excluirBeneficiario(id);
		        }
		    }
		});		
	}


	ONG.montar.excluirBeneficiario = function(id){
		var cfg={
			
			url:  ONG.contextPath + "/rest/beneficiarios/inativar/"+id,
			success: function (data){
				
				bootbox.alert(data);

            	setTimeout(function(){
    	    	    location.reload();
    	    	}, 4000);
			},
			error: function (err){				
				bootbox.alert("Erro ao deletar o contato: " + err.responseText);
			}
		};
		ONG.ajax.delet(cfg);
	}

	// ==========================================================
   // ==============================================================================
	
	ONG.montar.montaTabelaMembros = function(listaMembro, valorBusca){
		
		var html = "<p> Membros</p>  </br><table class='table'>";
		html += "<tr><th>Nome</th><th>SobreNome</th><th>Email</th><th>Telefone Residencial</th><th>Telefone Comercial</th><th>Login</th></tr>";	
		
	  if(listaMembro != undefined && listaMembro.length > 0 && listaMembro[0].id != undefined){
	
		    for(var i = 0; i < listaMembro.length; i++){
			  
				html += "<tr>";

					html += "<td>" + listaMembro[i].nome + "</td>";
					html += "<td>" + listaMembro[i].sobreNome + "</td>";
					html += "<td>" + listaMembro[i].email + "</td>";
					html += "<td>" + listaMembro[i].telCelular + "</td>";
					html += "<td>" + listaMembro[i].telResidencial + "</td>";
					html += "<td>" + listaMembro[i].login + "</td>";
					html += "<td" + listaMembro[i]. cep +"</td>";

					html += "<td>";
					html += "<button type='button' class='btn btn-pencil' onclick='ONG.montar.pesquisarPorIdMemb("+listaMembro[i].id+")'>Editar</button>"
					html += "</td>";

					html += "<td>";
					html += "<button type='button'class='btn btn-trash' onclick='ONG.montar.confirmarExclusaoMemb("+listaMembro[i].id+")'>Excluir</button>"
					html += "</td>";

				html += "</tr>";  
		    }
	    }else{
		  if(listaMembro == undefined || (listaMembro != undefined && listaMembro.length > 0)){
				if(valorBusca==""){
					valorBusca=null;
				}
				var cfg ={
						
					url: ONG.contextPath + "/rest/membros/consultarNome/"+valorBusca,
					success: function(listaMembro){
						
						console.log(listaMembro);
						ONG.montar.montaTabelaMembros(listaMembro);
					},
					error: function(err){
						bootbox.alert("Erro ao consultar os contatos: "+err.responseText);
					}
				};
				
				ONG.ajax.get(cfg);
			}else{
				
				html += "<tr><td colspan='3'>Nenhum registro encontrado</td></tr>";
			}
	  }
		  
	  	html +="</table>";
	  	$("#resultadoContatos").html(html);
	}
	
	ONG.montar.montaTabelaMembros(undefined, "");

	ONG.montar.pesquisarPorIdMemb = function(id){
		
		var url = ONG.contextPath + "/rest/membros/consultarPorId/" + id;
		  $.ajax({
			  
			type:'GET',
			url: url,
			data: JSON.stringify(id),
			dataType:'Json',
			contentType:'application/json',
				  
			success:function(data){
				ONG.montar.formularioMembro(data);
			},
			
			error: function(err){
				
				bootbox.alert(err.responseText);
			}
		});
	}
	
	ONG.montar.formularioMembro = function(data){
		
		$("#form").load("formularios/formMembros.html",function(){  
			$('#resultadoContatos').html("");
			if(data != undefined && data != null){
				  
			  $("#id").val(data.id);
			  $('#nome').val(data.nome);
			  $('#sobrenome').val(data.sobreNome);
			  $('#email').val(data.email);
			  $('#telefone1').val(data.telCelular);
			  $('#telefone2').val(data.telResidencial);
			  $('#endereco').val(data.endereco);
			  $('#datanascimento').val(data.dataNascimento);
			  $('#login').val(data.login.replace( ".OngGenios" , ""));
			}
		});
	}
	
	ONG.montar.salvarMembrodit = function(){

		bootbox.confirm({
		    message: "Caro usuário certifique que no campo Login não está trazendo .OngGenios, se sim retirar pois está informação vem automática. ",
		    buttons: {
		        confirm: {
		            label: 'Editar ',
		            className: 'btn-success'
		        },
		        cancel: {
		            label: 'Irei verificar ',
		            className: 'btn-danger'
		        }
		    },
		    callback: function (result) {
		       if(result == true){

		       		var msg="";
					var expressao="";

					msg+=ONG.montar.validaVazio("Nome ",$("#nome").val());
					msg+=ONG.montar.validaVazio("SobreNome ", $("#sobrenome").val());
					msg+=ONG.montar.validaVazio("Email ", $("#email").val());
					msg+=ONG.montar.validaVazio("Telefone Celular ", $("#telefone1").val());
					msg+=ONG.montar.validaVazio("Telefone Residencial ", $("#telefone1").val());
					msg+=ONG.montar.validaVazio("Endereco ", $("#endereco").val());
					msg+=ONG.montar.validaVazio("Login ", $("#login").val());
					msg+=ONG.montar.validaVazio("Data Nascimento ", $("#datanascimento").val());

					/*if($("#login").val().indexOf("OngGenios") == -1  ||  $("#email").val().indexOf("OngGenios") == -1){ valida se existe o OngGenios 
			    			
						msg += "Login Invalido para cadastro" +"</br>"
						+ "ex: nome.OngGenios " +
						"</br> " + "Obrigatorio    'OngGenios' "
						document.getElementById("login").focus();
			    	}*/		   	   

					if(msg==""){

						if($("#email").val().indexOf("@") == -1 || //valida se existe o @
				    		$("#email").val().indexOf(".") == -1 || //valida se existe o .

				    		$("#email").val().indexOf("@") == 0 || //valida se tem texto antes do @
				    		$("#email").val().lastIndexOf(".") + 1 == email.length || //valida se tem texto depois do ponto
				    		($("#email").val().indexOf("@") + 1 == $("#email").val().indexOf("."))){ //valida se tem texto entre o @ e o .{
							expressao += "E-mail invalido" +"</br>"
							+ "ex: teste_@teste.com.br"
							document.getElementById("email").focus();
				    	}
				    	if(!$("#telefone1").val().match(/^\d{10,13}$/)){	
						    	
							expressao += "Telefone invalido ! </br> " + "</br>"+
						     		"ex: 4791088783" + "</br>" + "</br>";
						}
				    	if(!$("#telefone2").val().match(/^\d{10,13}$/)){	
						    	
							expressao += "Telefone invalido ! </br> " + "</br>"+
						     		"ex: 4791088783" + "</br>" + "</br>";
						}

						if(expressao == "" || expressao == null){

							
							var	base64crip=btoa($("#senha").val());
							
							var editadoMembro;
							var login=$("#login").val() + ".OngGenios";

							if(base64crip!="" || base64crip!= null){
								editadoMembro = {
										
									id: $("#id").val(),
									nome: $("#nome").val(),
									sobreNome: $("#sobrenome").val(),
									email: $("#email").val(),
									telCelular: $("#telefone1").val(),
									telResidencial: $("#telefone1").val(),
									endereco: $("#endereco").val(),
									login: login,
									dataNascimento: $("#datanascimento").val(),
									senha: base64crip,
								};
							}else if(base64crip=="" || base64crip==null){
								editadoMembro={
										
									id: $("#id").val(),
									nome: $("#nome").val(),
									sobreNome: $("#sobrenome").val(),
									email: $("#email").val(),
									telCelular: $("#telefone1").val(),
									telResidencial: $("#telefone1").val(),
									endereco: $("#endereco").val(),
									login: login,
									dataNascimento: $("#datanascimento").val(),
								};
							}

							ONG.montar.editarMembros(editadoMembro);
						}else{
							bootbox.alert("Caro usuário gentileza ajustar os seguintes campos: <br>" + expressao);
						}
					}else{
						bootbox.alert(msg);
					}
		        }
		    }
		});
	}
	
	ONG.montar.editarMembros = function(editadoMembro){
		
		$.ajax({
			
			type: 'PUT',
			url:ONG.contextPath + "/rest/membros/editar/",
			data: JSON.stringify(editadoMembro),
			
			dataType:'text',
			contentType:'application/json',
			
			success:function(data){
				bootbox.alert(data);
				setTimeout(function(){
	    	    	location.reload();
	    	    }, 5000);
			},
			error: function(err){
				bootbox.alert(err.responseText); 
			}
		});
	}

	ONG.montar.confirmarExclusaoMemb = function(id){

		bootbox.confirm({
		    message: "Você deseja Excluir este Membro?",
		    buttons: {
		        confirm: {
		            label: 'Sim',
		            className: 'btn-success',
		        },
		        cancel: {
		            label: 'Não',
		            className: 'btn-danger'
		        }
		    },
		    callback: function (result) {
		    	
		        if(result == true){
		        	ONG.montar.excluirMembro(id);
		        }
		    }
		});		
	}
	
	ONG.montar.excluirMembro = function(id){
		
		var cfg = {
				
			url:  ONG.contextPath + "/rest/membros/inativar/"+id,
			success: function (data){

				bootbox.alert(data);
				setTimeout(function(){
	    	    	location.reload();
	    	    }, 4000);
			},
			error: function (err){
				bootbox.alert(err.responseText);
			}
		};
		ONG.ajax.delet(cfg);
	}
});



