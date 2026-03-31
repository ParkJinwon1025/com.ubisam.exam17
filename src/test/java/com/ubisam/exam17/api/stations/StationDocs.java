package com.ubisam.exam17.api.stations;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ubisam.exam17.domain.Station;

import io.u2ware.common.docs.MockMvcRestDocs;

@Component
public class StationDocs extends MockMvcRestDocs {

    public Station newEntity(String... entity) {
        Station station = new Station();
        station.setStationName(entity.length > 0 ? entity[0] : super.randomText("stationName"));
        station.setStationCode(entity.length > 1 ? entity[1] : super.randomText("stationCode"));
        return station;
    }

    public Map<String, Object> updateEntity(Map<String, Object> entity, String name) {

        entity.put("stationName", name);
        return entity;
    }

    public Map<String, Object> setSearchName(String name) {
        Map<String, Object> entity = new HashMap<>();
        entity.put("searchName", name);
        return entity;
    }

    public Map<String, Object> setSearchCode(String code) {
        Map<String, Object> entity = new HashMap<>();
        entity.put("searchCode", code);
        return entity;
    }

}
