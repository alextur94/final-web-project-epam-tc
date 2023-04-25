package com.epam.jwd.dao.model.price;

import com.epam.jwd.dao.model.Entity;
import java.util.Objects;

public class Price extends Entity<Integer> {
    private Double pricePerDay;
    private Double pricePerHour;

    public Price() {
    }

    public Price(Double pricePerDay, Double pricePerHour) {
        this.pricePerDay = pricePerDay;
        this.pricePerHour = pricePerHour;
    }

    public Price(Integer id, Double pricePerDay, Double pricePerHour) {
        this.id = id;
        this.pricePerDay = pricePerDay;
        this.pricePerHour = pricePerHour;
    }

    public Double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(Double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public Double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(Double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return pricePerDay.equals(price.pricePerDay) && pricePerHour.equals(price.pricePerHour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pricePerDay, pricePerHour);
    }

    @Override
    public String toString() {
        return "Price{" +
                "id=" + id +
                ", pricePerDay=" + pricePerDay +
                ", pricePerHour=" + pricePerHour +
                '}';
    }
}
