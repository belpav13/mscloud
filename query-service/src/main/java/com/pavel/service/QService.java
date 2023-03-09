package com.pavel.service;


import com.pavel.model.DetailsEntity;
import com.pavel.model.DetailsPage;
import com.pavel.model.DetailsSearchCriteria;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;


@Service
@Transactional
public class QService {
    private final WebClient.Builder webClientBuilder;

    public QService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public List<DetailsEntity> getAllDetails() {
        return webClientBuilder.build().get()
                .uri("http://db-service/listingsall",
                        uriBuilder -> uriBuilder
                                .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<DetailsEntity>>(){})
                .block();
    }


    public Page<DetailsEntity> getDetails(DetailsPage detailsPage,
                                          DetailsSearchCriteria detailsSearchCriteria) {
        List<DetailsEntity> result = webClientBuilder.build().get()
                .uri("http://db-service/listingsquery",
                        uriBuilder -> uriBuilder
                                .queryParam("minPrice", detailsSearchCriteria.getMinPrice())
                                .queryParam("maxPrice", detailsSearchCriteria.getMaxPrice())
                                .queryParam("minMinCpm", detailsSearchCriteria.getMinMinCpm())
                                .queryParam("maxMinCpm", detailsSearchCriteria.getMaxMinCpm())
                                .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<DetailsEntity>>(){})
                .block();
        detailsPage.setSortBy("price");
        detailsPage.setSortDirection(Sort.Direction.ASC);
          return new PageImpl(result,getPageable(detailsPage) , result.size());


    }

    private Pageable getPageable(DetailsPage detailsPage) {
        Sort sort = Sort.by(detailsPage.getSortDirection(), detailsPage.getSortBy());
        return PageRequest.of(detailsPage.getPageNumber(),detailsPage.getPageSize(), sort);
    }
}
