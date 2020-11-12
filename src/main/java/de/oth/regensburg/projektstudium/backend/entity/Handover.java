package de.oth.regensburg.projektstudium.backend.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Entity
public class Handover {
    @OneToMany(mappedBy = "handover")
    private final Collection<Package> packages = ConcurrentHashMap.newKeySet();
    @Id
    @Type(type = "uuid-char") // Necessary only for mysql
    private UUID uuid;
    @CreationTimestamp
    private LocalDateTime createdTimestamp;
    @UpdateTimestamp
    private LocalDateTime lastUpdatedTimestamp;
    @Enumerated(EnumType.STRING)
    private HandoverStatus status = HandoverStatus.ON_GOING;

    public Handover() {
    }

    public Handover(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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

    public Collection<Package> getPackages() {
        return packages;
    }

    public void addPackage(Package pkg) {
        packages.add(pkg);
        pkg.setHandover(this);
    }

    public void removePackage(Package pkg) {
        pkg.setHandover(null);
        packages.remove(pkg);
    }

    public HandoverStatus getStatus() {
        return status;
    }

    public void setStatus(HandoverStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Handover handover = (Handover) o;
        return uuid.equals(handover.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return "Handover{" +
                "packages=" + packages +
                ", uuid=" + uuid +
                ", createdTimestamp=" + createdTimestamp +
                ", lastUpdatedTimestamp=" + lastUpdatedTimestamp +
                ", status=" + status +
                '}';
    }
}
