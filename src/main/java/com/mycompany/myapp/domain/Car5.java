package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * A Car5.
 */
@Entity
@Table(name = "car_5")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "car5")
public class Car5 implements Serializable {

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

    @ManyToMany(mappedBy = "car5S")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Owner5> owner5S = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Car5 name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Car5 description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Owner5> getOwner5S() {
        return owner5S;
    }

    public Car5 owner5S(Set<Owner5> owner5S) {
        this.owner5S = owner5S;
        return this;
    }

    public Car5 addOwner5(Owner5 owner5) {
        this.owner5S.add(owner5);
        owner5.getCar5S().add(this);
        return this;
    }

    public Car5 removeOwner5(Owner5 owner5) {
        this.owner5S.remove(owner5);
        owner5.getCar5S().remove(this);
        return this;
    }

    public void setOwner5S(Set<Owner5> owner5S) {
        this.owner5S = owner5S;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car5 car5 = (Car5) o;
        if (car5.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), car5.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Car5{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
