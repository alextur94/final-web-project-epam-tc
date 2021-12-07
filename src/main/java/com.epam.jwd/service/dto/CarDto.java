package com.epam.jwd.service.dto;

import java.util.Objects;

public class CarDto extends AbstractDto<Integer>{
    private String brand;
    private String model;
    private Integer year;
    private Byte level;
    private Byte body;
    private Integer engineVolume;
    private Boolean transmission;
    private Byte doors;
    private String color;
    private Boolean available;
    private Integer priceId;

    private CarDto() {
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

    public Boolean getTransmission() {
        return transmission;
    }

    public Byte getDoors() {
        return doors;
    }

    public String getColor() {
        return color;
    }

    public Boolean getAvailable() {
        return available;
    }

    public Integer getPriceId() {
        return priceId;
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
        private Boolean transmission;
        private Byte doors;
        private String color;
        private Boolean available;
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

        public Builder withTransmission(Boolean transmission){
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

        public Builder withAvailable(Boolean available){
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
