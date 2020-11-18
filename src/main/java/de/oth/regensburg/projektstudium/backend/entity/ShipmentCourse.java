package de.oth.regensburg.projektstudium.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class ShipmentCourse {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    @JsonIgnore
    private Package relatedPackage;
    private String message;
    @CreationTimestamp
    private LocalDateTime timestamp;

    public ShipmentCourse() {
    }

    public ShipmentCourse(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public Package getRelatedPackage() {
        return relatedPackage;
    }

    public void setRelatedPackage(Package relatedPackage) {
        this.relatedPackage = relatedPackage;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShipmentCourse that = (ShipmentCourse) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ShipmentCourse{" +
                "id=" + id +
                ", relatedPackage=" + relatedPackage +
                ", message=" + message +
                ", timestamp=" + timestamp +
                '}';
    }
}
