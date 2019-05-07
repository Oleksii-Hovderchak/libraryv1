package ua.nure.library.persistence.specification;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.stream.Stream;

/**
 * Various utility methods for working with specifications.
 */
public final class SpecificationUtils {

    private SpecificationUtils() {
    }

    /**
     * Groups by filed.
     *
     * @param firstPath first path
     * @param nextPaths next paths if exist
     * @param <T>       specification entity type
     * @return specification with group by
     */
    public static <T> Specification<T> groupBy(String firstPath, String... nextPaths) {
        return (root, query, cb) -> {
            Path<T> path = Stream.of(nextPaths)
                    .reduce(root.get(firstPath), Path::get, (path1, path2) -> path1);
            return query.groupBy(path).getRestriction();
        };
    }

    /**
     * Joins table and apply filters in specification. Path to joined entity starts from {@link E} class.
     *
     * @param specification         specification containing search clause
     * @param joinedClass           joined entity class of
     * @param firstJoinedEntityPath first path to the joining entity
     * @param nextJoinedEntityPaths next paths to the joining entity
     * @param <T>                   joined entity type
     * @param <E>                   target entity type
     * @return specification for target type with joined specification
     */
    public static <T, E> Specification<E> joinLeftSpecification(Specification<T> specification, Class<T> joinedClass,
                                                                String firstJoinedEntityPath,
                                                                String... nextJoinedEntityPaths) {
        return (root, query, cb) -> {
            Root<T> joinRoot = query.from(joinedClass);
            Expression<Collection<T>> targetExpression = Stream.of(nextJoinedEntityPaths)
                    .reduce(root.get(firstJoinedEntityPath), Path::get, (path1, path2) -> path1);
            Predicate joinPredicate;
            if (Collection.class.isAssignableFrom(targetExpression.getJavaType())) {
                joinPredicate = cb.isMember(joinRoot, targetExpression);
            } else {
                joinPredicate = cb.equal(joinRoot, targetExpression);
            }
            return cb.and(joinPredicate, specification.toPredicate(joinRoot, query, cb));
        };
    }

    /**
     * Joins table and apply filters in specification. Path to joined entity starts from {@link T} class.
     *
     * @param specification         specification containing search clause
     * @param fromClass             joined entity class of
     * @param firstJoinedEntityPath first path to the joining entity
     * @param nextJoinedEntityPaths next paths to the joining entity
     * @param <T>                   joined entity type
     * @param <E>                   target entity type
     * @return specification for target type with joined specification
     */
    public static <T, E> Specification<E> joinRightSpecification(Specification<T> specification, Class<T> fromClass,
                                                                 String firstJoinedEntityPath,
                                                                 String... nextJoinedEntityPaths) {
        return (root, query, cb) -> {
            Root<T> joinRoot = query.from(fromClass);
            Expression<Collection<E>> expression = Stream.of(nextJoinedEntityPaths)
                    .reduce(joinRoot.get(firstJoinedEntityPath), Path::get, (path1, path2) -> path1);
            Predicate joinPredicate;
            if (Collection.class.isAssignableFrom(expression.getJavaType())) {
                joinPredicate = cb.isMember(root, expression);
            } else {
                joinPredicate = cb.equal(root, expression);
            }
            return cb.and(joinPredicate, specification.toPredicate(joinRoot, query, cb));
        };
    }

    /**
     * Creates specification which always true
     *
     * @param <T> type of specification
     * @return specification
     */
    public static <T> Specification<T> conjunction() {
        return (root, query, cb) -> cb.conjunction();
    }

    /**
     * Creates distinct specification
     *
     * @param <T> type of specification
     * @return specification
     */
    public static <T> Specification<T> distinct() {
        return (root, query, cb) -> query.distinct(true).getRestriction();
    }
}
