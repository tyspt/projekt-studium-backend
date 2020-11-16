package de.oth.regensburg.projektstudium.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Entity
public class Employee {
    @OneToMany(mappedBy = "recipient")
    @JsonIgnore
    private final Collection<Package> inboundPackages = new HashSet<>();
    @OneToMany(mappedBy = "sender")
    @JsonIgnore
    private final Collection<Package> outboundPackages = new HashSet<>();
    @OneToMany(mappedBy = "representative")
    @JsonIgnore
    private final Collection<Employee> representativeOf = new HashSet<>();
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String name;
    private String email;
    private String telephone;
    @ManyToOne
    private Building building;
    private String fullAddress;
    @ManyToOne
    private Employee representative;

    public Employee() {
    }

    public Employee(String name, String email, String telephone, Building building, String fullAddress) {
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
            this.building.getEmployees().remove(this);
        }
        this.building = building;
        building.getEmployees().add(this);
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

    public Employee getRepresentative() {
        return representative;
    }

    public void setRepresentative(Employee representative) {
        if (representative == null) {
            this.representative.getRepresentativeOf().remove(this);
            this.representative = null;
        }
        this.representative = representative;
        this.representative.getRepresentativeOf().add(this);
    }

    public Collection<Employee> getRepresentativeOf() {
        return representativeOf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id.equals(employee.id) &&
                name.equals(employee.name) &&
                Objects.equals(email, employee.email) &&
                Objects.equals(telephone, employee.telephone) &&
                Objects.equals(building, employee.building) &&
                Objects.equals(fullAddress, employee.fullAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", building=" + building +
                ", fullAddress='" + fullAddress + '\'' +
                ", representative='" + representative + '\'' +
                '}';
    }
}
