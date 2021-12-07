package com.epam.jwd.dao.model.user;

import com.epam.jwd.dao.model.Entity;
import java.util.Objects;

public class User extends Entity<Integer> {
    private String login;
    private String password;
    private Integer accountId;

    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(String login, String password, Integer accountId) {
        this.login = login;
        this.password = password;
        this.accountId = accountId;
    }

    public User(Integer id, String login, String password, Integer accountId) {
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
        User user = (User) o;
        return login.equals(user.login) && password.equals(user.password) && accountId.equals(user.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, accountId);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", accountId=" + accountId +
                '}';
    }
}
