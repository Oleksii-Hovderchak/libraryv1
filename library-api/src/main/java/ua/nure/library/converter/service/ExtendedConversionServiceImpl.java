package ua.nure.library.converter.service;

import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.domain.Page;
import ua.nure.library.dto.PageDto;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ExtendedConversionService implementation
 */
public class ExtendedConversionServiceImpl extends DefaultConversionService implements ExtendedConversionService {

    @Override
    public <T> List<T> convertAll(Collection<?> source, Class<T> targetClass) {
        Stream<?> stream = stream(source);
        return stream
                .filter(Objects::nonNull)
                .map(item -> convert(item, targetClass))
                .collect(Collectors.toList());
    }

    @Override
    public <T> Set<T> convertSet(Collection<?> source, Class<T> targetClass) {
        Stream<?> stream = stream(source);
        return stream
                .filter(Objects::nonNull)
                .map(item -> convert(item, targetClass))
                .collect(Collectors.toSet());
    }

    @Override
    public <T> PageDto<T> convertPage(Page<?> page, Class<T> targetClass) {
        if (Objects.isNull(page)) {
            return new PageDto<>(Collections.emptyList());
        }
        return new PageDto<>(page.map(source -> convert(source, targetClass)).getContent())
                .setItemsPerPage(page.getSize())
                .setPageNumber(page.getNumber() + 1)
                .setTotalItems(page.getTotalElements())
                .setTotalPages(page.getTotalPages());
    }

    private static <T> Stream<T> stream(Collection<T> collection) {
        if (Objects.isNull(collection)) {
            return Stream.empty();
        }
        return collection.stream();
    }
}
