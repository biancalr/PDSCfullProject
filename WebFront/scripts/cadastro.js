/**
 * Cadastro de um Usuário. Alguns campos precisam de máscaras
 */
// Nome, senha, Login, CPF, E-mail, Telefone,
var buttonCadastroUsuario = document
		.getElementById("buttonCadastroUsuario")
		.addEventListener(
				'click',
				function(e) {
					e.preventDefault();
					var name = document.getElementById("nome").value;
					var password = document.getElementById("senha").value;
					var login = document.getElementById("login").value;
					var cpf = document.getElementById("cpf").value;
					var email = document.getElementById("email").value;
					var phoneNumber = document.getElementById("telefone").value;
					var xmlhttp = new XMLHttpRequest();
					console.log("name: " + name);
					// response
					xmlhttp.onreadystatechange = function() {
						if (xmlhttp.readyState == 4) {
							if (xmlhttp.status == 200) {
//								console.log(this.responseText);
//								alert("cpf: " + cpf);
								window.location.href = "cadastroEndereco.html";
							} else {
//								console.log(this.responseText);
								window.location.href = "error.html";
							}
						}
					}
					xmlhttp
							.open("POST",
									"http://localhost:8080/CoquetailsGateway/api/users");
					xmlhttp
							.setRequestHeader("Content-Type",
									"application/json");
					var data = {
						cpf : cpf,
						email : email,
						login : login,
						name : name,
						password : password,
						phoneNumber : phoneNumber
					};
					xmlhttp.send(JSON.stringify(data));
				});
