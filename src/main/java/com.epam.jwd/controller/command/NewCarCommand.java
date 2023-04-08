package com.epam.jwd.controller.command;

import com.epam.jwd.controller.api.Command;
import com.epam.jwd.controller.api.CommandRequest;
import com.epam.jwd.controller.api.CommandResponse;
import com.epam.jwd.service.dto.CarDto;
import com.epam.jwd.service.dto.PriceDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.CarServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;

public enum NewCarCommand implements Command {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger(NewCarCommand.class);
    private static final CommandResponse ERROR_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return Constant.COMM_NEW_CAR_PAGE;
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };
    private static final CommandResponse SUCCESS_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return Constant.COMM_NEW_CAR_PAGE;
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };
    final CarServiceImpl carService = new CarServiceImpl();

    @Override
    public CommandResponse execute(CommandRequest request) {
        HttpSession session = request.getCurrentSession().get();
        try {
            final String brand = request.getParameter(Constant.BRAND_PARAM);
            final String model = request.getParameter(Constant.MODEL_PARAM);
            final Integer year = Integer.parseInt(request.getParameter(Constant.YEAR_PARAM));
            final Byte level = Byte.parseByte(request.getParameter(Constant.LEVEL_PARAM));
            final Byte body = Byte.parseByte(request.getParameter(Constant.BODY_PARAM));
            final Integer engineVolume = Integer.parseInt(request.getParameter(Constant.ENGINE_VOLUME_PARAM));
            Byte trans;
            String tempTrans = request.getParameter(Constant.TRANSMISSION_PARAM);
            if (tempTrans.equals("on")) {
                trans = 1;
            } else {
                trans = 0;
            }
            final Byte doors = Byte.parseByte(request.getParameter(Constant.DOORS_PARAM));
            final String color = request.getParameter(Constant.COLOR_PARAM);
            Byte available;
            String ava = request.getParameter(Constant.AVAILABLE_PARAM);
            if (ava.equals("on")) {
                available = 1;
            } else {
                available = 0;
            }
            final Double price = Double.parseDouble(request.getParameter(Constant.PRICE_PARAM));
            CarDto carDto = new CarDto.Builder()
                    .withBrand(brand)
                    .withModel(model)
                    .withYear(year)
                    .withLevel(level)
                    .withBody(body)
                    .withEngineVolume(engineVolume)
                    .withTransmission(trans)
                    .withDoors(doors)
                    .withColor(color)
                    .withAvailable(available)
                    .build();
            PriceDto priceDto = new PriceDto(price);
            carService.saveCar(carDto, priceDto);
            session.setAttribute(Constant.SUCCESS_PARAM, Constant.SUCCESS_CAR_CREATE_MSS);
            return SUCCESS_RESPONSE;
        } catch (NumberFormatException exception) {
            logger.error(exception);
            session.setAttribute(Constant.ERROR_PARAM, Constant.ERROR_FIELDS_IS_NULL_MSS);
            return ERROR_RESPONSE;
        } catch (ServiceException exception) {
            logger.error(exception);
            session.setAttribute(Constant.ERROR_PARAM, exception.getMessage());
            return ERROR_RESPONSE;
        }
    }
}
