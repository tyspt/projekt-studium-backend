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
    private String data;

    public Signature() {
    }

    public Signature(Package relatedPackage, String data) {
        this.relatedPackage = relatedPackage;
        this.data = data;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Signature{" +
                "id=" + id +
                ", relatedPackage=" + relatedPackage +
                ", data='" + data + '\'' +
                '}';
    }
}
