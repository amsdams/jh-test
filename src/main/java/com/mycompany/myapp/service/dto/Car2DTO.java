package com.mycompany.myapp.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Car2 entity.
 */
public class Car2DTO implements Serializable {

    private Long id;

    private String name;

    @Size(max = 2000)
    private String description;

    private Long owner2Id;

    private Long driver2Id;

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

    public Long getOwner2Id() {
        return owner2Id;
    }

    public void setOwner2Id(Long owner2Id) {
        this.owner2Id = owner2Id;
    }

    public Long getDriver2Id() {
        return driver2Id;
    }

    public void setDriver2Id(Long owner2Id) {
        this.driver2Id = owner2Id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Car2DTO car2DTO = (Car2DTO) o;
        if(car2DTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), car2DTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Car2DTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
