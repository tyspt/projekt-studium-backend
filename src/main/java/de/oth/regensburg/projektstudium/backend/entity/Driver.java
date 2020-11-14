package de.oth.regensburg.projektstudium.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Entity
public class Driver {
    @OneToMany(mappedBy = "driver")
    @JsonIgnore
    private final Collection<Package> assignedPackages = new HashSet<>();
    @OneToMany(mappedBy = "driver")
    @JsonIgnore
    private final Collection<Handover> handovers = new HashSet<>();

    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String name;
    private String email;
    private String telephone;
    private String company;

    // Last known GPS location
    private double latitude;
    private double longitude;
    @UpdateTimestamp
    private LocalDateTime lastUpdatedTimestamp;

    public Driver() {
    }

    public Driver(String name, String email, String telephone, String company) {
        this.name = name;
        this.email = email;
        this.telephone = telephone;
        this.company = company;
    }

    public Collection<Package> getAssignedPackages() {
        return assignedPackages;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public LocalDateTime getLastUpdatedTimestamp() {
        return lastUpdatedTimestamp;
    }

    public void setLastUpdatedTimestamp(LocalDateTime lastUpdatedTimestamp) {
        this.lastUpdatedTimestamp = lastUpdatedTimestamp;
    }

    public Collection<Handover> getHandovers() {
        return handovers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return id.equals(driver.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", company='" + company + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", lastUpdatedTimestamp=" + lastUpdatedTimestamp +
                '}';
    }
}
