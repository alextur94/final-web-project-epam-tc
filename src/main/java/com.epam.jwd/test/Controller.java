package com.epam.jwd.test;

import com.epam.jwd.dao.impl.InsuranceDaoImpl;
import com.epam.jwd.dao.impl.MarkDaoImpl;
import com.epam.jwd.dao.model.insurance.Insurance;
import com.epam.jwd.dao.model.mark.Mark;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.AccountDto;
import com.epam.jwd.service.dto.CarDto;
import com.epam.jwd.service.dto.PriceDto;
import com.epam.jwd.service.dto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.AccountServiceImpl;
import com.epam.jwd.service.impl.CarServiceImpl;
import com.epam.jwd.service.impl.PriceServiceImpl;
import com.epam.jwd.service.impl.UserServiceImpl;

import java.sql.SQLException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Controller {
    static UserServiceImpl userService = new UserServiceImpl();
    static PriceServiceImpl priceService = new PriceServiceImpl();
    static CarServiceImpl carService = new CarServiceImpl();

    public static void main(String[] args) throws ServiceException, SQLException {
//        update Person
//        userService.updatePerson(updateUser(14),updateAccount(14));
//        priceService.save(new PriceDto(123.00));
//        carService.saveCar(createCar(), new PriceDto(999.00));

//        orderDao.update(new Order.Builder()
//                .withStatus((byte)1)
//                .withRentalTime(1236)
//                .withPaymentStatus(false)
//                .withRentStartDtm(new Date().getTime())
//                .withRentEndDtm(new Date().getTime())
//                .withStartLevel(80)
//                .withEndLevel(20)
//                .withRefusal("asdasdasd")
//                .withPledge(123.00)
//                .withCurrentSum(100.00)
//                .withRealSum(80.00)
//                .withStatusMark(false)
//                .withUserId(1)
//                .withCarId(1)
//                .withMarkId(1)
//                .withInsuranceId(1)
//                .withId(1)
//                .build()
//        );


//        insuranceDao.update(new Insurance(1, "NUM123","COM123",0.00));
//        carDao.update(new Car.Builder()
//                .withBrand("Bently")
//                .withModel("Camry")
//                .withYear(2016)
//                .withLevel((byte) 3)
//                .withBody((byte) 5)
//                .withEngineVolume(12)
//                .withTransmission(true)
//                .withDoors((byte) 3)
//                .withColor("Red")
//                .withAvailable(true)
//                .withPriceId(3)
//                .withId(2)
//                .build()
//        );

    }

    private static UserDto updateUser(Integer id) throws ServiceException {
        UserDto userDto = userService.getById(id);
        System.out.println(userDto);
        userDto.setLogin("lex");
        userDto.setPassword("92341865Alex");
        return userDto;
    }

    private static AccountDto updateAccount(Integer id){
        AccountDto accountDto = new AccountDto.Builder()
                .withId(14)
                .withSurname("name")
                .withName("alex")
                .build();
        return accountDto;
    }



    private static void newPerson() throws ServiceException {
        UserDto userDto = new UserDto();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entry login: ");
        userDto.setLogin(scanner.nextLine());
        System.out.print("Entry pass: ");
        userDto.setPassword(scanner.nextLine());
        System.out.print("Entry email: ");
        AccountDto accountDto = new AccountDto.Builder().withEmail(scanner.nextLine()).build();
        userService.savePerson(userDto, accountDto);
    }

    private static CarDto createCar() {
        CarDto car = new CarDto.Builder()
                .withBrand("Volkswagen")
                .withModel("Polo")
                .withYear(2020)
                .withLevel((byte)1)
                .withBody((byte)1)
                .withEngineVolume(100)
                .withTransmission(true)
                .withDoors((byte)3)
                .withColor("Red")
                .withAvailable(true)
                .build();
        return car;
    }

    private static Mark createMark() {
        MarkDaoImpl markDao = new MarkDaoImpl();
        Mark mark = new Mark();
        mark.setDescription("Test");
        markDao.save(mark);
        return mark;
    }

    private static Insurance createInsurance() {
        InsuranceDaoImpl insuranceDao = new InsuranceDaoImpl();
        Insurance insurance = new Insurance();
        insurance.setType((byte)1);
        insurance.setNumber("qweqwe");
        insurance.setCompany("asdasd");
        insurance.setAmount(12.00);
        insuranceDao.save(insurance);
        return insurance;
    }

    public static void createPrice() throws ServiceException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException, SQLException {
        Service<PriceDto, Integer> service = new PriceServiceImpl();
        PriceDto priceDto = new PriceDto();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Price of day : ");
        priceDto.setPricePerDay(scanner.nextDouble());
        System.out.print("Price if hour : ");
        priceDto.setPricePerHour(scanner.nextDouble());
        service.save(priceDto);
    }

    public static Integer accountCreate() throws ServiceException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException, SQLException {
        Scanner scanner = new Scanner(System.in);
        Service<AccountDto, Integer> service = new AccountServiceImpl();
        System.out.print("Email : ");
        String email = scanner.next();
        AccountDto accountDto = new AccountDto.Builder().withEmail(email).build();
        return service.save(accountDto).getId();
    }
}

