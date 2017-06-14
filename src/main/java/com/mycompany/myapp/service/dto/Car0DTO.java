package com.mycompany.myapp.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Car0 entity.
 */
public class Car0DTO implements Serializable {

    private Long id;

    private String name;

    @Size(max = 2000)
    private String description;

    private Long owner0Id;

    private String owner0Name;

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

    public Long getOwner0Id() {
        return owner0Id;
    }

    public void setOwner0Id(Long owner0Id) {
        this.owner0Id = owner0Id;
    }

    public String getOwner0Name() {
        return owner0Name;
    }

    public void setOwner0Name(String owner0Name) {
        this.owner0Name = owner0Name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Car0DTO car0DTO = (Car0DTO) o;
        if(car0DTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), car0DTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Car0DTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
