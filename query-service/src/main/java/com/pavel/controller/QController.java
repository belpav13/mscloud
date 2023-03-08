package com.pavel.controller;

import com.pavel.model.DetailsEntity;
import com.pavel.model.DetailsPage;
import com.pavel.model.DetailsSearchCriteria;
import com.pavel.service.QService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class QController {

    private final QService qService;

    public QController(QService qService) {
        this.qService = qService;
    }

    @GetMapping("/queryall")
    public ResponseEntity<List<DetailsEntity>> getDetails() {
        return new ResponseEntity<>(qService.getAllDetails(),
                HttpStatus.OK);
    }

    @GetMapping("/query")
    public ResponseEntity<Page<DetailsEntity>> getDetails(DetailsSearchCriteria detailsSearchCriteria, DetailsPage detailsPage
    ) {
        return new ResponseEntity<>(qService.getDetails(detailsPage, detailsSearchCriteria),
                HttpStatus.OK);
    }
}
