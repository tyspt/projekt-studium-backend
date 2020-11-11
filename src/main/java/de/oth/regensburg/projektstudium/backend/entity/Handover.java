package de.oth.regensburg.projektstudium.backend.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
public class Handover {
    @OneToMany(mappedBy = "handover")
    private final Collection<Package> packages = new HashSet<>();
    @Id
    @Type(type = "uuid-char") // Necessary only for mysql
    @GeneratedValue
    private UUID uuid;
    @CreationTimestamp
    private LocalDateTime createdTimestamp;
    @UpdateTimestamp
    private LocalDateTime lastUpdatedTimestamp;
    private boolean completed = false;

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

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Handover handover = (Handover) o;
        return completed == handover.completed &&
                uuid.equals(handover.uuid) &&
                Objects.equals(createdTimestamp, handover.createdTimestamp) &&
                Objects.equals(lastUpdatedTimestamp, handover.lastUpdatedTimestamp) &&
                Objects.equals(packages, handover.packages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, createdTimestamp, lastUpdatedTimestamp, packages, completed);
    }

    @Override
    public String toString() {
        return "Handover{" +
                "id=" + uuid +
                ", createdTimestamp=" + createdTimestamp +
                ", lastUpdatedTimestamp=" + lastUpdatedTimestamp +
                ", Packages=" + packages.stream().map(p -> p.getId()).collect(Collectors.toList()) +
                ", completed=" + completed +
                '}';
    }
}
