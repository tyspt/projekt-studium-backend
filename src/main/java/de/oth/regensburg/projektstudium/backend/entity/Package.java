package de.oth.regensburg.projektstudium.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.oth.regensburg.projektstudium.backend.entity.deserializers.PackageDeserializer;
import de.oth.regensburg.projektstudium.backend.entity.enums.PackageStatus;
import de.oth.regensburg.projektstudium.backend.entity.enums.PackageType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@JsonDeserialize(using = PackageDeserializer.class)
@NamedEntityGraph(
        name = "package-graph",
        attributeNodes = {
                @NamedAttributeNode(value = "recipient", subgraph = "employee-subgraph"),
                @NamedAttributeNode(value = "sender", subgraph = "employee-subgraph"),
                @NamedAttributeNode("driver"),
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "employee-subgraph",
                        attributeNodes = {
                                @NamedAttributeNode("building"),
                                @NamedAttributeNode(value = "representative", subgraph = "representative-subgraph")
                        }
                ),
        }
)
public class Package {
    @OneToMany(mappedBy = "relatedPackage")
    @OrderBy("timestamp DESC")
    @JsonIgnore
    private final List<ShipmentCourse> shipmentCourses = new ArrayList<>();

    private @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "package_generator")
    @SequenceGenerator(name = "package_generator", sequenceName = "package_seq", initialValue = 10001, allocationSize = 10)
    Long id; // ID used for internal tracking which is also printed as a QR code on the package

    @Enumerated(EnumType.STRING)
    private PackageType type;
    private String barcode; // Barcode from external providers (e.g. DHL, DPD, etc.)
    private String orderNumber; // Order number in SAP

    @CreationTimestamp
    private LocalDateTime createdTimestamp;
    @UpdateTimestamp
    private LocalDateTime lastUpdatedTimestamp;
    @Enumerated(EnumType.STRING)
    private PackageStatus status = PackageStatus.CREATED;

    @ManyToOne
    private Employee recipient;
    @ManyToOne
    private Employee sender;

    @ManyToOne
    @JsonIgnore
    private Handover handover;
    @ManyToOne
    private Driver driver;

    public Package() {
    }

    public Package(PackageType type, String barcode, String orderNumber, Employee recipient, Employee sender) {
        this.type = type;
        this.barcode = barcode;
        this.orderNumber = orderNumber;
        setRecipient(recipient);
        setSender(sender);
    }

    public Package(JsonNode node) {
        if (node != null) {
            this.setId(node.get("id") != null ? node.get("id").asLong() : null);
            this.setBarcode(node.get("barcode") != null ? node.get("barcode").textValue() : null);
            this.setOrderNumber(node.get("orderNumber") != null ? node.get("orderNumber").textValue() : null);
            this.setType(node.get("type") != null ? PackageType.valueOf(node.get("type").asText()) : null);
            this.setStatus(node.get("status") != null ? PackageStatus.valueOf(node.get("status").asText()) : null);

            JsonNode rcpNode = node.get("recipient");
            if (rcpNode != null) {
                this.setRecipient(new Employee(rcpNode));
            }

            JsonNode sndNode = node.get("sender");
            if (sndNode != null) {
                this.setSender(new Employee(sndNode));
            }
        }
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

    public Employee getRecipient() {
        return recipient;
    }

    public void setRecipient(Employee recipient) {
        if (this.recipient != null) {
            this.recipient.getInboundPackages().remove(this);
        }
        this.recipient = recipient;
        recipient.getInboundPackages().add(this);
    }

    public Employee getSender() {
        return sender;
    }

    public void setSender(Employee sender) {
        if (this.sender != null) {
            this.sender.getOutboundPackages().remove(this);
        }
        this.sender = sender;
        sender.getOutboundPackages().add(this);
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

    public Handover getHandover() {
        return handover;
    }

    public void setHandover(Handover handover) {
        this.handover = handover;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        if (this.driver != null) {
            this.driver.getAssignedPackages().remove(driver);
        }
        driver.getAssignedPackages().add(this);
        this.driver = driver;
    }

    public List<ShipmentCourse> getShipmentCourses() {
        return Collections.unmodifiableList(shipmentCourses);
    }

    public void addShipmentCourse(ShipmentCourse shipmentCourse) {
        shipmentCourse.setRelatedPackage(this);
        shipmentCourses.add(shipmentCourse);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Package aPackage = (Package) o;
        return id.equals(aPackage.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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