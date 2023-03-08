package com.pavel.model;


import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "DETAILSENTITY")
public class DetailsEntity {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "session_id")
    private Long sessionId;
    @Column(name = "advertiser_id")
    private Long advertiserId;
    @Column(name = "country")
    private String country;
    @Column(name = "price")
    private Double price;
    @Column(name = "event_type_id")
    private Integer eventTypeId;
    @Column(name = "gdpr")
    private Integer gdpr;
    @Column(name = "min_cpm")
    private Double minCpm;
    @Column(name = "priority")
    private Double priority;
    @Column(name = "bid_price")
    private Double bidPrice;

    public DetailsEntity() {
    }

    public DetailsEntity( Long sessionId, Long advertiserId, String country, Double price, Integer eventTypeId, Integer gdpr, Double minCpm, Double priority, Double bidPrice) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public Long getAdvertiserId() {
        return advertiserId;
    }

    public void setAdvertiserId(Long advertiserId) {
        this.advertiserId = advertiserId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getGdpr() {
        return gdpr;
    }

    public void setGdpr(Integer gdpr) {
        this.gdpr = gdpr;
    }

    public Double getMinCpm() {
        return minCpm;
    }

    public void setMinCpm(Double minCpm) {
        this.minCpm = minCpm;
    }

    public Double getPriority() {
        return priority;
    }

    public void setPriority(Double priority) {
        this.priority = priority;
    }

    public Double getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(Double bidPrice) {
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
