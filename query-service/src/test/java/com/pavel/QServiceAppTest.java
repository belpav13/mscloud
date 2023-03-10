package com.pavel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pavel.contracts.model.DetailsPage;
import com.pavel.contracts.model.DetailsSearchCriteria;
import com.pavel.service.QService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class QServiceAppTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private QService qService;



    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
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
        mockMvc.perform(get("/query")
                        .queryParam("minPrice", String.valueOf(detailsSearchCriteria.getMinPrice()))
                        .queryParam("maxPrice", String.valueOf(detailsSearchCriteria.getMaxPrice()))
                        .queryParam("minMinCpm", String.valueOf(detailsSearchCriteria.getMinMinCpm()))
                        .queryParam("maxMinCpm", String.valueOf(detailsSearchCriteria.getMaxMinCpm()))
                        .queryParam("sortDirection", String.valueOf(detailsPage.getSortDirection()))
                        .queryParam("sortBy", String.valueOf(detailsPage.getSortBy()))
                        .queryParam("pageNumber", String.valueOf(detailsPage.getPageNumber()))
                        .queryParam("pageSize", String.valueOf(detailsPage.getPageSize())))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());;

    }



}
