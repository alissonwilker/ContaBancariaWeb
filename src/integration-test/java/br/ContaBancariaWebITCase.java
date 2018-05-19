package br;

import java.io.File;
import java.util.function.Function;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

@RunWith(Arquillian.class)
public class ContaBancariaWebITCase {
    private static final String VALOR_DEPOSITO_TAG = "valorDeposito";
    private static final String VALOR_SAQUE_TAG = "valorSaque";
    private static final String VALOR_0 = "0";
    private static final String VALOR_10 = "10";
    private static final String VALOR_20 = "20";
    private static final String CNPJ_CLIENTE = "01.001...";
    private static final String CPF_CNPJ_TAG = "cpfCnpj";
    private static final String TIPO_CLIENTE_PESSOA_FISICA_TAG = "tipoClientePessoaFisica";
    private static final String TIPO_CLIENTE_PESSOA_JURIDICA_TAG = "tipoClientePessoaJuridica";
    private static final String NOME_CLIENTE_TAG = "nomeCliente";
    private static final String CPF_CLIENTE = "053.749...";
    private static final String NOME_CLIENTE_PESSOA_FISICA = "Carlos";
    private static final String NOME_CLIENTE_PESSOA_JURIDICA = "Atacadista S/A";
    private static final int WAIT_TIMEOUT = 10;
    private static final String FORM_TAG = "form";
    private static final String APP_CONTEXT_ROOT = "ContaBancariaWeb";
    private static final String APP_HOME_URL = "http://localhost:8080/" + APP_CONTEXT_ROOT;
    private static final String ID_RESPOSTA = "resposta";

    @Deployment
    public static Archive<?> criarWebArchiveTeste() {
        WebArchive webArchiveTeste = ShrinkWrap.create(WebArchive.class, APP_CONTEXT_ROOT + ".war");

        webArchiveTeste.merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class)
            .importDirectory("src/main/webapp").as(GenericArchive.class), "/", Filters.includeAll());

        File[] bibliotecas = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeAndTestDependencies().resolve()
            .withTransitivity().asFile();
        webArchiveTeste.addAsLibraries(bibliotecas).addPackages(true, "br");

        webArchiveTeste.merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class)
            .importDirectory("src/main/resources").as(GenericArchive.class), "/WEB-INF/classes", Filters.includeAll());

        return webArchiveTeste;
    }

    private WebDriver driver;

    @Before
    public void beforeTest() {
        System.setProperty("webdriver.gecko.driver", "/tmp/geckodriver/geckodriver");

        FirefoxOptions firefoxOptions = new FirefoxOptions().addArguments("-headless");
        driver = new FirefoxDriver(firefoxOptions);
    }

    @After
    public void afterTest() {
        driver.quit();
    }

    @Test
    public void testRecuperarNomeCliente() {
        validarOperacaoPessoaFisica("operacaoRecuperarNomeCliente", NOME_CLIENTE_PESSOA_FISICA);
    }

    @Test
    public void testRecuperarCpfCliente() {
        validarOperacaoPessoaFisica("operacaoRecuperarCpfCliente", CPF_CLIENTE);
    }

    @Test
    public void testRecuperarCnpjCliente() {
        validarOperacaoPessoaJuridica("operacaoRecuperarCnpjCliente", CNPJ_CLIENTE);
    }

    @Test
    public void testOperacaoRecuperarSaldo() {
        validarOperacaoPessoaFisica("operacaoRecuperarSaldo", VALOR_0);
    }

    @Test
    public void testOperacaoDepositarValor() {
        validarOperacao("operacaoDepositarValor", VALOR_10, VALOR_0, VALOR_10);
    }

    @Test
    public void testOperacaoSacarValor() {
        validarOperacao("operacaoSacarValor", VALOR_0, VALOR_10, VALOR_0);
    }

    @Test
    public void testOperacaoDepositarSacarValor() {
        validarOperacao("operacaoDepositarSacarValor", VALOR_20, VALOR_10, VALOR_10);
    }

    private void validarOperacao(final String NOME_OPERACAO, final String VALOR_DEPOSITO, final String VALOR_SAQUE,
        final String RESPOSTA_ESPERADA) {
        preencherClientePessoaFisica(NOME_OPERACAO);
        driver.findElement(By.name(VALOR_DEPOSITO_TAG)).sendKeys(VALOR_DEPOSITO);
        driver.findElement(By.name(VALOR_SAQUE_TAG)).sendKeys(VALOR_SAQUE);
        submeterEValidar(RESPOSTA_ESPERADA);
    }

    private void submeterEValidar(final String RESPOSTA_ESPERADA) {
        driver.findElement(By.tagName(FORM_TAG)).submit();

        aguardarResposta(ID_RESPOSTA);

        Assert.assertEquals(RESPOSTA_ESPERADA, driver.findElement(By.id(ID_RESPOSTA)).getText());
    }

    private void preencherClientePessoaFisica(final String NOME_OPERACAO) {
        driver.get(APP_HOME_URL);
        driver.findElement(By.name(NOME_CLIENTE_TAG)).sendKeys(NOME_CLIENTE_PESSOA_FISICA);
        driver.findElement(By.id(TIPO_CLIENTE_PESSOA_FISICA_TAG)).click();
        driver.findElement(By.name(CPF_CNPJ_TAG)).sendKeys(CPF_CLIENTE);
        driver.findElement(By.id(NOME_OPERACAO)).click();
    }

    private void preencherClientePessoaJuridica(final String NOME_OPERACAO) {
        driver.get(APP_HOME_URL);
        driver.findElement(By.name(NOME_CLIENTE_TAG)).sendKeys(NOME_CLIENTE_PESSOA_JURIDICA);
        driver.findElement(By.id(TIPO_CLIENTE_PESSOA_JURIDICA_TAG)).click();
        driver.findElement(By.name(CPF_CNPJ_TAG)).sendKeys(CNPJ_CLIENTE);
        driver.findElement(By.id(NOME_OPERACAO)).click();
    }

    private void validarOperacaoPessoaFisica(final String NOME_OPERACAO, final String RESPOSTA_ESPERADA) {
        preencherClientePessoaFisica(NOME_OPERACAO);
        submeterEValidar(RESPOSTA_ESPERADA);
    }

    private void validarOperacaoPessoaJuridica(final String NOME_OPERACAO, final String RESPOSTA_ESPERADA) {
        preencherClientePessoaJuridica(NOME_OPERACAO);
        submeterEValidar(RESPOSTA_ESPERADA);
    }

    private void aguardarResposta(final String ID_RESPOSTA) {
        (new WebDriverWait(driver, WAIT_TIMEOUT)).until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver driver) {
                return driver.findElement(By.id(ID_RESPOSTA)).isDisplayed();
            }
        });
    }

}
