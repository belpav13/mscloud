package com.pavel.controller;

import com.pavel.contracts.model.DetailsEntity;
import com.pavel.contracts.model.DetailsPage;
import com.pavel.contracts.model.DetailsSearchCriteria;
import com.pavel.service.QService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class QCCBontroller {

    private final QService qService;

    @GetMapping("/cbquery")
    @ResponseStatus(HttpStatus.OK)
    @CircuitBreaker(name = "details", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "details")
    @Retry(name = "details")
    public CompletableFuture<Page<DetailsEntity>> getDetails(DetailsSearchCriteria detailsSearchCriteria, DetailsPage detailsPage
    ) {
        return CompletableFuture.supplyAsync(() -> qService.getDetails(detailsPage, detailsSearchCriteria));
    }

    public CompletableFuture<String> fallbackMethod(DetailsSearchCriteria detailsSearchCriteria, DetailsPage detailsPage, RuntimeException runtimeException){
        return  CompletableFuture.supplyAsync(() -> "Something went wrong! Try later.");
    }
}
