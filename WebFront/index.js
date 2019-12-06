/**
 * User login
 */
var button = document.getElementById("buttonLogin").addEventListener(
		'click',
		function(e) {
			e.preventDefault();
			var login = document.getElementById("idLogin").value;
			var senha = document.getElementById("passwordLogin").value;
			var persistToken;
			var persist = document.getElementsByName("persistToken")[0];
			if (persist.type == "checkbox" && persist.checked == true) {
				persistToken = true;
			} else if (persist.type == "checkbox" && persist.checked == false) {
				persistToken = false;
			}
			var xmlhttp = new XMLHttpRequest();
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4) {
//					console.log("readyState == 4");
					if (xmlhttp.status == 200) {
						window.location.replace("./pages/logged.html");
					} else {
						window.location.href = "./pages/error.html";
					}
				}
			}
			xmlhttp.open("POST",
					"http://localhost:8080/CoquetailsGateway/api/users/login");
			xmlhttp.setRequestHeader("Content-Type", "application/json");
			var data = {
				login : login,
				password : senha,
				persistToken : persistToken
			};
			xmlhttp.send(JSON.stringify(data));
		});
