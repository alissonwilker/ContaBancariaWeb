
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ControllerServlet
 */
@WebServlet("/BancoServlet")
public class BancoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String PARAM_NOME_CLIENTE = "nomeCliente";
    private static final String PARAM_TIPO_CLIENTE = "tipoCliente";
    private static final String PARAM_CPF_CNPJ = "cpfCnpj";
    private static final String PARAM_OPERACAO = "operacao";
    private static final String PARAM_VALOR_DEPOSITO = "valorDeposito";
    private static final String PARAM_VALOR_SAQUE = "valorSaque";

    enum Operacao {
        recuperarNomeCliente, recuperarCpfCliente, recuperarCnpjCliente, recuperarSaldo, depositarValor, sacarValor, depositarSacarValor
    }

    enum TipoCliente {
        pessoaFisica, pessoaJuridica
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String nomeCliente = request.getParameter(PARAM_NOME_CLIENTE);
        String cpfCnpj = request.getParameter(PARAM_CPF_CNPJ);
        String valorDeposito = request.getParameter(PARAM_VALOR_DEPOSITO);
        String valorSaque = request.getParameter(PARAM_VALOR_SAQUE);

        String tipoClienteStr = request.getParameter(PARAM_TIPO_CLIENTE);
        TipoCliente tipoCliente = Enum.valueOf(TipoCliente.class, tipoClienteStr);

        String operacaoStr = request.getParameter(PARAM_OPERACAO);
        Operacao operacao = Enum.valueOf(Operacao.class, operacaoStr);

        ContaBancaria contaBancaria = criarContaBancaria(nomeCliente, cpfCnpj, tipoCliente);

        Object resposta = null;
        switch (operacao) {
        case recuperarNomeCliente:
            resposta = contaBancaria.getCliente().getNome();
            break;
        case recuperarCpfCliente:
            if (TipoCliente.pessoaFisica.equals(tipoCliente)) {
                resposta = ((PessoaFisica) contaBancaria.getCliente()).getCpf();
            }
            break;
        case recuperarCnpjCliente:
            if (TipoCliente.pessoaJuridica.equals(tipoCliente)) {
                resposta = ((PessoaJuridica) contaBancaria.getCliente()).getCnpj();
            }
            break;
        case recuperarSaldo:
            resposta = contaBancaria.getSaldo();
            break;
        case depositarValor:
            contaBancaria.depositar(Integer.parseInt(valorDeposito));
            resposta = contaBancaria.getSaldo();
            break;
        case sacarValor:
            resposta = contaBancaria.sacar(Integer.parseInt(valorSaque));
            break;
        case depositarSacarValor:
            contaBancaria.depositar(Integer.parseInt(valorDeposito));
            resposta = contaBancaria.sacar(Integer.parseInt(valorSaque));
            break;
        }

        request.setAttribute("resposta", resposta);
        request.getRequestDispatcher("resposta.jsp").forward(request, response);
    }

    private ContaBancaria criarContaBancaria(String nomeCliente, String cpfCnpj, TipoCliente tipoCliente) {
        ContaBancaria contaBancaria;
        if (TipoCliente.pessoaFisica.equals(tipoCliente)) {
            contaBancaria = new ContaBancaria(new PessoaFisica(nomeCliente, cpfCnpj));
        } else {
            contaBancaria = new ContaBancaria(new PessoaJuridica(nomeCliente, cpfCnpj));
        }
        return contaBancaria;
    }

}
