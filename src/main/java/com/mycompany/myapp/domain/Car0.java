package com.mycompany.myapp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Car0.
 */
@Entity
@Table(name = "car_0")
public class Car0 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private Owner0 owner0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Car0 name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Owner0 getOwner0() {
        return owner0;
    }

    public Car0 owner0(Owner0 owner0) {
        this.owner0 = owner0;
        return this;
    }

    public void setOwner0(Owner0 owner0) {
        this.owner0 = owner0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car0 car0 = (Car0) o;
        if (car0.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), car0.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Car0{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}