package stockmarket.example.test.integration.cucumber.annotation;

import org.junit.runner.RunWith;

import cucumber.junit.Cucumber;


@RunWith(Cucumber.class)
@Cucumber.Options( glue = {"stockmarket.example.test.integration.cucumber.annotation"} )
public class RunCukesAnnotationIntegrationTest {

}
