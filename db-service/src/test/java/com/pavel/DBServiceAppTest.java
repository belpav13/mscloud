package com.pavel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.MediaType;
import com.pavel.model.DetailsPage;
import com.pavel.model.DetailsSearchCriteria;
import com.pavel.repository.CriteriaRepository;
import com.pavel.repository.DBRepository;
import lombok.Data;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DBServiceAppTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private DBRepository dbRepository;
    @Autowired
    private CriteriaRepository criteriaRepository;



    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    //@Test
    void shouldPopulateDB() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(multipart("/upload").file(loadFile()))
                .andExpect(status().isOk());
        Assertions.assertEquals(171, dbRepository.findAll().size());
    }

    public static MockMultipartFile loadFile() throws IOException {
        File targetFile = new File("listing-details.csv");
        MockMultipartFile multipartFile = new MockMultipartFile("file", "listing-details.csv", String.valueOf(MediaType.FORM_DATA), new FileInputStream(targetFile));
        return multipartFile;
    }

    @Test
    void shouldGetAllFromDBByCriteria() throws Exception {
        DetailsSearchCriteria detailsSearchCriteria = DetailsSearchCriteria.builder()
                .maxPrice(0.0081)
                .minPrice(0.0081)
                .minMinCpm(1)
                .maxMinCpm(2)
                .build();
        DetailsPage detailsPage = DetailsPage.builder()
                .pageNumber(1)
                .pageSize(20)
                .sortBy("price")
                .sortDirection(Sort.Direction.ASC)
                .build();

        mockMvc.perform(get("/listingsall")
                        .queryParam("minPrice", String.valueOf(detailsSearchCriteria.getMinPrice()))
                        .queryParam("maxPrice", String.valueOf(detailsSearchCriteria.getMaxPrice()))
                        .queryParam("minMinCpm", String.valueOf(detailsSearchCriteria.getMinMinCpm()))
                        .queryParam("maxMinCpm", String.valueOf(detailsSearchCriteria.getMaxMinCpm())))
                .andExpect(status().isOk());

    }

    @Test
    void shouldGetAllFInternalQuery() throws Exception {
        DetailsSearchCriteria detailsSearchCriteria = DetailsSearchCriteria.builder()
                .maxPrice(0.0081)
                .minPrice(0.0081)
                .minMinCpm(1)
                .maxMinCpm(2)
                .build();
        DetailsPage detailsPage = DetailsPage.builder()
                .pageNumber(1)
                .pageSize(20)
                .sortBy("price")
                .sortDirection(Sort.Direction.ASC)
                .build();

        mockMvc.perform(get("/listingsquery")
                        .queryParam("minPrice", String.valueOf(detailsSearchCriteria.getMinPrice()))
                        .queryParam("maxPrice", String.valueOf(detailsSearchCriteria.getMaxPrice()))
                        .queryParam("minMinCpm", String.valueOf(detailsSearchCriteria.getMinMinCpm()))
                        .queryParam("maxMinCpm", String.valueOf(detailsSearchCriteria.getMaxMinCpm())))
                .andExpect(status().isOk());

    }

    @Test
    void shouldGetAllFromDBByCriteriaAndPageing() throws Exception {
        DetailsSearchCriteria detailsSearchCriteria = DetailsSearchCriteria.builder()
                .maxPrice(0.0081)
                .minPrice(0.0081)
                .minMinCpm(1)
                .maxMinCpm(2)
                .build();
        DetailsPage detailsPage = DetailsPage.builder()
                .pageNumber(1)
                .pageSize(20)
                .sortBy("price")
                .sortDirection(Sort.Direction.ASC)
                .build();
        mockMvc.perform(get("/listings")
                        .queryParam("minPrice", String.valueOf(detailsSearchCriteria.getMinPrice()))
                        .queryParam("maxPrice", String.valueOf(detailsSearchCriteria.getMaxPrice()))
                        .queryParam("minMinCpm", String.valueOf(detailsSearchCriteria.getMinMinCpm()))
                        .queryParam("maxMinCpm", String.valueOf(detailsSearchCriteria.getMaxMinCpm()))
                        .queryParam("sortDirection", String.valueOf(detailsPage.getSortDirection()))
                        .queryParam("sortBy", String.valueOf(detailsPage.getSortBy()))
                        .queryParam("pageNumber", String.valueOf(detailsPage.getPageNumber()))
                        .queryParam("pageSize", String.valueOf(detailsPage.getPageSize())))
                .andExpect(status().isOk());

    }



}
