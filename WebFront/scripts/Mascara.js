/**
 * Classe das máscaras do usuário
 */

var Mascara = function(cpf) {
	console.log("aplicando máscaras do usuário");
	this.cpf = cpf;
};

// var mascara1 = new Mascara(document.getElementById("cpf").value);

// adiciona a máscara ao cpf
Mascara.mascaraCpf = function(cpf) {
	if (Mascara.mascaraInteiro(cpf) == false) {
		event.returnValue = false;
	}
	return Mascara.formataCampo(cpf, '000.000.000-00', event);
}

// valida o cpf no lado do cliente
Mascara.validaCpf = function(cpf) {
	erro = new String;

	if (cpf.value.length == 11) {
		cpf.value = cpf.value.replace('.', '');
		cpf.value = cpf.value.replace('.', '');
		cpf.value = cpf.value.replace('-', '');

		Mascara.valida(cpf);

	} else if (cpf.value.length == 14) {
		if (cpf.value.indexOf(".") != -1 && cpf.value.lastIndexOf(".") != -1
				&& cpf.value.indexOf("-") != -1) {
			Mascara.valida(cpf);
		} else {
			return false;
		}

	} else {
		if (cpf.value.length == 0) {
			return false;
		} else
			erro = "Número de CPF inválido 3.";
	}
	if (erro.length > 0) {
		alert(erro);
		cpf.focus();
		return false;
	}
	document.getElementById("buttonCadastroUsuario").disabled = false;
	return true;
}

Mascara.valida = function(cpf) {
	erro = new String();
	var nonNumbers = /\D/;
	var valor = cpf;

	valor = Mascara.retiraFormatacao(cpf);

	if (nonNumbers.test(cpf.value)) {
		erro = "A verificacao de CPF suporta apenas números!";
	} else {
		if (cpf.value == "00000000000" || cpf.value == "11111111111"
				|| cpf.value == "22222222222" || cpf.value == "33333333333"
				|| cpf.value == "44444444444" || cpf.value == "55555555555"
				|| cpf.value == "66666666666" || cpf.value == "77777777777"
				|| cpf.value == "88888888888" || cpf.value == "99999999999") {

			erro = "Número de CPF inválido! 1"
		}

		var a = [];
		var b = new Number;
		var c = 11;

		for (i = 0; i < 11; i++) {
			a[i] = cpf.value.charAt(i);
			if (i < 9)
				b += (a[i] * --c);
		}

		if ((x = b % 11) < 2) {
			a[9] = 0
		} else {
			a[9] = 11 - x
		}
		b = 0;
		c = 11;

		for (y = 0; y < 10; y++)
			b += (a[y] * c--);

		if ((x = b % 11) < 2) {
			a[10] = 0;
		} else {
			a[10] = 11 - x;
		}

		if ((cpf.value.charAt(9) != a[9]) || (cpf.value.charAt(10) != a[10])) {
			erro = "Número de CPF inválido 2.";

		}
	}

	if (erro.length > 0) {
		Mascara.formataCampo(cpf, '000.000.000-00', event);
		alert(erro);
		cpf.focus();
		return false;
	}

}

// valida numero inteiro com mascara
Mascara.mascaraInteiro = function() {
	if (event.keyCode < 48 || event.keyCode > 57) {
		event.returnValue = false;
		return false;
	}
	return true;
}

// formata os campos de forma generica
Mascara.formataCampo = function(campo, Mascara, evento) {
	var boleanoMascara;

	var Digitato = evento.keyCode;
	exp = /\-|\.|\/|\(|\)| /g;
	campoSoNumeros = campo.value.toString().replace(exp, "");

	var posicaoCampo = 0;
	var NovoValorCampo = "";
	var TamanhoMascara = campoSoNumeros.length;

	if (Digitato != 8) { // backspace
		for (i = 0; i <= TamanhoMascara; i++) {
			boleanoMascara = ((Mascara.charAt(i) == "-")
					|| (Mascara.charAt(i) == ".") || (Mascara.charAt(i) == "/"))
			boleanoMascara = boleanoMascara
					|| ((Mascara.charAt(i) == "(")
							|| (Mascara.charAt(i) == ")") || (Mascara.charAt(i) == " "))
			if (boleanoMascara) {
				NovoValorCampo += Mascara.charAt(i);
				TamanhoMascara++;
			} else {
				NovoValorCampo += campoSoNumeros.charAt(posicaoCampo);
				posicaoCampo++;
			}
		}
		campo.value = NovoValorCampo;
		return true;
	} else {
		return true;
	}
}

Mascara.retiraFormatacao = function(cpf) {
	with (cpf) {
		value = value.replace(".", "");
		value = value.replace(".", "");
		value = value.replace("-", "");
		value = value.replace("/", "");
	}
	return cpf;
}
