package app.helium.projectplanning.test.blackbox;

import app.helium.projectplanning.ProjectPlanningApplication;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT, classes = {ApplicationTest.class, ProjectPlanningApplication.class})
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public @interface BlackBoxTest {

}

