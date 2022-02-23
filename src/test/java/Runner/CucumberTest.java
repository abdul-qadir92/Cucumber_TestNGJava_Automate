package Runner;

import com.browserstack.local.Local;
import com.qa.util.BrowserstackTestStatusListener;
import com.qa.util.CapabilityReader;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.*;

import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@CucumberOptions(features = "src/test/resources/Features", glue = {"StepDefinitions"},
        plugin = {"pretty",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
        monochrome = true
       )
@Listeners(BrowserstackTestStatusListener.class)
public class CucumberTest {
       private TestNGCucumberRunner testNGCucumberRunner;
       public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
       private Local l;
       @BeforeClass(alwaysRun = true)
       public void setUpClass() {
              testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
       }
       @BeforeTest
       public void localStart() throws Exception {
              if (System.getProperties().get("local").toString().contains("true")){
                     JSONParser parser = new JSONParser();
                     JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resources/browserstack/conf/Run_Local_Test/local.conf.json"));
                     System.out.println("Starting Local");
                     l = new Local();
                     Map<String, String> options = new HashMap<String, String>();
                     options.put("key", (String) config.get("key"));
                     l.start(options);
              }
       }
       @Parameters(value = { "config", "environment" })
       @BeforeMethod()
       public void setUpHooks(String config_file, String environment) throws Exception {
              DesiredCapabilities capabilities = new DesiredCapabilities();
              String browser=System.getProperty("browser-type").toLowerCase();
              if (browser.equalsIgnoreCase("chrome")) {
                     WebDriverManager.chromedriver().setup();
                     tlDriver.set(new ChromeDriver());
              } else if (browser.equalsIgnoreCase("firefox")) {
                     WebDriverManager.firefoxdriver().setup();
                     tlDriver.set(new FirefoxDriver());
              } else if (browser.equalsIgnoreCase("safari")) {
                     WebDriverManager.safaridriver().setup();
                     tlDriver.set(new SafariDriver());
              } else if (browser.equalsIgnoreCase("remote")) {
                     JSONParser parser = new JSONParser();
                     JSONObject config;
                     JSONObject envs;
                     config = (JSONObject) parser.parse(new FileReader("src/test/resources/browserstack/conf/"+config_file));
                     envs = (JSONObject) config.get("environments");
                     Object env = envs.get(environment);
                     capabilities = CapabilityReader.getCapability((Map<String, String>) env, config);
                     tlDriver.set( new RemoteWebDriver(
                             new URL("http://" + config.get("user").toString() + ":" + config.get("key").toString() + "@" + config.get("server") + "/wd/hub"), capabilities));
              }else
                     throw new AssertionError("Invalid input for browser");
              if(!capabilities.toString().contains("realMobile"))
                     getDriver().manage().window().maximize();
       }

       public static synchronized WebDriver getDriver(){
              return tlDriver.get();
       }

       @Test(groups = "cucumber",description = "Runs Cucumber Feature",dataProvider = "scenarios")
       public void scenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) throws MalformedURLException {
              testNGCucumberRunner.runScenario(pickleWrapper.getPickle());
       }
       @DataProvider(parallel = true)
       public Object[][] scenarios(){
              return testNGCucumberRunner.provideScenarios();
       }

       @AfterClass(alwaysRun = true)
       public void tearDownClass() throws Exception {
              testNGCucumberRunner.finish();
              System.out.println("Stopping Local");
              if(l != null) l.stop();
       }
}
