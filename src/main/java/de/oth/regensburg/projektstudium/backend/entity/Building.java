package de.oth.regensburg.projektstudium.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Entity
public class Building {
    @OneToMany(mappedBy = "building")
    @JsonIgnore
    private final Collection<Person> people = new HashSet<>();
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
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

    public Collection<Person> getPeople() {
        return people;
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
                Objects.equals(people, building.people);
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
