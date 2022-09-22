package mx.conacyt.arche.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import mx.conacyt.arche.IntegrationTest;
import org.springframework.test.context.web.WebAppConfiguration;

@CucumberContextConfiguration
@IntegrationTest
@WebAppConfiguration
public class CucumberTestContextConfiguration {}
