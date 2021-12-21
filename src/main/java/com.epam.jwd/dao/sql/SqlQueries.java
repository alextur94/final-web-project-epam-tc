package com.epam.jwd.dao.sql;

public interface SqlQueries {
    //Account sql
    String SQL_SAVE_ACCOUNT = "INSERT INTO account (role_id, name, surname, email, phone, document_id, address, drive_license_number, balance, status) VALUES (?,?,?,?,?,?,?,?,?,?)";
    String SQL_UPDATE_ACCOUNT_BY_ID = "UPDATE account SET role_id = ?, name = ?, surname = ?, email = ?, phone = ?, document_id = ?, address = ?, drive_license_number = ?, balance = ?, status = ? WHERE id = ?";
    String SQL_DELETE_ACCOUNT_BY_ID = "DELETE FROM account WHERE id = ?";
    String SQL_FIND_ALL_ACCOUNTS = "SELECT id, role_id, name, surname, email, phone, document_id, address, drive_license_number, balance, status FROM account";
    String SQL_FIND_ACCOUNT_BY_ID = "SELECT id, role_id, name, surname, email, phone, document_id, address, drive_license_number, balance, status FROM account WHERE id = ?";
    String SQL_FIND_ACCOUNT_BY_EMAIL = "SELECT id, role_id, name, surname, email, phone, document_id, address, drive_license_number, balance, status FROM account WHERE email = ?";
    String SQL_FIND_ACCOUNT_BY_PHONE = "SELECT id, role_id, name, surname, email, phone, document_id, address, drive_license_number, balance, status FROM account WHERE phone = ?";

    String SQL_SAVE_USER = "INSERT INTO user (login, password, account_id) VALUES (?,?,?)";
    String SQL_UPDATE_USER_BY_ID = "UPDATE user SET login = ?, password = ?, account_id = ? WHERE id = ?";
    String SQL_DELETE_USER_BY_ID = "DELETE FROM user WHERE id = ?";
    String SQL_FIND_USER_BY_ID = "SELECT id, login, password, account_id FROM user WHERE id = ?";
    String SQL_FIND_ALL_USERS = "SELECT id, login, password, account_id FROM user";
    String SQL_FIND_USER_BY_LOGIN = "SELECT id, login, password, account_id FROM user WHERE login = ?";

    String SQL_SAVE_CAR = "INSERT INTO car (brand, model, year, level, body, engine_volume, transmission, doors, color, available, price_id) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
    String SQL_UPDATE_CAR_BY_ID = "UPDATE car SET brand = ?, model = ?, year = ?, level = ?, body = ?, engine_volume = ?, transmission = ?, doors = ?, color = ?, available = ?, price_id = ? WHERE id = ?";
    String SQL_DELETE_CAR_BY_ID = "DELETE FROM car WHERE id = ?";
    String SQL_FIND_ALL_CARS = "SELECT id, brand, model, year, level, body, engine_volume, transmission, doors, color, available, price_id FROM car";
    String SQL_FIND_CAR_BY_ID = "SELECT id, brand, model, year, level, body, engine_volume, transmission, doors, color, available, price_id FROM car WHERE id = ?";

    String SQL_SAVE_INSURANCE = "INSERT INTO insurance (type, number, company, amount) VALUES (?,?,?,?)";
    String SQL_UPDATE_INSURANCE_BY_ID = "UPDATE insurance SET type = ?, number = ?, company = ?, amount = ? WHERE id = ?";
    String SQL_DELETE_INSURANCE_BY_ID = "DELETE FROM insurance WHERE id = ?";
    String SQL_FIND_INSURANCE_BY_ID = "SELECT id, type, number, company, amount FROM insurance WHERE id = ?";

    String SQL_SAVE_MARK = "INSERT INTO mark (description) VALUES (?)";
    String SQL_UPDATE_MARK_BY_ID = "UPDATE mark SET description = ? WHERE id = ?";
    String SQL_DELETE_MARK_BY_ID = "DELETE FROM mark WHERE id = ?";
    String SQL_FIND_MARK_BY_ID = "SELECT id, description  FROM mark WHERE id = ?";

    String SQL_SAVE_ORDER = "INSERT INTO order_tab (status, rental_time, payment_status, pledge, current_sum, user_id, car_id, mark_id, insurance_id) VALUES (?,?,?,?,?,?,?,?,?)";
    String SQL_UPDATE_ORDER_BY_ID = "UPDATE order_tab SET status = ?, rental_time = ?, payment_status = ?, rent_start_dtm = ?, rent_end_dtm = ?, start_level = ?, end_level = ?, refusal = ?, pledge = ?, current_sum = ?, status_mark = ?, user_id = ?, car_id = ?, mark_id = ?, insurance_id = ? WHERE id = ?";
    String SQL_DELETE_ORDER_BY_ID = "DELETE FROM order_tab WHERE id = ?";
    String SQL_FIND_COUNT_ORDERS = "SELECT id, status, rental_time, payment_status, rent_start_dtm, rent_end_dtm, start_level, end_level, refusal, pledge, current_sum, status_mark, user_id, car_id, mark_id, insurance_id FROM order_tab ORDER BY id DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    String SQL_FIND_ALL_ORDERS = "SELECT id, status, rental_time, payment_status, rent_start_dtm, rent_end_dtm, start_level, end_level, refusal, pledge, current_sum, status_mark, user_id, car_id, mark_id, insurance_id FROM order_tab";
    String SQL_FIND_ORDER_BY_ID = "SELECT id, status, rental_time, payment_status, rent_start_dtm, rent_end_dtm, start_level, end_level, refusal, pledge, current_sum, status_mark, user_id, car_id, mark_id, insurance_id FROM order_tab WHERE id = ?";
    String SQL_FIND_ORDER_BY_USER_ID = "SELECT id, status, rental_time, payment_status, rent_start_dtm, rent_end_dtm, start_level, end_level, refusal, pledge, current_sum, status_mark, user_id, car_id, mark_id, insurance_id FROM order_tab WHERE user_id = ? ORDER BY id DESC LIMIT ?, ?";
//    String SQL_FIND_ORDER_BY_USER_ID = "SELECT id, status, rental_time, payment_status, rent_start_dtm, rent_end_dtm, start_level, end_level, refusal, pledge, current_sum, status_mark, user_id, car_id, mark_id, insurance_id FROM order_tab WHERE user_id = ? ORDER BY id DESC";
    String SQL_FIND_ORDER_BY_STATUS = "SELECT id, status, rental_time, payment_status, rent_start_dtm, rent_end_dtm, start_level, end_level, refusal, pledge, current_sum, status_mark, user_id, car_id, mark_id, insurance_id FROM order_tab WHERE status = ? ORDER BY id DESC";
    String SQL_ONLY_ONE = "SELECT COUNT(*) FROM order_tab WHERE status !=6 AND user_id = ?";
    String SQL_COUNT_ORDERS = "SELECT COUNT(*) FROM order_tab WHERE user_id = ?";

    String SQL_SAVE_PRICE = "INSERT INTO price (price_per_day, price_per_hour) VALUES (?,?)";
    String SQL_UPDATE_PRICE_BY_ID = "UPDATE price SET price_per_day = ?, price_per_hour = ? WHERE id = ?";
    String SQL_DELETE_PRICE_BY_ID = "DELETE FROM price WHERE id = ?";
    String SQL_FIND_ALL_PRICES = "SELECT id, price_per_day, price_per_hour FROM price";
    String SQL_FIND_PRICE_BY_ID = "SELECT id, price_per_day, price_per_hour FROM price WHERE id = ?";

}
