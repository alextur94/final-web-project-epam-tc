package com.epam.jwd.service.dto;

import java.util.Objects;

public class PriceDto extends AbstractDto<Integer>{
    private Double pricePerDay;
    private Double pricePerHour;

    public PriceDto() {
    }

    public PriceDto(Double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public PriceDto(Integer id, Double pricePerDay, Double pricePerHour) {
        this.id = id;
        this.pricePerDay = pricePerDay;
        this.pricePerHour = pricePerHour;
    }

    public PriceDto(Double pricePerDay, Double pricePerHour) {
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
        PriceDto priceDto = (PriceDto) o;
        return id.equals(priceDto.id) && pricePerDay.equals(priceDto.pricePerDay) && pricePerHour.equals(priceDto.pricePerHour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pricePerDay, pricePerHour);
    }

    @Override
    public String toString() {
        return "PriceDto{" +
                "id=" + id +
                ", pricePerDay=" + pricePerDay +
                ", pricePerHour=" + pricePerHour +
                '}';
    }
}
