package com.application.service;

import com.application.exception.DataNotFoundException;
import com.application.model.dto.PersonDTO;
import com.application.model.entity.Person;
import com.application.model.entity.Tariff;
import com.application.repository.TariffRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TariffService {

    private TariffRepository tariffRepository;

    @Autowired
    public TariffService(TariffRepository tariffRepository) {
        this.tariffRepository = tariffRepository;
    }

    public Tariff getCurrentTariff() throws DataNotFoundException {
        List<Tariff> list= tariffRepository.findByStatus(true);
        if (list.size() == 1){
            return list.get(0);
        } else {
            throw new DataNotFoundException("DataBase Error");
        }
    }

    @Transactional
    public void changeTariff(double newValue) throws DataNotFoundException {
        Tariff oldTariff = getCurrentTariff();
        oldTariff.setStatus(false);
        Tariff newTariff = new Tariff();
        newTariff.setValue(newValue);
        newTariff.setStatus(true);
        tariffRepository.save(newTariff);
    }
}
