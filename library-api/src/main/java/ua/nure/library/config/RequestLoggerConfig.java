package ua.nure.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.nure.library.filter.RequestLoggingFilter;

/**
 * Configuration for request logger.
 */
@Configuration
public class RequestLoggerConfig {

    /**
     * Request logging filter.
     *
     * @return the request logging filter
     */
    @Bean
    public RequestLoggingFilter requestLoggingFilter() {
        return new RequestLoggingFilter();
    }
}
