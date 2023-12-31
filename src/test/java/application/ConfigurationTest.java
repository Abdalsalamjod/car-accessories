package application;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
  features = "src/test/resources",
  monochrome = true,
  snippets = CucumberOptions.SnippetType.CAMELCASE,
  glue = {"application"},
  plugin = {"summary", "html:target/cucumber/test-summary.html"}
)
public class ConfigurationTest {
  
}


