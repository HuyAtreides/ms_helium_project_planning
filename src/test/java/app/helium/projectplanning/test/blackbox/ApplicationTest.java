package app.helium.projectplanning.test.blackbox;

import app.helium.projectplanning.core.application.service.AbstractDateTimeService;
import app.helium.projectplanning.test.shared.datetime.ClockProviderForTesting;
import app.helium.projectplanning.test.shared.datetime.DateTimeServiceWithFixedInstant;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

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
        return hibernateProperties -> hibernateProperties.put("javax.persistence.validation.factory", validator());
    }

    @Bean
    @Primary
    public AbstractDateTimeService dateTimeServiceWithFixedInstant() {
        return new DateTimeServiceWithFixedInstant();
    }
}
