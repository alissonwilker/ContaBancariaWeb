
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
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

@RunWith(Arquillian.class)
public abstract class AbstractIntegrationTest {
    private static final String FORM_TAG = "form";
    private static final String APP_CONTEXT_ROOT = "ContaBancariaWeb";
    private static final String ID_RESPOSTA = "resposta";
    private static final int WAIT_TIMEOUT = 10;

    protected static final String APP_HOME_URL = "http://localhost:8080/" + APP_CONTEXT_ROOT;

    protected WebDriver driver;

    @Deployment
    public static Archive<?> criarWebArchiveTeste() {
        WebArchive webArchiveTeste = ShrinkWrap.create(WebArchive.class, APP_CONTEXT_ROOT + ".war");

        webArchiveTeste.merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class)
            .importDirectory("src/main/webapp").as(GenericArchive.class), "/", Filters.includeAll());

        File[] bibliotecas = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeAndTestDependencies().resolve()
            .withTransitivity().asFile();
        webArchiveTeste.addAsLibraries(bibliotecas).addPackages(true, "");

        webArchiveTeste.merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class)
            .importDirectory("src/main/resources").as(GenericArchive.class), "/WEB-INF/classes", Filters.includeAll());

        return webArchiveTeste;
    }
    
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

    private void aguardarResposta(final String ID_RESPOSTA) {
        (new WebDriverWait(driver, WAIT_TIMEOUT)).until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver driver) {
                return driver.findElement(By.id(ID_RESPOSTA)).isDisplayed();
            }
        });
    }
    
    protected void submeterEValidar(final String RESPOSTA_ESPERADA) {
        driver.findElement(By.tagName(FORM_TAG)).submit();

        aguardarResposta(ID_RESPOSTA);

        Assert.assertEquals(RESPOSTA_ESPERADA, driver.findElement(By.id(ID_RESPOSTA)).getText());
    }

}