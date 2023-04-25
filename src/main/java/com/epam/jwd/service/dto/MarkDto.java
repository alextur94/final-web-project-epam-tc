package com.epam.jwd.service.dto;

import java.util.Objects;

public class MarkDto extends AbstractDto<Integer>{
    private String description;

    public MarkDto() {
    }

    public MarkDto(String description) {
        this.description = description;
    }

    public MarkDto(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarkDto markDto = (MarkDto) o;
        return description.equals(markDto.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }

    @Override
    public String toString() {
        return "MarkDto{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
