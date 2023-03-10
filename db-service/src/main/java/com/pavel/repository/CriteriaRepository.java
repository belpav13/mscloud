package com.pavel.repository;


import com.pavel.contracts.model.DetailsEntity;
import com.pavel.contracts.model.DetailsPage;
import com.pavel.contracts.model.DetailsSearchCriteria;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Repository
public class CriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public CriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();;
    }

    public Page<DetailsEntity> findAllWithFilters(DetailsPage detailsPage,
                                                  DetailsSearchCriteria detailsSearchCriteria){
        CriteriaQuery<DetailsEntity> criteriaQuery = criteriaBuilder.createQuery(DetailsEntity.class);
        Root<DetailsEntity> detailsRoot = criteriaQuery.from(DetailsEntity.class);
        Predicate predicate = getPredicate(detailsSearchCriteria, detailsRoot);
        criteriaQuery.where(predicate);
        setOrder(detailsPage, criteriaQuery, detailsRoot);

        TypedQuery<DetailsEntity> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(detailsPage.getPageNumber() * detailsPage.getPageSize());
        typedQuery.setMaxResults(detailsPage.getPageSize());

        Pageable pageable = getPageable(detailsPage);

        long detailsCount = getDetailsCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, detailsCount);
    }

    private Predicate getPredicate(DetailsSearchCriteria detailsSearchCriteria,
                                   Root<DetailsEntity> detailsRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if(Objects.nonNull(detailsSearchCriteria.getMinPrice())){
            predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(detailsRoot.get("price"),
                            + detailsSearchCriteria.getMinPrice() )
            );
        }
        if(Objects.nonNull(detailsSearchCriteria.getMaxPrice())){
            predicates.add(
                    criteriaBuilder.lessThanOrEqualTo(detailsRoot.get("price"),
                            detailsSearchCriteria.getMaxPrice() )
            );
        }
        if(Objects.nonNull(detailsSearchCriteria.getMinMinCpm())){
            predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(detailsRoot.get("minCpm"),
                             detailsSearchCriteria.getMinMinCpm())
            );
        }
        if(Objects.nonNull(detailsSearchCriteria.getMaxMinCpm())){
            predicates.add(
                    criteriaBuilder.lessThanOrEqualTo(detailsRoot.get("minCpm"),
                            + detailsSearchCriteria.getMaxMinCpm() )
            );
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(DetailsPage detailsPage,
                          CriteriaQuery<DetailsEntity> criteriaQuery,
                          Root<DetailsEntity> detailsRoot) {
        if(detailsPage.getSortDirection().equals(Sort.Direction.ASC)){
            criteriaQuery.orderBy(criteriaBuilder.asc(detailsRoot.get(detailsPage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(detailsRoot.get(detailsPage.getSortBy())));
        }
    }

    private Pageable getPageable(DetailsPage detailsPage) {
        Sort sort = Sort.by(detailsPage.getSortDirection(), detailsPage.getSortBy());
        return PageRequest.of(detailsPage.getPageNumber(),detailsPage.getPageSize(), sort);
    }

    private long getDetailsCount(Predicate predicate) {

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<DetailsEntity> countRoot = countQuery.from(DetailsEntity.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

    public List<DetailsEntity> findAllWithFiltersOnly(DetailsSearchCriteria detailsSearchCriteria) {
        DetailsPage detailsPage = DetailsPage.builder()
                .pageNumber(0)
                .pageSize(10)
                .sortBy("price")
                .sortDirection(Sort.Direction.ASC)
                .build();
        CriteriaQuery<DetailsEntity> criteriaQuery = criteriaBuilder.createQuery(DetailsEntity.class);
        Root<DetailsEntity> detailsRoot = criteriaQuery.from(DetailsEntity.class);
        Predicate predicate = getPredicate(detailsSearchCriteria, detailsRoot);
        criteriaQuery.where(predicate);
        setOrder(detailsPage, criteriaQuery, detailsRoot);

        TypedQuery<DetailsEntity> typedQuery = entityManager.createQuery(criteriaQuery);

        List<DetailsEntity> resultList = typedQuery.getResultList();
        return Optional.ofNullable(resultList).orElse(Collections.emptyList());

    }
}
