package com.mycompany.myapp.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Car3 entity.
 */
public class Car3DTO implements Serializable {

    private Long id;

    private String name;

    @Size(max = 2000)
    private String description;

    private Set<Owner3DTO> owner3S = new HashSet<>();

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

    public Set<Owner3DTO> getOwner3S() {
        return owner3S;
    }

    public void setOwner3S(Set<Owner3DTO> owner3S) {
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

        Car3DTO car3DTO = (Car3DTO) o;
        if(car3DTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), car3DTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Car3DTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
