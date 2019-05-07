package ua.nure.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import ua.nure.library.converter.service.ExtendedConversionService;
import ua.nure.library.converter.service.ExtendedConversionServiceImpl;

import java.util.List;

/**
 * Configuration for ExtendedConversionService
 */
@Configuration
public class ConversionConfig {

    @Bean
    ExtendedConversionService extendedConversionService(List<Converter> converters) {
        ExtendedConversionServiceImpl bean = new ExtendedConversionServiceImpl();
        converters.forEach(bean::addConverter);
        return bean;
    }
}
