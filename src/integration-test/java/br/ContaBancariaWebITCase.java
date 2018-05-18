package br;

import java.io.File;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

@RunWith(Arquillian.class)
public class ContaBancariaWebITCase {
    protected static final String TEST_APP_CONTEXT_ROOT = "ContaBancariaWeb";

    @Deployment
    public static Archive<?> criarWebArchiveTeste() {
        WebArchive webArchiveTeste = ShrinkWrap.create(WebArchive.class, TEST_APP_CONTEXT_ROOT + ".war");

        webArchiveTeste.merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class)
            .importDirectory("src/main/webapp").as(GenericArchive.class), "/", Filters.includeAll());

        File[] bibliotecas = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeAndTestDependencies().resolve()
            .withTransitivity().asFile();
        webArchiveTeste.addAsLibraries(bibliotecas).addPackages(true, "br");

        webArchiveTeste.merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class)
            .importDirectory("src/main/resources").as(GenericArchive.class), "/WEB-INF/classes", Filters.includeAll());

        return webArchiveTeste;
    }

    @Test
    public void should_create_greeting() {
        System.setProperty("webdriver.gecko.driver", "/tmp/geckodriver");
        
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addArguments("-headless");

        // Create a new instance of the Firefox driver
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.
        WebDriver driver = new FirefoxDriver(firefoxOptions);

        // And now use this to visit Google
        driver.get("http://localhost:8080/ContaBancariaWeb/");
        // Alternatively the same thing can be done like this
        // driver.navigate().to("http://www.google.com");

        // Find the text input element by its name
        // WebElement element = driver.findElement(By.name("q"));

        // Enter something to search for
        // element.sendKeys("Cheese!");

        // Now submit the form. WebDriver will find the form for us from the element
        // element.submit();

        // Check the title of the page
        System.out.println("Page title is: " + driver.getTitle());

        // Google's search is rendered dynamically with JavaScript.
        // Wait for the page to load, timeout after 10 seconds
        // (new WebDriverWait(driver, 10)).until(new Function<WebDriver, Boolean>() {
        // public Boolean apply(WebDriver d) {
        // return d.getTitle().toLowerCase().startsWith("cheese!");
        // }
        // });

        // Should see: "cheese! - Google Search"
        // System.out.println("Page title is: " + driver.getTitle());

        // Close the browser
        driver.quit();
    }

}
