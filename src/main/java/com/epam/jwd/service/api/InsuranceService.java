package com.epam.jwd.service.api;

import com.epam.jwd.service.dto.InsuranceDto;

public interface InsuranceService extends Service<InsuranceDto, Integer> {
    Double getAmountInsurance(Byte type);
    String getGenerateNumber();
    String getCompany();
    Double getCostInsurance(Byte type);
}
