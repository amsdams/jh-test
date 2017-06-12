package com.mycompany.myapp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Owner4.
 */
@Entity
@Table(name = "owner_4")
public class Owner4 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(unique = true)
    private Car4 car4;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Owner4 name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Car4 getCar4() {
        return car4;
    }

    public Owner4 car4(Car4 car4) {
        this.car4 = car4;
        return this;
    }

    public void setCar4(Car4 car4) {
        this.car4 = car4;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Owner4 owner4 = (Owner4) o;
        if (owner4.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), owner4.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Owner4{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
