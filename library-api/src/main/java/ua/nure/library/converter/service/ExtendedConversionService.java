package ua.nure.library.converter.service;

import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import ua.nure.library.dto.PageDto;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Custom ExtendedConversionService
 */
public interface ExtendedConversionService extends ConversionService {

    /**
     * Converts all in collection
     *
     * @param source      source collection
     * @param targetClass targetClass
     * @param <T>         the type of the class modeled by this {@code Class} object.
     * @return List of converted items
     */
    <T> List<T> convertAll(Collection<?> source, Class<T> targetClass);

    /**
     * Converts all in set
     *
     * @param source      source collection
     * @param targetClass targetClass
     * @param <T>         the type of the class modeled by this {@code Class} object.
     * @return Set of converted items
     */
    <T> Set<T> convertSet(Collection<?> source, Class<T> targetClass);

    /**
     * Converts page
     *
     * @param page        page
     * @param targetClass targetClass
     * @param <T>         the type of the class modeled by this {@code Class} object.
     * @return PageDto
     */
    <T> PageDto<T> convertPage(Page<?> page, Class<T> targetClass);
}
