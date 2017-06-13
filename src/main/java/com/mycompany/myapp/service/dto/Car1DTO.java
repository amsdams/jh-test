package com.mycompany.myapp.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Car1 entity.
 */
public class Car1DTO implements Serializable {

    private Long id;

    private String name;

    @Size(max = 2000)
    private String description;

    private Long owner1Id;

    private String owner1Name;

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

    public Long getOwner1Id() {
        return owner1Id;
    }

    public void setOwner1Id(Long owner1Id) {
        this.owner1Id = owner1Id;
    }

    public String getOwner1Name() {
        return owner1Name;
    }

    public void setOwner1Name(String owner1Name) {
        this.owner1Name = owner1Name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Car1DTO car1DTO = (Car1DTO) o;
        if(car1DTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), car1DTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Car1DTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
