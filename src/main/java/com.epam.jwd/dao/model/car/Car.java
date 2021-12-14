package com.epam.jwd.dao.model.car;

import com.epam.jwd.dao.model.Entity;

import java.util.Objects;

public class Car extends Entity<Integer> {
    private String brand;
    private String model;
    private Integer year;
    private Byte level;
    private Byte body;
    private Integer engineVolume;
    private Byte transmission;
    private Byte doors;
    private String color;
    private Byte available;
    private Integer priceId;

    private Car() {
    }

    public void setPriceId(Integer priceId) {
        this.priceId = priceId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public Integer getYear() {
        return year;
    }

    public Byte getLevel() {
        return level;
    }

    public Byte getBody() {
        return body;
    }

    public Integer getEngineVolume() {
        return engineVolume;
    }

    public Byte getTransmission() {
        return transmission;
    }

    public Byte getDoors() {
        return doors;
    }

    public String getColor() {
        return color;
    }

    public Byte getAvailable() {
        return available;
    }

    public void setAvailable(Byte available) {
        this.available = available;
    }

    public Integer getPriceId() {
        return priceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return brand.equals(car.brand) && model.equals(car.model) && year.equals(car.year) && level.equals(car.level) && body.equals(car.body) && engineVolume.equals(car.engineVolume) && transmission.equals(car.transmission) && doors.equals(car.doors) && color.equals(car.color) && available.equals(car.available) && priceId.equals(car.priceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, model, year, level, body, engineVolume, transmission, doors, color, available, priceId);
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", level=" + level +
                ", body=" + body +
                ", engineVolume=" + engineVolume +
                ", transmission=" + transmission +
                ", doors=" + doors +
                ", color='" + color + '\'' +
                ", available=" + available +
                ", priceId=" + priceId +
                '}';
    }

    public static class Builder {
        private Integer id;
        private String brand;
        private String model;
        private Integer year;
        private Byte level;
        private Byte body;
        private Integer engineVolume;
        private Byte transmission;
        private Byte doors;
        private String color;
        private Byte available;
        private Integer priceId;

        public Builder() {
        }

        public Builder withId(Integer id){
            this.id = id;
            return this;
        }

        public Builder withBrand(String brand){
            this.brand = brand;
            return this;
        }
        public Builder withModel(String model){
            this.model = model;
            return this;
        }

        public Builder withYear(Integer year){
            this.year = year;
            return this;
        }

        public Builder withLevel(Byte level){
            this.level = level;
            return this;
        }

        public Builder withBody(Byte body){
            this.body = body;
            return this;
        }

        public Builder withEngineVolume(Integer engineVolume){
            this.engineVolume = engineVolume;
            return this;
        }

        public Builder withTransmission(Byte transmission){
            this.transmission = transmission;
            return this;
        }

        public Builder withDoors(Byte doors){
            this.doors = doors;
            return this;
        }

        public Builder withColor(String color){
            this.color = color;
            return this;
        }

        public Builder withAvailable(Byte available){
            this.available = available;
            return this;
        }

        public Builder withPriceId(Integer priceId){
            this.priceId = priceId;
            return this;
        }

        public Car build(){
            Car car = new Car();
            car.id = this.id;
            car.brand = this.brand;
            car.model = this.model;
            car.year = this.year;
            car.level = this.level;
            car.body = this.body;
            car.engineVolume = this.engineVolume;
            car.transmission = this.transmission;
            car.doors = this.doors;
            car.color = this.color;
            car.available = this.available;
            car.priceId = this.priceId;
            return car;
        }
    }
}
