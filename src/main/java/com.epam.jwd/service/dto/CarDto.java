package com.epam.jwd.service.dto;

import java.util.Objects;

public class CarDto extends AbstractDto<Integer>{
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

    private CarDto() {
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Byte getLevel() {
        return level;
    }

    public void setLevel(Byte level) {
        this.level = level;
    }

    public Byte getBody() {
        return body;
    }

    public void setBody(Byte body) {
        this.body = body;
    }

    public Integer getEngineVolume() {
        return engineVolume;
    }

    public void setEngineVolume(Integer engineVolume) {
        this.engineVolume = engineVolume;
    }

    public Byte getTransmission() {
        return transmission;
    }

    public void setTransmission(Byte transmission) {
        this.transmission = transmission;
    }

    public Byte getDoors() {
        return doors;
    }

    public void setDoors(Byte doors) {
        this.doors = doors;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public void setPriceId(Integer priceId) {
        this.priceId = priceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarDto carDto = (CarDto) o;
        return brand.equals(carDto.brand) && model.equals(carDto.model) && year.equals(carDto.year) && level.equals(carDto.level) && body.equals(carDto.body) && engineVolume.equals(carDto.engineVolume) && transmission.equals(carDto.transmission) && doors.equals(carDto.doors) && color.equals(carDto.color) && available.equals(carDto.available) && priceId.equals(carDto.priceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, model, year, level, body, engineVolume, transmission, doors, color, available, priceId);
    }

    @Override
    public String toString() {
        return "CarDto{" +
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

    public static class Builder{
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

        public CarDto build(){
            CarDto car = new CarDto();
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
