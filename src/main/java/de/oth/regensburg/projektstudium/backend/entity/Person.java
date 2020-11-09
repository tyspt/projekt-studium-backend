package de.oth.regensburg.projektstudium.backend.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Entity
public class Person {
    private @Id
    @GeneratedValue
    Long id;
    private String name;
    private String email;
    private String telephone;
    @ManyToOne
    private Building building;
    private String fullAddress;

    @OneToMany
    private Collection<Package> outboundPackages = new HashSet<>();
    @OneToMany
    private Collection<Package> inboundPackages = new HashSet<>();

    @ManyToOne
    private Person representative;
    @OneToMany
    private Collection<Person> representativeOf = new HashSet<>();

    public Person() {
    }

    public Person(String name, String email, String telephone, Building building, String fullAddress) {
        this.name = name;
        this.email = email;
        this.telephone = telephone;
        this.building = building;
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
        this.building = building;
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

    public void setOutboundPackages(Collection<Package> outboundPackages) {
        this.outboundPackages = outboundPackages;
    }

    public Collection<Package> getInboundPackages() {
        return inboundPackages;
    }

    public void setInboundPackages(Collection<Package> inboundPackages) {
        this.inboundPackages = inboundPackages;
    }

    public Person getRepresentative() {
        return representative;
    }

    public void setRepresentative(Person representative) {
        this.representative = representative;
    }

    public Collection<Person> getRepresentativeOf() {
        return representativeOf;
    }

    public void setRepresentativeOf(Collection<Person> representativeOf) {
        this.representativeOf = representativeOf;
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
                ", building=" + building +
                ", fullAddress='" + fullAddress + '\'' +
                '}';
    }
}
