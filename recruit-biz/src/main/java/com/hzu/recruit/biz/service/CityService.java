package com.hzu.recruit.biz.service;

import com.hzu.recruit.biz.mapper.ProjectMapper;
import com.hzu.recruit.common.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    @Autowired
    private ProjectMapper projectMapper;

    public List<City> getAllCities() {
        City city = new City();
        List<City> cities = projectMapper.selectCity(city);
        return cities;
    }
}
