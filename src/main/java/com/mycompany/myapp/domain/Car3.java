package com.mycompany.myapp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Car3.
 */
@Entity
@Table(name = "car_3")
public class Car3 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(name = "car3_owner3",
               joinColumns = @JoinColumn(name="car3s_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="owner3s_id", referencedColumnName="id"))
    private Set<Owner3> owner3S = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Car3 name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Owner3> getOwner3S() {
        return owner3S;
    }

    public Car3 owner3S(Set<Owner3> owner3S) {
        this.owner3S = owner3S;
        return this;
    }

    public Car3 addOwner3(Owner3 owner3) {
        this.owner3S.add(owner3);
        owner3.getCar3S().add(this);
        return this;
    }

    public Car3 removeOwner3(Owner3 owner3) {
        this.owner3S.remove(owner3);
        owner3.getCar3S().remove(this);
        return this;
    }

    public void setOwner3S(Set<Owner3> owner3S) {
        this.owner3S = owner3S;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car3 car3 = (Car3) o;
        if (car3.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), car3.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Car3{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
