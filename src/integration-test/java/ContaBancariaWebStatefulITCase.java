
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;

public class ContaBancariaWebStatefulITCase extends AbstractIntegrationTest {
    private static final String NAME_INPUT_ID_CONTA = "idConta";
    private static final String VALOR_DEPOSITO_TAG = "valorDeposito";
    private static final String VALOR_SAQUE_TAG = "valorSaque";
    private static final String VALOR_0 = "0";
    private static final String VALOR_10 = "10";
    private static final String VALOR_20 = "20";
    private static final String CNPJ_CLIENTE = "01.001...";
    private static final String CPF_CNPJ_TAG = "cpfCnpj";
    private static final String ID_BOTAO_CRIAR_CONTA = "botaoCriarConta";
    private static final String ID_BOTAO_REALIZAR_OPERACAO_BANCARIA = "botaoRealizarOperacaoBancaria";
    private static final String TIPO_CLIENTE_PESSOA_FISICA_TAG = "tipoClientePessoaFisica";
    private static final String TIPO_CLIENTE_PESSOA_JURIDICA_TAG = "tipoClientePessoaJuridica";
    private static final String NOME_CLIENTE_TAG = "nomeCliente";
    private static final String CPF_CLIENTE = "053.749...";
    private static final String NOME_CLIENTE_PESSOA_FISICA = "Carlos";
    private static final String NOME_CLIENTE_PESSOA_JURIDICA = "Atacadista S/A";

    @Test
    public void testPessoaFisica() {
        String idContaPessoaFisica = criarContaBancaria(NOME_CLIENTE_PESSOA_FISICA, CPF_CLIENTE, true);

        validarOperacao("operacaoRecuperarNomeCliente", NOME_CLIENTE_PESSOA_FISICA, idContaPessoaFisica);
        validarOperacao("operacaoRecuperarCpfCliente", CPF_CLIENTE, idContaPessoaFisica);
        validarOperacao("operacaoRecuperarSaldo", VALOR_0, idContaPessoaFisica);
        validarOperacaoDepositoOuSaque("operacaoDepositarValor", VALOR_10, VALOR_0, VALOR_10, idContaPessoaFisica);
        validarOperacaoDepositoOuSaque("operacaoSacarValor", VALOR_0, VALOR_10, VALOR_0, idContaPessoaFisica);
        validarOperacaoDepositoOuSaque("operacaoDepositarSacarValor", VALOR_20, VALOR_10, VALOR_10,
            idContaPessoaFisica);
    }

    @Test
    public void testPessoaJuridica() {
        String idContaPessoaJuridica = criarContaBancaria(NOME_CLIENTE_PESSOA_JURIDICA, CNPJ_CLIENTE, false);

        validarOperacao("operacaoRecuperarNomeCliente", NOME_CLIENTE_PESSOA_JURIDICA, idContaPessoaJuridica);
        validarOperacao("operacaoRecuperarCnpjCliente", CNPJ_CLIENTE, idContaPessoaJuridica);
    }

    @Test
    public void testPessoaFisicaEPessoaJuridica() {
        String idContaPessoaFisica = criarContaBancaria(NOME_CLIENTE_PESSOA_FISICA, CPF_CLIENTE, true);
        String idContaPessoaJuridica = criarContaBancaria(NOME_CLIENTE_PESSOA_JURIDICA, CNPJ_CLIENTE, false);

        validarOperacaoDepositoOuSaque("operacaoDepositarValor", VALOR_20, VALOR_0, VALOR_20, idContaPessoaFisica);
        validarOperacaoDepositoOuSaque("operacaoDepositarValor", VALOR_10, VALOR_0, VALOR_10, idContaPessoaJuridica);

        validarOperacao("operacaoRecuperarSaldo", VALOR_20, idContaPessoaFisica);
        validarOperacao("operacaoRecuperarSaldo", VALOR_10, idContaPessoaJuridica);
    }

    private String criarContaBancaria(String nomeCliente, String cpfCnpj, boolean pessoaFisica) {
        driver.get(APP_HOME_URL);

        preencherInputTexto(NOME_CLIENTE_TAG, nomeCliente);
        preencherInputTexto(CPF_CNPJ_TAG, cpfCnpj);
        if (pessoaFisica) {
            driver.findElement(By.id(TIPO_CLIENTE_PESSOA_FISICA_TAG)).click();
        } else {
            driver.findElement(By.id(TIPO_CLIENTE_PESSOA_JURIDICA_TAG)).click();
        }
        driver.findElement(By.id(ID_BOTAO_CRIAR_CONTA)).click();

        aguardarRespostaPorClassName("celulaTabela");

        return driver.findElement(By.className("celulaTabela")).getText();
    }

    private void validarOperacao(final String NOME_OPERACAO, final String RESPOSTA_ESPERADA, String idConta) {
        preencherInputTexto(NAME_INPUT_ID_CONTA, idConta);
        driver.findElement(By.id(NOME_OPERACAO)).click();
        driver.findElement(By.id(ID_BOTAO_REALIZAR_OPERACAO_BANCARIA)).click();

        aguardarResposta(ID_RESPOSTA);

        Assert.assertEquals(RESPOSTA_ESPERADA, driver.findElement(By.id(ID_RESPOSTA)).getText());
    }

    private void validarOperacaoDepositoOuSaque(final String NOME_OPERACAO, final String VALOR_DEPOSITO,
        final String VALOR_SAQUE, final String RESPOSTA_ESPERADA, String idConta) {
        preencherInputTexto(VALOR_DEPOSITO_TAG, VALOR_DEPOSITO);
        preencherInputTexto(VALOR_SAQUE_TAG, VALOR_SAQUE);

        validarOperacao(NOME_OPERACAO, RESPOSTA_ESPERADA, idConta);
    }
}
