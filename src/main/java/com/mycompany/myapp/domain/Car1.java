package com.mycompany.myapp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Car1.
 */
@Entity
@Table(name = "car_1")
public class Car1 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private Owner1 owner1;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Car1 name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Owner1 getOwner1() {
        return owner1;
    }

    public Car1 owner1(Owner1 owner1) {
        this.owner1 = owner1;
        return this;
    }

    public void setOwner1(Owner1 owner1) {
        this.owner1 = owner1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car1 car1 = (Car1) o;
        if (car1.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), car1.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Car1{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
