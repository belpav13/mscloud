package com.pavel.service;

import com.pavel.model.DetailsEntity;
import com.pavel.model.DetailsPage;
import com.pavel.model.DetailsSearchCriteria;
import com.pavel.repository.CriteriaRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class QService {
    private final CriteriaRepository criteriaRepository;

    public QService(CriteriaRepository criteriaRepository) {
        this.criteriaRepository = criteriaRepository;
    }


    public Page<DetailsEntity> getDetails(DetailsPage detailsPage,
                                          DetailsSearchCriteria detailsSearchCriteria) {
        return criteriaRepository.findAllWithFilters(detailsPage, detailsSearchCriteria);
    }

    @SneakyThrows
    public List<DetailsEntity> getDetailsFiltered(
            DetailsSearchCriteria detailsSearchCriteria) {
        //Circuitbreaker test
//        log.info("Wait starting");
//        Thread.sleep(10000);
//        log.info("Wait ending");
        return criteriaRepository.findAllWithFiltersOnly(detailsSearchCriteria);
    }

}
