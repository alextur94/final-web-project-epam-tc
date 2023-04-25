package com.epam.jwd.service.dto;

import java.util.Objects;

public class UserDto extends AbstractDto<Integer>{
    private String login;
    private String password;
    private Integer accountId;

    public UserDto() {
    }

    public UserDto(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public UserDto(String login, String password, Integer accountId) {
        this.login = login;
        this.password = password;
        this.accountId = accountId;
    }

    public UserDto(Integer id, String login, String password, Integer accountId) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.accountId = accountId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return login.equals(userDto.login) && password.equals(userDto.password) && accountId.equals(userDto.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, accountId);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", accountId=" + accountId +
                '}';
    }
}
