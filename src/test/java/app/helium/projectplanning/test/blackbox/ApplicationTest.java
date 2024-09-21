package app.helium.projectplanning.test.blackbox;

import app.helium.projectplanning.test.shared.datetime.ClockProviderForTesting;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import java.util.Map;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationTest {

    @Bean
    public ValidatorFactory validator() {
        return Validation
                .byDefaultProvider()
                .configure()
                .clockProvider(new ClockProviderForTesting())
                .buildValidatorFactory();
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer() {
        return new HibernatePropertiesCustomizer() {
            @Override
            public void customize(Map<String, Object> hibernateProperties) {
                hibernateProperties.put("javax.persistence.validation.factory", validator());
            }
        };
    }
}
