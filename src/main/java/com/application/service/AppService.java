package com.application.service;

import com.application.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AppService {
    private TariffService tariffService;

    @Autowired
    public AppService(TariffService tariffService) {
        this.tariffService = tariffService;
    }

    public double calculateCost(Date begin, Date end) throws DataNotFoundException {
        double hourCost = tariffService.getCurrentTariff().getValue();
        double time = Math.ceil((end.getTime() - begin.getTime()) / 60000.0);
        return Math.ceil(100 * time * (hourCost / 60)) / 100;
    }
}
