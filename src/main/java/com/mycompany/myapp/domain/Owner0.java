package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Owner (1) <-----> (*) Car
 */
@ApiModel(description = "Owner (1) <-----> (*) Car")
@Entity
@Table(name = "owner_0")
public class Owner0 implements Serializable {

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

    @OneToMany(mappedBy = "owner0")
    @JsonIgnore
    private Set<Car0> car0S = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Owner0 name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Owner0 description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Car0> getCar0S() {
        return car0S;
    }

    public Owner0 car0S(Set<Car0> car0S) {
        this.car0S = car0S;
        return this;
    }

    public Owner0 addCar0(Car0 car0) {
        this.car0S.add(car0);
        car0.setOwner0(this);
        return this;
    }

    public Owner0 removeCar0(Car0 car0) {
        this.car0S.remove(car0);
        car0.setOwner0(null);
        return this;
    }

    public void setCar0S(Set<Car0> car0S) {
        this.car0S = car0S;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Owner0 owner0 = (Owner0) o;
        if (owner0.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), owner0.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Owner0{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
