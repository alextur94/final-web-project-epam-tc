package com.epam.jwd.service.impl;

import com.epam.jwd.dao.api.MarkDao;
import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.impl.MarkDaoImpl;
import com.epam.jwd.dao.model.mark.Mark;
import com.epam.jwd.service.api.MarkService;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.converter.impl.MarkConverterImpl;
import com.epam.jwd.service.dto.MarkDto;
import com.epam.jwd.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class MarkServiceImpl implements MarkService {
    private static final Logger logger = LogManager.getLogger(MarkServiceImpl.class);
    private MarkDao markDao = new MarkDaoImpl();
    private Converter<Mark, MarkDto, Integer> markConverterImpl = new MarkConverterImpl();

    @Override
    public MarkDto create(MarkDto value) {
        return null;
    }

    /**
     * Convert and submitting an entity to save
     *
     * @param markDto
     * @return the boolean
     */
    @Override
    public Boolean update(MarkDto markDto) throws ServiceException {
        logger.info("update method " + MarkServiceImpl.class);
        Mark mark = markConverterImpl.convert(markDto);
        try {
            markDao.update(mark);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return true;
    }

    @Override
    public Boolean delete(MarkDto priceDto) {
        return null;
    }

    /**
     * Convert and return entity
     *
     * @param id
     * @return MarkDao entity
     */
    @Override
    public MarkDto getById(Integer id) throws ServiceException {
        logger.info("get by id method " + MarkServiceImpl.class);
        try {
            return markConverterImpl.convert(markDao.findById(id));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<MarkDto> getAll() {
        return null;
    }
}
