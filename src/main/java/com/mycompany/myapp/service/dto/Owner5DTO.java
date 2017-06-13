package com.mycompany.myapp.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Owner5 entity.
 */
public class Owner5DTO implements Serializable {

    private Long id;

    private String name;

    @Size(max = 2000)
    private String description;

    private Set<Car5DTO> car5S = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Car5DTO> getCar5S() {
        return car5S;
    }

    public void setCar5S(Set<Car5DTO> car5S) {
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

        Owner5DTO owner5DTO = (Owner5DTO) o;
        if(owner5DTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), owner5DTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Owner5DTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
