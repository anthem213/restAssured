package cucumber.Options;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class) //in features we have given path till /features. it means it will run all file present inside features package
// to run specific file give /any file name the specific file will run
@CucumberOptions(features = "src/test/java/features",plugin = "json:target/jsonReports/cucumber-report.json",glue = {"stepDefinations"})

//tags = "@AddPlace"
//here tags means only scenario tagged with mentioned name will be run
public class TestRunner {

}
