package de.oth.regensburg.projektstudium.backend.entity;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class Location {
    private Double latitude;
    private Double longitude;
    private Double accuracy;
    private LocalDateTime lastUpdatedTimestamp;

    public Location() {
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        if (latitude < -90d || latitude > 90d) {
            throw new RuntimeException("Invalid latitude value");
        }
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        if (longitude < -180d || longitude > 180d) {
            throw new RuntimeException("Invalid longitude value");
        }
        this.longitude = longitude;
    }

    public Double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Double accuracy) {
        this.accuracy = (accuracy != null) ? accuracy : 1000d;
    }

    public LocalDateTime getLastUpdatedTimestamp() {
        return lastUpdatedTimestamp;
    }

    public void setLastUpdatedTimestamp(LocalDateTime lastUpdatedTimestamp) {
        this.lastUpdatedTimestamp = lastUpdatedTimestamp;
    }
}
