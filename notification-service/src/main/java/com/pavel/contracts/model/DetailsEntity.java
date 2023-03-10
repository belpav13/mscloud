package com.pavel.contracts.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "DETAILSENTITY")
@Builder
@Data
@Getter
@Setter
@AllArgsConstructor
public class DetailsEntity {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "session_id")
    private long sessionId;
    @Column(name = "advertiser_id")
    private long advertiserId;
    @Column(name = "country")
    private String country;
    @Column(name = "price")
    private double price;
    @Column(name = "event_type_id")
    private int eventTypeId;
    @Column(name = "gdpr")
    private int gdpr;
    @Column(name = "min_cpm")
    private double minCpm;
    @Column(name = "priority")
    private double priority;
    @Column(name = "bid_price")
    private double bidPrice;

    public DetailsEntity() {
    }

    public DetailsEntity( long sessionId, long advertiserId, String country, double price, int eventTypeId, int gdpr, double minCpm, double priority, double bidPrice) {
        this.sessionId = sessionId;
        this.advertiserId = advertiserId;
        this.country = country;
        this.price = price;
        this.eventTypeId = eventTypeId;
        this.gdpr = gdpr;
        this.minCpm = minCpm;
        this.priority = priority;
        this.bidPrice = bidPrice;
    }

    @Override
    public String toString() {
        return "DetailsEntity{" +
                "id=" + id +
                ", sessionId=" + sessionId +
                ", advertiserId=" + advertiserId +
                ", country='" + country + '\'' +
                ", price=" + price +
                ", event_typeId=" + eventTypeId +
                ", gdpr=" + gdpr +
                ", minCpm=" + minCpm +
                ", priority=" + priority +
                ", bidPrice=" + bidPrice +
                '}';
    }
}
