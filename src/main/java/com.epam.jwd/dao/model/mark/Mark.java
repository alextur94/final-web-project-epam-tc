package com.epam.jwd.dao.model.mark;

import com.epam.jwd.dao.model.Entity;

import java.util.Objects;

public class Mark extends Entity<Integer> {
    private String description;

    public Mark() {
    }

    public Mark(String description) {
        this.description = description;
    }

    public Mark(Integer id, String description) {
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
        Mark mark = (Mark) o;
        return description.equals(mark.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }

    @Override
    public String toString() {
        return "Mark{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
