package com.ubisam.exam17.api.routes;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ubisam.exam17.domain.Driver;
import com.ubisam.exam17.domain.Route;

import io.u2ware.common.docs.MockMvcRestDocs;

@Component
public class RouteDocs extends MockMvcRestDocs {

    public Route newEntity(String... entity) {
        Route route = new Route();
        route.setRouteName(entity.length > 0 ? entity[0] : super.randomText("routeName"));
        route.setRouteStartStation(entity.length > 1 ? entity[1] : super.randomText("routeStartStation"));
        route.setRouteEndStation(entity.length > 2 ? entity[2] : super.randomText("routeEndStation"));
        return route;
    }

    public Map<String, Object> updateEntity(Map<String, Object> entity, String name) {

        entity.put("routeName", name);
        return entity;
    }

    public Map<String, Object> setSearchName(String name) {
        Map<String, Object> entity = new HashMap<>();
        entity.put("searchName", name);
        return entity;
    }

}
