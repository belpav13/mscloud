package com.pavel.service;


import com.pavel.event.QueryEvent;
import com.pavel.model.DetailsEntity;
import com.pavel.model.DetailsPage;
import com.pavel.model.DetailsSearchCriteria;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;


@Service
@Transactional
//@AllArgsConstructor
public class QService {
    @Value("${db-service.listingsquery.uri}")
    private String uri;
    private final WebClient.Builder webClientBuilder;
    private final Tracer tracer;
    private KafkaTemplate<String, QueryEvent> kafkaTemplate;

    public QService( WebClient.Builder webClientBuilder, Tracer tracer, KafkaTemplate<String, QueryEvent> kafkaTemplate) {
        this.webClientBuilder = webClientBuilder;
        this.tracer = tracer;
        this.kafkaTemplate = kafkaTemplate;
    }

    public List<DetailsEntity> getAllDetails() {
        return webClientBuilder.build().get()
                .uri(uri,
                        uriBuilder -> uriBuilder
                                .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<DetailsEntity>>(){})
                .block();
    }


    public Page<DetailsEntity> getDetails(DetailsPage detailsPage,
                                          DetailsSearchCriteria detailsSearchCriteria) {
        Span detailsServiceLookup = tracer.nextSpan().name("DetailsServiceLookup");

        try (Tracer.SpanInScope isLookup = tracer.withSpan(detailsServiceLookup.start())) {
        List<DetailsEntity> result = webClientBuilder.build().get()
                .uri(uri,
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
        kafkaTemplate.send("notificationTopic", new QueryEvent(String.valueOf(result.size())));
          return new PageImpl(result,getPageable(detailsPage) , result.size());

        } finally {
            detailsServiceLookup.end();
        }
    }

    private Pageable getPageable(DetailsPage detailsPage) {
        Sort sort = Sort.by(detailsPage.getSortDirection(), detailsPage.getSortBy());
        return PageRequest.of(detailsPage.getPageNumber(),detailsPage.getPageSize(), sort);
    }
}
