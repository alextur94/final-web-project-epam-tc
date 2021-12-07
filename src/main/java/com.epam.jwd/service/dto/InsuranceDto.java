package com.epam.jwd.service.dto;

import java.util.Objects;

public class InsuranceDto extends AbstractDto<Integer>{
    private Byte type;
    private String number;
    private String company;
    private Double amount;

    public InsuranceDto() {
    }

    public InsuranceDto(Byte type, String number, String company, Double amount) {
        this.type = type;
        this.number = number;
        this.company = company;
        this.amount = amount;
    }

    public InsuranceDto(Integer id, Byte type, String number, String company, Double amount) {
        this.id = id;
        this.type = type;
        this.number = number;
        this.company = company;
        this.amount = amount;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InsuranceDto that = (InsuranceDto) o;
        return type.equals(that.type) && number.equals(that.number) && company.equals(that.company) && amount.equals(that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, number, company, amount);
    }

    @Override
    public String toString() {
        return "InsuranceDto{" +
                "id=" + id +
                ", type=" + type +
                ", number='" + number + '\'' +
                ", company='" + company + '\'' +
                ", amount=" + amount +
                '}';
    }
}
