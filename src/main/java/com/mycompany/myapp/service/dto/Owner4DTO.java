package com.mycompany.myapp.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Owner4 entity.
 */
public class Owner4DTO implements Serializable {

    private Long id;

    private String name;

    @Size(max = 2000)
    private String description;

    private Long car4Id;

    private String car4Name;

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

    public Long getCar4Id() {
        return car4Id;
    }

    public void setCar4Id(Long car4Id) {
        this.car4Id = car4Id;
    }

    public String getCar4Name() {
        return car4Name;
    }

    public void setCar4Name(String car4Name) {
        this.car4Name = car4Name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Owner4DTO owner4DTO = (Owner4DTO) o;
        if(owner4DTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), owner4DTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Owner4DTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
