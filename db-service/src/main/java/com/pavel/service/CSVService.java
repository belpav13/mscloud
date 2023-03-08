package com.pavel.service;

import java.io.IOException;
import java.util.List;

import com.pavel.helper.CSVHelper;
import com.pavel.model.DetailsEntity;
import com.pavel.repository.DBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



@Service
public class CSVService {
    @Autowired
    DBRepository repository;

    public void save(MultipartFile file) {
        try {
            List<DetailsEntity> detailsEntities = CSVHelper.csvToDetailsEntities(file.getInputStream());
            repository.saveAll(detailsEntities);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public List<DetailsEntity> getAllDetails() {
        return repository.findAll();
    }

}
