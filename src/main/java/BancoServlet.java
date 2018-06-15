
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ControllerServlet
 */
@WebServlet("/BancoServlet")
public class BancoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String RESULTADO_OPERACAO_BANCARIA = "resultadoOperacaoBancaria";
    private static final String ATRIB_CONTAS = "contas";
    private static final String PARAM_ID_CONTA = "idConta";
    private static final String PARAM_NOME_CLIENTE = "nomeCliente";
    private static final String PARAM_TIPO_CLIENTE = "tipoCliente";
    private static final String PARAM_CPF_CNPJ = "cpfCnpj";
    private static final String PARAM_OPERACAO = "operacao";
    private static final String PARAM_VALOR_DEPOSITO = "valorDeposito";
    private static final String PARAM_VALOR_SAQUE = "valorSaque";

    private Operacao operacao;
    private String nomeCliente;
    private String cpfCnpj;
    private String tipoClienteStr;
    private String valorDepositoStr;
    private String valorSaqueStr;

    private enum Operacao {
        criarConta, recuperarNomeCliente, recuperarCpfCliente, recuperarCnpjCliente, recuperarSaldo, depositarValor, sacarValor, depositarSacarValor
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String resposta = null;
        recuperarParametros(request);

        switch (operacao) {
        case criarConta:
            resposta = criarContaBancaria(request);
            break;
        case recuperarNomeCliente:
            resposta = recuperarNomeCliente(request);
            break;
        case recuperarCpfCliente:
            resposta = recuperarCpfCliente(request);
            break;
        case recuperarCnpjCliente:
            resposta = recuperarCnpjCliente(request);
            break;
        case recuperarSaldo:
            resposta = recuperarSaldo(request);
            break;
        case depositarValor:
            resposta = depositarValor(request);
            break;
        case sacarValor:
            resposta = sacarValor(request);
            break;
        case depositarSacarValor:
            resposta = depositarSacarValor(request);
            break;
        }

        response.setContentType("application/json");
        response.getWriter().print(resposta);
    }

    private void recuperarParametros(HttpServletRequest request) {
        nomeCliente = request.getParameter(PARAM_NOME_CLIENTE);
        cpfCnpj = request.getParameter(PARAM_CPF_CNPJ);
        tipoClienteStr = request.getParameter(PARAM_TIPO_CLIENTE);
        valorDepositoStr = request.getParameter(PARAM_VALOR_DEPOSITO);
        valorSaqueStr = request.getParameter(PARAM_VALOR_SAQUE);

        String operacaoStr = request.getParameter(PARAM_OPERACAO);
        operacao = Enum.valueOf(Operacao.class, operacaoStr);
    }

    private String depositarSacarValor(HttpServletRequest request) {
         ContaBancaria contaBancaria = recuperarContaBancaria(request);
         int valorDeposito = Integer.parseInt(valorDepositoStr);
         int valorSaque = Integer.parseInt(valorSaqueStr);
         
         Integer saldo = new BancoBusiness().depositarSacarValor(contaBancaria, valorDeposito, valorSaque);
         
         return criarRetornoOperacaoBancaria(saldo);
    }

    private String sacarValor(HttpServletRequest request) {
        ContaBancaria contaBancaria = recuperarContaBancaria(request);
        int valorSaque = Integer.parseInt(valorSaqueStr);

        Integer saldo = new BancoBusiness().sacarValor(contaBancaria, valorSaque);
        
        return criarRetornoOperacaoBancaria(saldo);
    }

    private String depositarValor(HttpServletRequest request) {
        ContaBancaria contaBancaria = recuperarContaBancaria(request);
        int valorDeposito = Integer.parseInt(valorDepositoStr);

        Integer saldo = new BancoBusiness().depositarValor(contaBancaria, valorDeposito);
        
        return criarRetornoOperacaoBancaria(saldo);
    }

    private String recuperarSaldo(HttpServletRequest request) {
        ContaBancaria contaBancaria = recuperarContaBancaria(request);

        Integer saldo = new BancoBusiness().recuperarSaldo(contaBancaria);
        
        return criarRetornoOperacaoBancaria(saldo);
    }

    private String recuperarCnpjCliente(HttpServletRequest request) {
        ContaBancaria contaBancaria = recuperarContaBancaria(request);

        String cnpj = new BancoBusiness().recuperarCnpjCliente(contaBancaria);
        
        return criarRetornoOperacaoBancaria(cnpj);
    }

    private String recuperarCpfCliente(HttpServletRequest request) {
        ContaBancaria contaBancaria = recuperarContaBancaria(request);

        String cpf = new BancoBusiness().recuperarCpfCliente(contaBancaria);
        
        return criarRetornoOperacaoBancaria(cpf);
    }

    private String recuperarNomeCliente(HttpServletRequest request) {
        ContaBancaria contaBancaria = recuperarContaBancaria(request);

        String nomeCliente = new BancoBusiness().recuperarNomeCliente(contaBancaria);
        
        return criarRetornoOperacaoBancaria(nomeCliente);
    }

    private String criarContaBancaria(HttpServletRequest request) {
        Cliente.TipoCliente tipoCliente = Enum.valueOf(Cliente.TipoCliente.class, tipoClienteStr);
        
        ContaBancaria contaBancaria = new BancoBusiness().criarContaBancaria(nomeCliente, cpfCnpj, tipoCliente);

        adicionarNovaContaNaSessaoDoUsuario(request, contaBancaria);
        return criarRetornoNovaContaBancaria(contaBancaria);
    }

    private String criarRetornoOperacaoBancaria(Object resultadoOperacaoBancaria) {
        JsonUtils.CampoJson campoResultadoOperacaoBancaria = new JsonUtils.CampoJson(RESULTADO_OPERACAO_BANCARIA,
            resultadoOperacaoBancaria);
        return JsonUtils.criarJsonObject(campoResultadoOperacaoBancaria);
    }

    private String criarRetornoNovaContaBancaria(ContaBancaria contaBancaria) {
        JsonUtils.CampoJson campoIdConta = new JsonUtils.CampoJson(PARAM_ID_CONTA, contaBancaria.getId());
        JsonUtils.CampoJson campoNomeCliente = new JsonUtils.CampoJson(PARAM_NOME_CLIENTE,
            contaBancaria.getCliente().getNome());
        return JsonUtils.criarJsonObject(campoIdConta, campoNomeCliente);
    }

    private void adicionarNovaContaNaSessaoDoUsuario(HttpServletRequest request, ContaBancaria contaBancaria) {
        Map<Integer, ContaBancaria> contas = recuperarContasDaSessaoDoUsuario(request);
        contas.put(contaBancaria.getId(), contaBancaria);
    }

    private ContaBancaria recuperarContaBancaria(HttpServletRequest request) {
        int idConta = Integer.parseInt(request.getParameter(PARAM_ID_CONTA));

        Map<Integer, ContaBancaria> contasBancarias = recuperarContasDaSessaoDoUsuario(request);
        return contasBancarias.get(idConta);
    }

    @SuppressWarnings("unchecked")
    private Map<Integer, ContaBancaria> recuperarContasDaSessaoDoUsuario(HttpServletRequest request) {
        HttpSession sessao = request.getSession();
        Map<Integer, ContaBancaria> contas = (Map<Integer, ContaBancaria>) sessao.getAttribute(ATRIB_CONTAS);
        if (contas == null) {
            contas = new HashMap<>();
            sessao.setAttribute(ATRIB_CONTAS, contas);
        }
        return contas;
    }

}
