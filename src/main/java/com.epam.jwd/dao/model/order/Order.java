package com.epam.jwd.dao.model.order;

import com.epam.jwd.dao.model.Entity;

import java.util.Objects;

public class Order extends Entity<Integer> {
    private Byte status;
    private Integer rentalTime;
    private Byte paymentStatus;
    private Long rentStartDtm;
    private Long rentEndDtm;
    private Integer startLevel;
    private Integer endLevel;
    private String refusal;
    private Double pledge;
    private Double currentSum;
    private Byte statusMark;
    private Integer userId;
    private Integer carId;
    private Integer markId;
    private Integer insuranceId;

    private Order() {
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getRentalTime() {
        return rentalTime;
    }

    public void setRentalTime(Integer rentalTime) {
        this.rentalTime = rentalTime;
    }

    public Byte getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Byte paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Long getRentStartDtm() {
        return rentStartDtm;
    }

    public void setRentStartDtm(Long rentStartDtm) {
        this.rentStartDtm = rentStartDtm;
    }

    public Long getRentEndDtm() {
        return rentEndDtm;
    }

    public void setRentEndDtm(Long rentEndDtm) {
        this.rentEndDtm = rentEndDtm;
    }

    public Integer getStartLevel() {
        return startLevel;
    }

    public void setStartLevel(Integer startLevel) {
        this.startLevel = startLevel;
    }

    public Integer getEndLevel() {
        return endLevel;
    }

    public void setEndLevel(Integer endLevel) {
        this.endLevel = endLevel;
    }

    public String getRefusal() {
        return refusal;
    }

    public void setRefusal(String refusal) {
        this.refusal = refusal;
    }

    public Double getPledge() {
        return pledge;
    }

    public void setPledge(Double pledge) {
        this.pledge = pledge;
    }

    public Double getCurrentSum() {
        return currentSum;
    }

    public void setCurrentSum(Double currentSum) {
        this.currentSum = currentSum;
    }

    public Byte getStatusMark() {
        return statusMark;
    }

    public void setStatusMark(Byte statusMark) {
        this.statusMark = statusMark;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Integer getMarkId() {
        return markId;
    }

    public void setMarkId(Integer markId) {
        this.markId = markId;
    }

    public Integer getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(Integer insuranceId) {
        this.insuranceId = insuranceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return status.equals(order.status) && rentalTime.equals(order.rentalTime) && paymentStatus.equals(order.paymentStatus) && rentStartDtm.equals(order.rentStartDtm) && rentEndDtm.equals(order.rentEndDtm) && startLevel.equals(order.startLevel) && endLevel.equals(order.endLevel) && refusal.equals(order.refusal) && pledge.equals(order.pledge) && currentSum.equals(order.currentSum) && statusMark.equals(order.statusMark) && userId.equals(order.userId) && carId.equals(order.carId) && markId.equals(order.markId) && insuranceId.equals(order.insuranceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, rentalTime, paymentStatus, rentStartDtm, rentEndDtm, startLevel, endLevel, refusal, pledge, currentSum, statusMark, userId, carId, markId, insuranceId);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", rentalTime=" + rentalTime +
                ", paymentStatus=" + paymentStatus +
                ", rentStartDtm=" + rentStartDtm +
                ", rentEndDtm=" + rentEndDtm +
                ", startLevel=" + startLevel +
                ", endLevel=" + endLevel +
                ", refusal='" + refusal + '\'' +
                ", pledge=" + pledge +
                ", currentSum=" + currentSum +
                ", statusMark=" + statusMark +
                ", accountId=" + userId +
                ", carId=" + carId +
                ", markId=" + markId +
                ", insuranceId=" + insuranceId +
                '}';
    }

    public static class Builder {
        private Integer id;
        private Byte status;
        private Integer rentalTime;
        private Byte paymentStatus;
        private Long rentStartDtm;
        private Long rentEndDtm;
        private Integer startLevel;
        private Integer endLevel;
        private String refusal;
        private Double pledge;
        private Double currentSum;
        private Byte statusMark;
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

        public Builder withRentalTime(Integer rentalTime){
            this.rentalTime = rentalTime;
            return this;
        }

        public Builder withPaymentStatus(Byte paymentStatus){
            this.paymentStatus = paymentStatus;
            return this;
        }

        public Builder withRentStartDtm(Long rentStartDtm){
            this.rentStartDtm = rentStartDtm;
            return this;
        }

        public Builder withRentEndDtm(Long rentEndDtm){
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

        public Order build(){
            Order order = new Order();
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
            order.statusMark = this.statusMark;
            order.userId = this.userId;
            order.carId = this.carId;
            order.markId = this.markId;
            order.insuranceId = this.insuranceId;
            return order;
        }
    }
}
