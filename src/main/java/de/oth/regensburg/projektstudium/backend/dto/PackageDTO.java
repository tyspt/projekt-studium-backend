package de.oth.regensburg.projektstudium.backend.dto;

import de.oth.regensburg.projektstudium.backend.entity.enums.PackageStatus;
import de.oth.regensburg.projektstudium.backend.entity.enums.PackageType;

import java.time.LocalDateTime;
import java.util.Objects;

public class PackageDTO {
    private String id;
    private PackageType type;
    private String barcode;
    private String orderNumber;

    private String recipientId;
    private String recipientName;
    private String recipientEmail;
    private String recipientTelephone;
    private String recipientBuildingId;
    private String recipientBuildingShortName;
    private String recipientFullAddress;
    private String representativeId;
    private String representativeName;

    private String senderId;
    private String senderName;

    private LocalDateTime createdTimestamp;
    private LocalDateTime lastUpdatedTimestamp;
    private PackageStatus status;

    private String driverId;
    private String driverName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public String getRecipientTelephone() {
        return recipientTelephone;
    }

    public void setRecipientTelephone(String recipientTelephone) {
        this.recipientTelephone = recipientTelephone;
    }

    public String getRecipientBuildingId() {
        return recipientBuildingId;
    }

    public void setRecipientBuildingId(String recipientBuildingId) {
        this.recipientBuildingId = recipientBuildingId;
    }

    public String getRecipientBuildingShortName() {
        return recipientBuildingShortName;
    }

    public void setRecipientBuildingShortName(String recipientBuildingShortName) {
        this.recipientBuildingShortName = recipientBuildingShortName;
    }

    public String getRecipientFullAddress() {
        return recipientFullAddress;
    }

    public void setRecipientFullAddress(String recipientFullAddress) {
        this.recipientFullAddress = recipientFullAddress;
    }

    public String getRepresentativeId() {
        return representativeId;
    }

    public void setRepresentativeId(String representativeId) {
        this.representativeId = representativeId;
    }

    public String getRepresentativeName() {
        return representativeName;
    }

    public void setRepresentativeName(String representativeName) {
        this.representativeName = representativeName;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
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

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PackageDTO that = (PackageDTO) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "PackageDTO{" +
                "id=" + id +
                ", type=" + type +
                ", barcode='" + barcode + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", recipientId=" + recipientId +
                ", recipientName='" + recipientName + '\'' +
                ", recipientEmail='" + recipientEmail + '\'' +
                ", recipientTelephone='" + recipientTelephone + '\'' +
                ", recipientBuildingId='" + recipientBuildingId + '\'' +
                ", recipientBuildingShortName='" + recipientBuildingShortName + '\'' +
                ", recipientFullAddress='" + recipientFullAddress + '\'' +
                ", representativeId='" + representativeId + '\'' +
                ", representativeName='" + representativeName + '\'' +
                ", senderId=" + senderId +
                ", senderName='" + senderName + '\'' +
                ", createdTimestamp=" + createdTimestamp +
                ", lastUpdatedTimestamp=" + lastUpdatedTimestamp +
                ", status=" + status +
                ", driverId=" + driverId +
                ", driverName='" + driverName + '\'' +
                '}';
    }
}
