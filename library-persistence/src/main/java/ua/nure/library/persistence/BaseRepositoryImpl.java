package ua.nure.library.persistence;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Base repository implementation.
 *
 * @param <T> the domain type the repository manages
 * @param <I> the type of the id of the entity the repository manages
 */
public class BaseRepositoryImpl<T, I extends Serializable> extends SimpleJpaRepository<T, I> implements BaseRepository<T, I> {

    /**
     * Base repository implementation constructor
     *
     * @param entityInformation the entity to manage
     * @param entityManager     the entity manager
     */
    public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    /**
     * Base repository implementation constructor
     *
     * @param domainClass the entity class to manage
     * @param em          the entity manager
     */
    public BaseRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    @SuppressWarnings("unchecked")
    private Specifications<T> generateSortSpecification(Sort sort) {
        Specifications<T> specificationWithSort = Specifications.where((root, query, cb) -> cb.conjunction());
        if (Objects.nonNull(sort)) {
            List<Pair<String[], Boolean>> collect = StreamSupport.stream(
                    Spliterators.spliteratorUnknownSize(sort.iterator(), Spliterator.ORDERED), false)
                    .map(order -> new ImmutablePair<>(order.getProperty().split("\\."), order.isAscending()))
                    .collect(Collectors.toList());
            specificationWithSort = specificationWithSort.and((root, query, cb) -> {
                Order[] orders = collect.stream()
                        .map(booleanPair -> {
                            String[] paths = booleanPair.getKey();
                            Path<Collection<T>> path = Arrays.stream(paths)
                                    .reduce((Path<Collection<T>>) root, Path::get, (tPath, tPath2) -> tPath);
                            Order order;
                            if (Collection.class.isAssignableFrom(path.getJavaType())) {
                                order = new OrderImpl(cb.size(path), booleanPair.getValue());
                            } else {
                                order = new OrderImpl(path, booleanPair.getValue());
                            }
                            return order;
                        })
                        .toArray(Order[]::new);
                query.orderBy(orders);
                return cb.conjunction();
            });
        }
        return specificationWithSort;
    }

    @Override
    public Page<T> findAll(Specification<T> spec, Pageable pageable) {
        Sort sort = pageable.getSort();
        Pageable newPageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize());
        return super.findAll(Specifications.where(spec).and(generateSortSpecification(sort)), newPageable);
    }

    @Override
    public List<T> findAll(Specification<T> spec, Sort sort) {
        return super.findAll(Specifications.where(spec).and(generateSortSpecification(sort)));
    }

    @Override
    public List<T> findAll(Sort sort) {
        return super.findAll(generateSortSpecification(sort), sort);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        Pageable newPageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize());
        return super.findAll(generateSortSpecification(pageable.getSort()), newPageable);
    }
}
