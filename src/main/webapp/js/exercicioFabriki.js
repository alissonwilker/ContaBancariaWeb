function enviarAjaxGet(destinoRequisicao, parametrosRequisicao, receberAjaxCallback) {
	let xhttp = new XMLHttpRequest();
	
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			let resposta = JSON.parse(xhttp.responseText);
			receberAjaxCallback(resposta);
		}
	};
	
	xhttp.open("GET", destinoRequisicao + "?" + parametrosRequisicao, true);
	xhttp.send();
};

function adicionarParametroRequisicao(chave, valor, primeiroParametro) {
	let novoParametroRequisicao = "";
	if (!primeiroParametro) {  
		novoParametroRequisicao = "&";
	}
	
	return novoParametroRequisicao += chave + "=" + valor;
}
