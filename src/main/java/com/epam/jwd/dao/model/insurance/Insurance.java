package com.epam.jwd.dao.model.insurance;

import com.epam.jwd.dao.model.Entity;

import java.util.Objects;

public class Insurance extends Entity<Integer> {
    private Byte type;
    private String number;
    private String company;
    private Double amount;

    public Insurance() {
    }

    public Insurance(Byte type, String number, String company, Double amount) {
        this.type = type;
        this.number = number;
        this.company = company;
        this.amount = amount;
    }

    public Insurance(Integer id, Byte type, String number, String company, Double amount) {
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
        Insurance insurance = (Insurance) o;
        return type.equals(insurance.type) && number.equals(insurance.number) && company.equals(insurance.company) && amount.equals(insurance.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, number, company, amount);
    }

    @Override
    public String toString() {
        return "Insurance{" +
                "id=" + id +
                ", type=" + type +
                ", number='" + number + '\'' +
                ", company='" + company + '\'' +
                ", amount=" + amount +
                '}';
    }
}
