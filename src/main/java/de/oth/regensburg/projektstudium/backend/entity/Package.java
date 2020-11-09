package de.oth.regensburg.projektstudium.backend.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Package {
    private @Id
    @GeneratedValue
    Long id; // ID used for internal tracking which is also printed as a QR code on the package
    private PackageType type;
    private String barcode; // Barcode from external providers (e.g. DHL, DPD, etc.)
    private String orderNumber; // Order number in SAP
    @ManyToOne
    private Person recipient;
    @ManyToOne
    private Person sender;
    @CreationTimestamp
    private LocalDateTime createdTimestamp;
    @UpdateTimestamp
    private LocalDateTime lastUpdatedTimestamp;
    private PackageStatus status = PackageStatus.CREATED;

    public Package() {
    }

    public Package(PackageType type, String barcode, String orderNumber, Person recipient, Person sender, PackageStatus status) {
        this.type = type;
        this.barcode = barcode;
        this.orderNumber = orderNumber;
        this.recipient = recipient;
        this.sender = sender;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PackageType getType() {
        return type;
    }

    public void setType(PackageType type) {
        this.type = type;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Person getRecipient() {
        return recipient;
    }

    public void setRecipient(Person recipient) {
        this.recipient = recipient;
    }

    public Person getSender() {
        return sender;
    }

    public void setSender(Person sender) {
        this.sender = sender;
    }

    public LocalDateTime getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(LocalDateTime createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public LocalDateTime getLastUpdatedTimestamp() {
        return lastUpdatedTimestamp;
    }

    public void setLastUpdatedTimestamp(LocalDateTime lastUpdatedTimestamp) {
        this.lastUpdatedTimestamp = lastUpdatedTimestamp;
    }

    public PackageStatus getStatus() {
        return status;
    }

    public void setStatus(PackageStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Package aPackage = (Package) o;
        return id.equals(aPackage.id) &&
                type == aPackage.type &&
                Objects.equals(barcode, aPackage.barcode) &&
                Objects.equals(orderNumber, aPackage.orderNumber) &&
                Objects.equals(recipient, aPackage.recipient) &&
                Objects.equals(sender, aPackage.sender) &&
                Objects.equals(createdTimestamp, aPackage.createdTimestamp) &&
                Objects.equals(lastUpdatedTimestamp, aPackage.lastUpdatedTimestamp) &&
                status == aPackage.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, barcode, orderNumber, recipient, sender, createdTimestamp, lastUpdatedTimestamp, status);
    }

    @Override
    public String toString() {
        return "Package{" +
                "id=" + id +
                ", type=" + type +
                ", barcode='" + barcode + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", recipient=" + recipient +
                ", sender=" + sender +
                ", createdTimestamp=" + createdTimestamp +
                ", lastUpdatedTimestamp=" + lastUpdatedTimestamp +
                ", status=" + status +
                '}';
    }
}