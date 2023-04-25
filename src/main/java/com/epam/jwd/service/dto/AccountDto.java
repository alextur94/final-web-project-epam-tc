package com.epam.jwd.service.dto;
import com.epam.jwd.dao.model.account.Role;

import java.util.Objects;

public class AccountDto extends AbstractDto<Integer>{
    private Role role;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String documentId;
    private String address;
    private String driveLicenseNumber;
    private Double balance;
    private Integer status;

    private AccountDto() {
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDriveLicenseNumber() {
        return driveLicenseNumber;
    }

    public void setDriveLicenseNumber(String driveLicenseNumber) {
        this.driveLicenseNumber = driveLicenseNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDto that = (AccountDto) o;
        return role.equals(that.role) && name.equals(that.name) && surname.equals(that.surname) && email.equals(that.email) && phone.equals(that.phone) && documentId.equals(that.documentId) && address.equals(that.address) && driveLicenseNumber.equals(that.driveLicenseNumber) && balance.equals(that.balance) && status.equals(that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, name, surname, email, phone, documentId, address, driveLicenseNumber, balance, status);
    }

    @Override
    public String toString() {
        return "AccountDto{" +
                "id=" + id +
                ", roleId=" + role +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", documentId='" + documentId + '\'' +
                ", address='" + address + '\'' +
                ", driveLicenseNumber='" + driveLicenseNumber + '\'' +
                ", balance=" + balance +
                ", status=" + status +
                '}';
    }

    public static class Builder {
        private Integer id;
        private Role role = Role.USER;
        private String name;
        private String surname;
        private String email;
        private String phone;
        private String documentId;
        private String address;
        private String driveLicenseNumber;
        private Double balance = 0.00;
        private Integer status = 0;

        public Builder() {
        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withRole(Role role) {
            this.role = role;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder withDocumentId(String documentId) {
            this.documentId = documentId;
            return this;
        }

        public Builder withAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder withDriveLicenseNumber(String driveLicenseNumber) {
            this.driveLicenseNumber = driveLicenseNumber;
            return this;
        }

        public Builder withBalance(Double balance) {
            this.balance = balance;
            return this;
        }

        public Builder withStatus(Integer status) {
            this.status = status;
            return this;
        }

        public AccountDto build() {
            AccountDto account = new AccountDto();
            account.id = this.id;
            account.role = this.role;
            account.name = this.name;
            account.surname = this.surname;
            account.email = this.email;
            account.phone = this.phone;
            account.documentId = this.documentId;
            account.address = this.address;
            account.driveLicenseNumber = this.driveLicenseNumber;
            account.balance = this.balance;
            account.status = this.status;
            return account;
        }
    }
}
