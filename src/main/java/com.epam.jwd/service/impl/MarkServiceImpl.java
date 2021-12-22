package com.epam.jwd.service.impl;

import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.impl.MarkDaoImpl;
import com.epam.jwd.dao.model.mark.Mark;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.converter.impl.MarkConverter;
import com.epam.jwd.service.dto.MarkDto;
import com.epam.jwd.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class MarkServiceImpl implements Service<MarkDto, Integer> {
    private static final Logger logger = LogManager.getLogger(MarkServiceImpl.class);
    private final MarkDaoImpl markDao = new MarkDaoImpl();
    private final MarkConverter markConverter = new MarkConverter();

    @Override
    public MarkDto create(MarkDto value) throws ServiceException, DaoException {
        return null;
    }

    /**
     * Convert and submitting an entity to save
     *
     * @param markDto
     * @return the boolean
     */
    @Override
    public Boolean update(MarkDto markDto) throws ServiceException, DaoException {
        logger.info("update method " + MarkServiceImpl.class);
        Mark mark = markConverter.convert(markDto);
        markDao.update(mark);
        return true;
    }

    @Override
    public Boolean delete(MarkDto priceDto) throws ServiceException, DaoException {
        return null;
    }

    /**
     * Convert and return entity
     *
     * @param id
     * @return MarkDao entity
     */
    @Override
    public MarkDto getById(Integer id) throws ServiceException, DaoException {
        logger.info("get by id method " + MarkServiceImpl.class);
        return markConverter.convert(markDao.findById(id));
    }

    @Override
    public List<MarkDto> getAll() throws DaoException, ServiceException {
        return null;
    }
}
