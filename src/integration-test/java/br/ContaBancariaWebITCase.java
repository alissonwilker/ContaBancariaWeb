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
        final String NOME_CLIENTE = "Carlos";

        driver.get(APP_HOME_URL);
        driver.findElement(By.name("nomeCliente")).sendKeys(NOME_CLIENTE);
        driver.findElement(By.id("tipoClientePessoaFisica")).click();
        driver.findElement(By.name("cpfCnpj")).sendKeys("053.749...");
        driver.findElement(By.id("operacaoRecuperarNomeCliente")).click();
        driver.findElement(By.tagName(FORM_TAG)).submit();

        aguardarResposta(ID_RESPOSTA);

        Assert.assertEquals(NOME_CLIENTE, driver.findElement(By.id(ID_RESPOSTA)).getText());
    }

    private void aguardarResposta(final String ID_RESPOSTA) {
        (new WebDriverWait(driver, WAIT_TIMEOUT)).until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver driver) {
                return driver.findElement(By.id(ID_RESPOSTA)).isDisplayed();
            }
        });
    }

}
