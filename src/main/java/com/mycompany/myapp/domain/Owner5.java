package com.mycompany.myapp.domain;

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
 * A Owner5.
 */
@Entity
@Table(name = "owner_5")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "owner5")
public class Owner5 implements Serializable {

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

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "owner5_car5",
               joinColumns = @JoinColumn(name="owner5s_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="car5s_id", referencedColumnName="id"))
    private Set<Car5> car5S = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Owner5 name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Owner5 description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Car5> getCar5S() {
        return car5S;
    }

    public Owner5 car5S(Set<Car5> car5S) {
        this.car5S = car5S;
        return this;
    }

    public Owner5 addCar5(Car5 car5) {
        this.car5S.add(car5);
        car5.getOwner5S().add(this);
        return this;
    }

    public Owner5 removeCar5(Car5 car5) {
        this.car5S.remove(car5);
        car5.getOwner5S().remove(this);
        return this;
    }

    public void setCar5S(Set<Car5> car5S) {
        this.car5S = car5S;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Owner5 owner5 = (Owner5) o;
        if (owner5.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), owner5.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Owner5{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
