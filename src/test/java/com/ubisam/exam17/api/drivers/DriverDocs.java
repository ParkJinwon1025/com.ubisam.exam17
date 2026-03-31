package com.ubisam.exam17.api.drivers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ubisam.exam17.domain.Driver;

import io.u2ware.common.docs.MockMvcRestDocs;

@Component
public class DriverDocs extends MockMvcRestDocs {

    public Driver newEntity(String... entity) {
        Driver driver = new Driver();
        driver.setDriverName(entity.length > 0 ? entity[0] : super.randomText("driverName"));
        driver.setDriverLicenseNumber(entity.length > 1 ? entity[1] : super.randomText("diverLicenseNumber"));
        driver.setDriverCareerYears(entity.length > 2 ? Integer.valueOf(entity[2]) : super.randomInt());
        return driver;
    }

    public Map<String, Object> updateEntity(Map<String, Object> entity, String name) {

        entity.put("driverName", name);
        return entity;
    }

    public Map<String, Object> setSearchName(String name) {
        Map<String, Object> entity = new HashMap<>();
        entity.put("searchName", name);
        return entity;
    }

    public Map<String, Object> setSearchLicenseNumber(String number) {
        Map<String, Object> entity = new HashMap<>();
        entity.put("searchLicenseNumber", number);
        return entity;
    }

    public Map<String, Object> setSearchCareerYears(Integer year) {
        Map<String, Object> entity = new HashMap<>();
        entity.put("searchCareerYears", year);
        return entity;
    }

}
