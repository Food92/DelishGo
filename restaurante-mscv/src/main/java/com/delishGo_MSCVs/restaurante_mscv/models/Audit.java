package com.delishGo_MSCVs.restaurante_mscv.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Embeddable
@Setter
@Getter
@ToString
@NoArgsConstructor

public class Audit {
    @Column(name = "created_at")
    private LocalDate createAt;

    @Column(name = "updated_at")
    private LocalDate updateAt;

    /**
     * Con este método genero autmaticamente la fecha de creacion de algún
     * elemento
     */

    @PrePersist
    public void prePersist() {
        this.createAt = LocalDate.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updateAt = LocalDate.now();
    }

}
