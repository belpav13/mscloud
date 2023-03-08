package com.pavel.controller;

import com.pavel.helper.CSVHelper;
import com.pavel.message.ResponseMessage;
import com.pavel.model.DetailsEntity;
import com.pavel.model.DetailsPage;
import com.pavel.model.DetailsSearchCriteria;
import com.pavel.service.CSVService;
import com.pavel.service.QService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping
public class DBController {
    private final CSVService fileService;
    private final QService qService;
    public DBController(CSVService fileService, QService qService) {
        this.qService = qService;
        this.fileService = fileService;
    }




    @GetMapping("/listings")
    public ResponseEntity<Page<DetailsEntity>> getDetails(DetailsSearchCriteria detailsSearchCriteria, DetailsPage detailsPage
    ) {
        return new ResponseEntity<>(qService.getDetails(detailsPage, detailsSearchCriteria),
                HttpStatus.OK);
    }

    @GetMapping("/listingsquery")
    public ResponseEntity<List<DetailsEntity>> getDetailsFiltered(DetailsSearchCriteria detailsSearchCriteria
    ) {
        return new ResponseEntity<>(qService.getDetailsFiltered( detailsSearchCriteria),
                HttpStatus.OK);
    }

    @GetMapping("/listingsall")
    public ResponseEntity<List<DetailsEntity>> getDetails(DetailsSearchCriteria detailsSearchCriteria
    ) {
        return new ResponseEntity<>(fileService.getAllDetails(),
                HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (CSVHelper.hasCSVFormat(file)) {
            try {
                fileService.save(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

}
