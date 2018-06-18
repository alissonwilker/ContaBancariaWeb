const OPERACAO_PARAM = "operacao";
const SERVLET_URL = "BancoServlet";

function criarContaCallback(resposta) {
	let tabelaContas = document.getElementById("contas");
	
	let linhaConta = tabelaContas.insertRow(1);
	
	let i = 0;
	for (let x in resposta) {
		criarCelulaTabela(linhaConta, resposta[x], i);
		i++;
	}
};

function realizarOperacaoBancariaCallback(resposta) {
	let respostaSpan = recuperarResposta();
	if (resposta && resposta.resultadoOperacaoBancaria) {
		respostaSpan.innerHTML = resposta.resultadoOperacaoBancaria;
	} else {
		limparResposta();
	}
};

function criarCelulaTabela(linhaTabela, conteudoCelula, posicaoCelulaNaLinha) {
	let celulaTabela = linhaTabela.insertCell(posicaoCelulaNaLinha);
	celulaTabela.innerHTML = conteudoCelula;
	celulaTabela.className = "celulaTabela";
};

function recuperarResposta() {
	return document.getElementById("resposta");
};

function limparResposta() {
	let respostaSpan = recuperarResposta();
	respostaSpan.innerHTML = "";
};

function realizarOperacaoBancaria() {	
	limparResposta();
	
	const VALOR_DEPOSITO_PARAM = "valorDeposito";
	const VALOR_SAQUE_PARAM = "valorSaque";
	const ID_CONTA_PARAM = "idConta";

	let valorDeposito = document.getElementsByName(VALOR_DEPOSITO_PARAM)[0].value;
	let valorSaque = document.getElementsByName(VALOR_SAQUE_PARAM)[0].value;
	let idConta = document.getElementsByName(ID_CONTA_PARAM)[0].value;

	let operacaoInput = document.getElementsByName(OPERACAO_PARAM);
	for (let i = 0; i < operacaoInput.length; i++) { 
	    if (operacaoInput[i].checked) {
	    	let operacao = operacaoInput[i].value;
	    	
	    	let parametrosRequisicao = adicionarParametroRequisicao(OPERACAO_PARAM, operacao, true); 
	    	parametrosRequisicao += adicionarParametroRequisicao(VALOR_DEPOSITO_PARAM, valorDeposito, false);
	    	parametrosRequisicao += adicionarParametroRequisicao(VALOR_SAQUE_PARAM, valorSaque, false);
	    	parametrosRequisicao += adicionarParametroRequisicao(ID_CONTA_PARAM, idConta, false);
	    	
	    	enviarAjaxGet(SERVLET_URL, parametrosRequisicao, realizarOperacaoBancariaCallback);
	    }
	}
};

function criarConta() {
	const NOME_CLIENTE_PARAM = "nomeCliente";
	const TIPO_CLIENTE_PARAM = "tipoCliente";
	const CPF_CNPJ_PARAM = "cpfCnpj";

	let nomeCliente = document.getElementsByName(NOME_CLIENTE_PARAM)[0].value;
	let tipoClienteInput = document.getElementsByName(TIPO_CLIENTE_PARAM);
	let tipoCliente;
	if (tipoClienteInput[0].checked) {
		tipoCliente = tipoClienteInput[0].value;
	} else {
		tipoCliente = tipoClienteInput[1].value;
	}
	let cpfCnpj = document.getElementsByName(CPF_CNPJ_PARAM)[0].value;
	
	let parametrosRequisicao = adicionarParametroRequisicao(OPERACAO_PARAM, "criarConta", true); 
	parametrosRequisicao += adicionarParametroRequisicao(NOME_CLIENTE_PARAM, nomeCliente, false);
	parametrosRequisicao += adicionarParametroRequisicao(TIPO_CLIENTE_PARAM, tipoCliente, false);
	parametrosRequisicao += adicionarParametroRequisicao(CPF_CNPJ_PARAM, cpfCnpj, false);
	
	enviarAjaxGet(SERVLET_URL, parametrosRequisicao, criarContaCallback);
};