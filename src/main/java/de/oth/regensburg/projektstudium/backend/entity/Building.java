package de.oth.regensburg.projektstudium.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.oth.regensburg.projektstudium.backend.entity.deserializers.BuildingDeserializer;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Entity
@JsonDeserialize(using = BuildingDeserializer.class)
public class Building {
    @OneToMany(mappedBy = "building")
    @JsonIgnore
    private final Collection<Employee> employees = new HashSet<>();
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(unique = true)
    private String shortName;
    private String fullName;
    private String description;
    private String address;

    public Building() {
    }

    public Building(String shortName, String fullName, String description, String address) {
        this.shortName = shortName;
        this.fullName = fullName;
        this.description = description;
        this.address = address;
    }

    public Building(JsonNode node) {
        if (node != null) {
            this.setId(node.get("id") != null ? node.get("id").asLong() : null);
            this.setShortName(node.get("shortName") != null ? node.get("shortName").asText() : null);
            this.setFullName(node.get("fullName") != null ? node.get("fullName").asText() : null);
            this.setDescription(node.get("description") != null ? node.get("description").asText() : null);
            this.setAddress(node.get("address") != null ? node.get("address").asText() : null);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Collection<Employee> getEmployees() {
        return employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Building building = (Building) o;
        return id.equals(building.id) &&
                Objects.equals(shortName, building.shortName) &&
                Objects.equals(fullName, building.fullName) &&
                Objects.equals(description, building.description) &&
                Objects.equals(address, building.address) &&
                Objects.equals(employees, building.employees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Building{" +
                "id=" + id +
                ", shortName='" + shortName + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
