package com.pavel.contracts.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Data
@Getter
@Setter
public class DetailsSearchCriteria {
    private double minPrice;
    private double maxPrice;
    private int minMinCpm;
    private int maxMinCpm;

}
