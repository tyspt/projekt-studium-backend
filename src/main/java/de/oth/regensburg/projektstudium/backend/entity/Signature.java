package de.oth.regensburg.projektstudium.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Signature {
    @Id
    private Long id;
    @OneToOne
    @MapsId
    @JsonIgnore
    private Package relatedPackage;
    @Lob
    private String dataBlob;

    public Signature() {
    }

    public Signature(Package relatedPackage, String dataBlob) {
        this.relatedPackage = relatedPackage;
        this.dataBlob = dataBlob;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Package getRelatedPackage() {
        return relatedPackage;
    }

    public void setRelatedPackage(Package relatedPackage) {
        this.relatedPackage = relatedPackage;
    }

    public String getDataBlob() {
        return dataBlob;
    }

    public void setDataBlob(String dataBlob) {
        this.dataBlob = dataBlob;
    }

    @Override
    public String toString() {
        return "Signature{" +
                "id=" + id +
                ", relatedPackage=" + relatedPackage +
                ", dataBlob='" + dataBlob + '\'' +
                '}';
    }
}
