package com.application.service;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AppService {
    public double calculateCost(Date begin, Date end) {
        double hourCost = 1;
        double time = Math.ceil((end.getTime() - begin.getTime()) / 3600000.0);
        System.out.println(time);
        return time * hourCost;
    }
}
