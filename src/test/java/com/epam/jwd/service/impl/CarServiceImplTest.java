package com.epam.jwd.service.impl;

import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.impl.CarDaoImpl;
import com.epam.jwd.service.converter.impl.CarConverter;
import com.epam.jwd.service.dto.CarDto;
import com.epam.jwd.service.exception.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CarServiceImplTest {
    @Mock
    CarDaoImpl carDao;
    @Mock
    CarConverter carConverter;

    @InjectMocks
    CarServiceImpl carService;

//    @Test
//    public void createShouldReturnNewCarDto() throws DaoException, ServiceException {
//        when(carConverter.convert()).thenReturn();
//        when(carDao.save(any())).thenReturn();
//        when(carConverter.convert()).thenReturn();
//
//        CarDto actualCarDto = carService.create();
//
//        assertEquals();
//    }

//    public static Car getCar() {
//        return new Car.Builder()
//                .withBrand("BMW")
//                .withModel("X6")
//                .withYear(2020)
//                .withAvailable((byte) 0)
//                .withEngineVolume(85)
//                .withDoors((byte)5)
//                .withLevel((byte) 1)
//                .withBody((byte) 1)
//                .withTransmission((byte) 1)
//                .withPriceId(1)
//                .withColor("COLOR")
//                .build();
//    }
}