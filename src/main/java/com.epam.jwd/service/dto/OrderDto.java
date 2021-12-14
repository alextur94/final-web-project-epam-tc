package com.epam.jwd.service.dto;

import java.util.Objects;

public class OrderDto extends AbstractDto<Integer>{
    private Byte status;
    private String rentalTime;
    private Byte paymentStatus;
    private String rentStartDtm;
    private String rentEndDtm;
    private Integer startLevel;
    private Integer endLevel;
    private String refusal;
    private Double pledge;
    private Double currentSum;
    private Double realSum;
    private Byte statusMark;
    private Integer userId;
    private Integer carId;
    private Integer markId;
    private Integer insuranceId;

    private OrderDto() {
    }

    public Byte getStatus() {
        return status;
    }

    public String getRentalTime() {
        return rentalTime;
    }

    public Byte getPaymentStatus() {
        return paymentStatus;
    }

    public String getRentStartDtm() {
        return rentStartDtm;
    }

    public String getRentEndDtm() {
        return rentEndDtm;
    }

    public Integer getStartLevel() {
        return startLevel;
    }

    public Integer getEndLevel() {
        return endLevel;
    }

    public String getRefusal() {
        return refusal;
    }

    public Double getPledge() {
        return pledge;
    }

    public Double getCurrentSum() {
        return currentSum;
    }

    public Double getRealSum() {
        return realSum;
    }

    public Byte getStatusMark() {
        return statusMark;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getCarId() {
        return carId;
    }

    public Integer getMarkId() {
        return markId;
    }

    public Integer getInsuranceId() {
        return insuranceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDto orderDto = (OrderDto) o;
        return status.equals(orderDto.status) && rentalTime.equals(orderDto.rentalTime) && paymentStatus.equals(orderDto.paymentStatus) && rentStartDtm.equals(orderDto.rentStartDtm) && rentEndDtm.equals(orderDto.rentEndDtm) && startLevel.equals(orderDto.startLevel) && endLevel.equals(orderDto.endLevel) && refusal.equals(orderDto.refusal) && pledge.equals(orderDto.pledge) && currentSum.equals(orderDto.currentSum) && realSum.equals(orderDto.realSum) && statusMark.equals(orderDto.statusMark) && userId.equals(orderDto.userId) && carId.equals(orderDto.carId) && markId.equals(orderDto.markId) && insuranceId.equals(orderDto.insuranceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, rentalTime, paymentStatus, rentStartDtm, rentEndDtm, startLevel, endLevel, refusal, pledge, currentSum, realSum, statusMark, userId, carId, markId, insuranceId);
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "id=" + id +
                ", status=" + status +
                ", rentalTime=" + rentalTime +
                ", paymentStatus=" + paymentStatus +
                ", rentStartDtm=" + rentStartDtm +
                ", rentEndDtm=" + rentEndDtm +
                ", startLevel=" + startLevel +
                ", endLevel=" + endLevel +
                ", refusal='" + refusal + '\'' +
                ", pledge=" + pledge +
                ", currentSum=" + currentSum +
                ", realSum=" + realSum +
                ", statusMark=" + statusMark +
                ", accountId=" + userId +
                ", carId=" + carId +
                ", markId=" + markId +
                ", insuranceId=" + insuranceId +
                '}';
    }

    public static class Builder{
        private Integer id;
        private Byte status = 1;
        private String rentalTime;
        private Byte paymentStatus = 0;
        private String rentStartDtm;
        private String rentEndDtm;
        private Integer startLevel;
        private Integer endLevel;
        private String refusal;
        private Double pledge;
        private Double currentSum;
        private Double realSum;
        private Byte statusMark = 0;
        private Integer userId;
        private Integer carId;
        private Integer markId;
        private Integer insuranceId;

        public Builder() {
        }

        public Builder withId(Integer id){
            this.id = id;
            return this;
        }

        public Builder withStatus(Byte status){
            this.status = status;
            return this;
        }

        public Builder withRentalTime(String rentalTime){
            this.rentalTime = rentalTime;
            return this;
        }

        public Builder withPaymentStatus(Byte paymentStatus){
            this.paymentStatus = paymentStatus;
            return this;
        }

        public Builder withRentStartDtm(String rentStartDtm){
            this.rentStartDtm = rentStartDtm;
            return this;
        }

        public Builder withRentEndDtm(String rentEndDtm){
            this.rentEndDtm = rentEndDtm;
            return this;
        }

        public Builder withStartLevel(Integer startLevel){
            this.startLevel = startLevel;
            return this;
        }

        public Builder withEndLevel(Integer endLevel){
            this.endLevel = endLevel;
            return this;
        }

        public Builder withRefusal(String refusal){
            this.refusal = refusal;
            return this;
        }

        public Builder withPledge(Double pledge){
            this.pledge = pledge;
            return this;
        }

        public Builder withCurrentSum(Double currentSum){
            this.currentSum = currentSum;
            return this;
        }

        public Builder withRealSum(Double realSum){
            this.realSum = realSum;
            return this;
        }

        public Builder withStatusMark(Byte statusMark){
            this.statusMark = statusMark;
            return this;
        }

        public Builder withUserId(Integer userId){
            this.userId = userId;
            return this;
        }

        public Builder withCarId(Integer carId){
            this.carId = carId;
            return this;
        }

        public Builder withMarkId(Integer markId){
            this.markId = markId;
            return this;
        }

        public Builder withInsuranceId(Integer insuranceId){
            this.insuranceId = insuranceId;
            return this;
        }

        public OrderDto build(){
            OrderDto order = new OrderDto();
            order.id = this.id;
            order.status = this.status;
            order.rentalTime = this.rentalTime;
            order.paymentStatus = this.paymentStatus;
            order.rentStartDtm = this.rentStartDtm;
            order.rentEndDtm = this.rentEndDtm;
            order.startLevel = this.startLevel;
            order.endLevel = this.endLevel;
            order.refusal = this.refusal;
            order.pledge = this.pledge;
            order.currentSum = this.currentSum;
            order.realSum = this.realSum;
            order.statusMark = this.statusMark;
            order.userId = this.userId;
            order.carId = this.carId;
            order.markId = this.markId;
            order.insuranceId = this.insuranceId;
            return order;
        }
    }
}
