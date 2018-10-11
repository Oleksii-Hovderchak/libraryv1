package ua.nure.library.persistence.specification;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Specifications for search.
 */
public class SearchSpecification {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchSpecification.class);

    private SearchSpecification() {
    }

    /**
     * Builds predicate to check if field equals to value
     *
     * @param value value to check.
     * @param cb    criteria builder.
     * @param path  path to expression.
     * @return predicate which checked if contains a value.
     */
    public static Predicate equalByField(String value, CriteriaBuilder cb, Expression<String> path) {
        String trimmedValue = StringUtils.trimToNull(value);
        if (StringUtils.isBlank(trimmedValue)) {
            return cb.conjunction();
        }
        return cb.equal(path, trimmedValue);
    }

    /**
     * Builds predicate to check if field equals to value
     *
     * @param value value to check.
     * @param cb    criteria builder.
     * @param path  path to expression.
     * @param <T>   type of value generic.
     * @return predicate which checked if contains a value.
     */
    public static <T> Predicate equalByField(T value, CriteriaBuilder cb, Expression<T> path) {
        if (Objects.isNull(value)) {
            return cb.conjunction();
        }
        return cb.equal(path, value);
    }

    /**
     * Builds predicate which checked if search matches pattern.
     *
     * @param search search to check.
     * @param cb     criteria builder.
     * @param paths  path to expressions.
     * @return predicate which checked if search matches expressions.
     */
    @SafeVarargs
    public static Predicate[] likeByFields(String search, CriteriaBuilder cb, Expression<String>... paths) {
        String finalSearch = StringUtils.trimToNull(search);
        if (StringUtils.isBlank(finalSearch)) {
            return new Predicate[]{cb.conjunction()};
        }
        return Arrays.stream(paths)
                .map(path -> cb.like(path, '%' + finalSearch + '%'))
                .toArray(Predicate[]::new);
    }

    /**
     * Builds predicate to check if search matches pattern
     *
     * @param search value to check.
     * @param cb     criteria builder.
     * @param path   path to expression.
     * @return predicate which checked if contains a value.
     */
    public static Predicate likeByField(String search, CriteriaBuilder cb, Expression<String> path) {
        String finalSearch = StringUtils.trimToNull(search);
        if (StringUtils.isBlank(finalSearch)) {
            return cb.conjunction();
        }
        return cb.like(path, '%' + finalSearch + '%');
    }

    /**
     * Build predicate which checked if list contains all values.
     *
     * @param value values to check.
     * @param cb    criteria builder.
     * @param path  path to collection.
     * @param <T>   type of value and checke collection generic.
     * @return predicate which checked if list contains all values.
     */
    public static <T> Predicate containsAllByField(List<T> value, CriteriaBuilder cb, Expression<List<T>> path) {
        if (Objects.isNull(value)) {
            return cb.conjunction();
        }
        return cb.and(value.stream().map(elem -> cb.isMember(elem, path)).toArray(Predicate[]::new));
    }

    /**
     * Build predicate which checked if list contains one of values.
     *
     * @param value values to check.
     * @param cb    criteria builder.
     * @param path  path to collection.
     * @param <T>   type of value and checke collection generic.
     * @return predicate which checked if list contains all values.
     */
    public static <T> Predicate containsAnyByField(List<T> value, CriteriaBuilder cb, Expression<List<T>> path) {
        return cb.or(value.stream().map(elem -> cb.isMember(elem, path)).toArray(Predicate[]::new));
    }

}