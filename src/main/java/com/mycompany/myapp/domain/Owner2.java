package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Owner2 (1) <---drives---> (*) Car2
 */
@ApiModel(description = "Owner2 (1) <---drives---> (*) Car2")
@Entity
@Table(name = "owner_2")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "owner2")
public class Owner2 implements Serializable {

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

    @OneToMany(mappedBy = "owner2")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Car2> ownedCar2S = new HashSet<>();

    @OneToMany(mappedBy = "driver2")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Car2> drivedCar2S = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Owner2 name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Owner2 description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Car2> getOwnedCar2S() {
        return ownedCar2S;
    }

    public Owner2 ownedCar2S(Set<Car2> car2S) {
        this.ownedCar2S = car2S;
        return this;
    }

    public Owner2 addOwnedCar2(Car2 car2) {
        this.ownedCar2S.add(car2);
        car2.setOwner2(this);
        return this;
    }

    public Owner2 removeOwnedCar2(Car2 car2) {
        this.ownedCar2S.remove(car2);
        car2.setOwner2(null);
        return this;
    }

    public void setOwnedCar2S(Set<Car2> car2S) {
        this.ownedCar2S = car2S;
    }

    public Set<Car2> getDrivedCar2S() {
        return drivedCar2S;
    }

    public Owner2 drivedCar2S(Set<Car2> car2S) {
        this.drivedCar2S = car2S;
        return this;
    }

    public Owner2 addDrivedCar2(Car2 car2) {
        this.drivedCar2S.add(car2);
        car2.setDriver2(this);
        return this;
    }

    public Owner2 removeDrivedCar2(Car2 car2) {
        this.drivedCar2S.remove(car2);
        car2.setDriver2(null);
        return this;
    }

    public void setDrivedCar2S(Set<Car2> car2S) {
        this.drivedCar2S = car2S;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Owner2 owner2 = (Owner2) o;
        if (owner2.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), owner2.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Owner2{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
