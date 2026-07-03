package com.delishGo_MSCVs.cliente_mscv.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Embeddable
public class Audit {

    @Column(name = "create_at")
    private LocalDate createdAt;

    @Column(name = "updated")
    private LocalDate updatedAt;

    /**
     * Con este método genero autmaticamente la fecha de creacion de algún
     * elemento
     */

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDate.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDate.now();
    }

}
