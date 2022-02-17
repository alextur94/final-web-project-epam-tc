package com.epam.jwd.dao.api;

public interface Message {
    String UPDATE_ACCOUNT_ERROR = "Account wasn't updated in db. ";
    String FIND_ALL_ACCOUNTS_ERROR = "Find all accounts ";
    String FIND_BY_ID_ACCOUNT_ERROR = "Account wasn't found by id. ";
    String FIND_BY_EMAIL_ERROR = "Account wasn't found by email. ";
    String FIND_BY_LOGIN_FOR_UPDATE_ERROR = "This login is already taken. ";
    String FIND_BY_EMAIL_FOR_UPDATE_ERROR = "This email is already taken. ";
    String TRANSACTION_ACCOUNT_ADMIN_ERROR = "Money transfer error. Account - Admin ";
    String CANCEL_ORDER_ERROR = "Unable to cancel order ";

    String SAVE_CAR_ERROR = "New car wasn't saved in db. ";
    String UPDATE_CAR_ERROR = "Car wasn't updated in db. ";
    String DELETE_CAR_ERROR = "Car wasn't deleted in db. ";
    String FIND_ALL_CARS_ERROR = "Find all accounts ";
    String FIND_BY_ID_CAR_ERROR = "Car wasn't found by id. ";
    String FIND_BY_RANGE_ERROR = "Car wasn't found by range. ";
    String SAVE_CAR_AND_PRICE_ERROR = "Failed to add new machine. ";

    String SAVE_INSURANCE_ERROR = "New insurance wasn't saved in db. ";
    String UPDATE_INSURANCE_ERROR = "Insurance wasn't updated in db. ";
    String DELETE_INSURANCE_ERROR = "Insurance wasn't deleted in db. ";
    String FIND_BY_ID_INSURANCE_ERROR = "Insurance wasn't found by id. ";

    String SAVE_MARK_ERROR = "New mark wasn't saved in db. ";
    String UPDATE_MARK_ERROR = "Mark wasn't updated in db. ";
    String DELETE_MARK_ERROR = "Mark wasn't deleted in db. ";
    String FIND_ALL_MARKS_ERROR = "Failed to find marks. ";

    String SAVE_ORDER_ERROR = "New order wasn't saved in db. ";
    String SAVE_ORDER_MARK_INSURANCE_ERROR = "New order, mark, insurance wasn't saved in db. ";
    String UPDATE_ORDER_ERROR = "Order wasn't updated in db. ";
    String DELETE_ORDER_ERROR = "Order wasn't deleted in db. ";
    String FIND_ALL_ORDERS_ERROR = "Failed to find orders. ";
    String FIND_COUNT_ORDERS_ERROR = "Failed to find count orders. ";
    String FIND_BY_ID_ORDER_ERROR = "Order wasn't found by id. ";
    String FIND_USER_BY_ID_ERROR = "Order wasn't found by user id. ";
    String ONLY_ONE_ACTIVE_ORDER_ERROR = "User can only have one active order. ";
    String CANCEL_ORDER_ADMIN_ERROR = "Failed to cancel the order by the administrator. ";
    String CANCEL_SAVE_FINISH_ORDER_ERROR = "Car rental completion error. ";

    String SAVE_PRICE_ERROR = "New price wasn't saved in db. ";
    String UPDATE_PRICE_ERROR = "Price wasn't updated in db. ";
    String DELETE_PRICE_ERROR = "Price wasn't deleted in db. ";
    String FIND_ALL_PRICE_ERROR = "Failed to find prices. ";
    String FIND_BY_ID_PRICE_ERROR = "Price wasn't found by id. ";

    String SAVE_USER_ERROR = "New user wasn't saved in db. ";
    String SAVE_PERSON_ERROR = "New person wasn't saved in db. ";
    String UPDATE_USER_ERROR = "User wasn't updated in db. ";
    String UPDATE_PERSON_ERROR = "Person wasn't updated in db. ";
    String DELETE_USER_ERROR = "User wasn't deleted in db. ";
    String FIND_ALL_USERS_ERROR = "Find all users ";
    String FIND_BY_ID_USER_ERROR = "User wasn't found by id. ";
    String FIND_BY_LOGIN_ERROR = "User wasn't found by login. ";
    String FIND_BY_ACCOUNT_ID_ERROR = "User wasn't found by account id. ";
    String INCORRECT_LOGIN_ERROR = "Incorrect login or password. ";
    String ENCRYPT_PASSWORD_ERROR = "Password encryption error. ";
}
