package com.epam.jwd.service.impl;

import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.impl.InsuranceDaoImpl;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.converter.impl.InsuranceConverter;
import com.epam.jwd.service.dto.InsuranceDto;
import com.epam.jwd.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class InsuranceServiceImpl implements Service<InsuranceDto, Integer> {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private final InsuranceConverter converter = new InsuranceConverter();
    private final InsuranceDaoImpl insuranceDao = new InsuranceDaoImpl();

    @Override
    public InsuranceDto create(InsuranceDto insuranceDto) throws ServiceException, SQLException {
        return null;
    }

    @Override
    public Boolean update(InsuranceDto insuranceDto) throws ServiceException {
        return null;
    }

    @Override
    public Boolean delete(InsuranceDto insuranceDto) throws ServiceException {
        return null;
    }

    /**
     * Convert and get entity by id
     *
     * @param id
     * @return entity insuranceDto
     */
    @Override
    public InsuranceDto getById(Integer id) throws ServiceException, DaoException {
        logger.info("get by id " + InsuranceServiceImpl.class);
        return converter.convert(insuranceDao.findById(id));
    }

    @Override
    public List<InsuranceDto> getAll() {
        return null;
    }

    /**
     * Receiving the insurance payment amount
     *
     * @param type insurance
     * @return amount
     */
    protected Double getAmountInsurance(Byte type) {
        logger.info("get amount insurance method " + InsuranceServiceImpl.class);
        Double amount = 0.00;
        switch (type) {
            case 1: {
                amount = 550.00;
                break;
            }
            case 2: {
                amount = 1950.00;
                break;
            }
            case 3: {
                amount = 8000.00;
                break;
            }
        }
        return amount;
    }

    /**
     * Creation of insurance number
     *
     * @return insurance number
     */
    protected String getGenerateNumber() {
        logger.info("generate number method " + InsuranceServiceImpl.class);
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            char c = (char) (random.nextInt(26) + 'a');
            stringBuffer.append(c);
        }
        return stringBuffer.toString().toUpperCase();
    }

    /**
     * Company name
     *
     * @return company name
     */
    protected String getCompany() {
        logger.info("get company method " + InsuranceServiceImpl.class);
        return "comGovInsurance";
    }

    /**
     * Receiving the amount for payment based on the type of insurance
     *
     * @param type insurance
     * @return amount
     */
    protected Double getCostInsurance(Byte type) {
        logger.info("get cost method " + InsuranceServiceImpl.class);
        Double cost = 0.00;
        switch (type) {
            case 1: {
                cost = 20.00;
                break;
            }
            case 2: {
                cost = 70.00;
                break;
            }
            case 3: {
                cost = 150.00;
                break;
            }
        }
        return cost;
    }
}
