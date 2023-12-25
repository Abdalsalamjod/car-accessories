package application;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
  features = "src/test/resources/features",
  monochrome = true,
  snippets = CucumberOptions.SnippetType.CAMELCASE,
  glue = {"application/justtest"},
  plugin = {"summary", "html:target/cucumber/test-summary.html"}
)
public class ConfigurationTest {
  
}


