package com.mycompany.myapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Car2.
 */
@Entity
@Table(name = "car_2")
public class Car2 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Size(max = 2000)
    @Column(name = "description", length = 2000)
    private String description;

    @ManyToOne
    private Owner2 owner2;

    @ManyToOne
    private Owner2 driver2;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Car2 name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Car2 description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Owner2 getOwner2() {
        return owner2;
    }

    public Car2 owner2(Owner2 owner2) {
        this.owner2 = owner2;
        return this;
    }

    public void setOwner2(Owner2 owner2) {
        this.owner2 = owner2;
    }

    public Owner2 getDriver2() {
        return driver2;
    }

    public Car2 driver2(Owner2 owner2) {
        this.driver2 = owner2;
        return this;
    }

    public void setDriver2(Owner2 owner2) {
        this.driver2 = owner2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car2 car2 = (Car2) o;
        if (car2.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), car2.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Car2{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
