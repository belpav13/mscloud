package com.pavel.service;

import com.pavel.model.DetailsEntity;
import com.pavel.model.DetailsPage;
import com.pavel.model.DetailsSearchCriteria;
import com.pavel.repository.CriteriaRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QService {
    private final CriteriaRepository criteriaRepository;

    public QService(CriteriaRepository criteriaRepository) {
        this.criteriaRepository = criteriaRepository;
    }


    public Page<DetailsEntity> getDetails(DetailsPage detailsPage,
                                          DetailsSearchCriteria detailsSearchCriteria) {
        return criteriaRepository.findAllWithFilters(detailsPage, detailsSearchCriteria);
    }

    public List<DetailsEntity> getDetailsFiltered(
            DetailsSearchCriteria detailsSearchCriteria) {
        return criteriaRepository.findAllWithFiltersOnly(detailsSearchCriteria);
    }

}
