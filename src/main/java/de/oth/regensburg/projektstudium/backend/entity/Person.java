package de.oth.regensburg.projektstudium.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Entity
public class Person {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String name;
    private String email;
    private String telephone;
    @ManyToOne
    private Building building;
    private String fullAddress;

    @OneToMany(mappedBy = "recipient")
    @JsonIgnore
    private Collection<Package> inboundPackages = new HashSet<>();
    @OneToMany(mappedBy = "sender")
    @JsonIgnore
    private Collection<Package> outboundPackages = new HashSet<>();

    @ManyToOne
    private Person representative;
    @OneToMany
    @JsonIgnore
    private Collection<Person> representativeOf = new HashSet<>();

    public Person() {
    }

    public Person(String name, String email, String telephone, Building building, String fullAddress) {
        this.name = name;
        this.email = email;
        this.telephone = telephone;
        setBuilding(building);
        this.fullAddress = fullAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long employeeId) {
        this.id = employeeId;
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

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        if (this.building != null) {
            this.building.getPeople().remove(this);
        }
        this.building = building;
        building.getPeople().add(this);
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public Collection<Package> getOutboundPackages() {
        return outboundPackages;
    }

    public Collection<Package> getInboundPackages() {
        return inboundPackages;
    }

    public Person getRepresentative() {
        return representative;
    }

    public void setRepresentative(Person representative) {
        if (representative == null) {
            this.representative.getRepresentativeOf().remove(this);
            this.representative = null;
        }
        this.representative = representative;
        this.representative.getRepresentativeOf().add(this);
    }

    public Collection<Person> getRepresentativeOf() {
        return representativeOf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id.equals(person.id) &&
                name.equals(person.name) &&
                Objects.equals(email, person.email) &&
                Objects.equals(telephone, person.telephone) &&
                Objects.equals(building, person.building) &&
                Objects.equals(fullAddress, person.fullAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, telephone, building, fullAddress);
    }

    @Override
    public String toString() {
        return "Person{" +
                "employeeId=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", building=" + building.getShortName() +
                ", fullAddress='" + fullAddress + '\'' +
                '}';
    }
}
