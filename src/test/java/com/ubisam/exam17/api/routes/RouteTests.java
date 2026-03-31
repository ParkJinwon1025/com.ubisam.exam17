package com.ubisam.exam17.api.routes;

import static io.u2ware.common.docs.MockMvcRestDocs.delete;
import static io.u2ware.common.docs.MockMvcRestDocs.is2xx;
import static io.u2ware.common.docs.MockMvcRestDocs.post;
import static io.u2ware.common.docs.MockMvcRestDocs.put;
import static io.u2ware.common.docs.MockMvcRestDocs.print;
import static io.u2ware.common.docs.MockMvcRestDocs.result;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.ubisam.exam17.domain.Route;

import io.u2ware.common.data.jpa.repository.query.JpaSpecificationBuilder;

@SpringBootTest
@AutoConfigureMockMvc
public class RouteTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RouteDocs docs;

    @Autowired
    private RouteRepository routeRepository;

    // CRUD
    @Test
    public void contextLoads() throws Exception {

        // Create
        mockMvc.perform(post("/api/routes").content(docs::newEntity, "route1")).andExpect(is2xx())
                .andDo(result(docs::context, "routeEntity"));

        // Read
        String uri = docs.context("routeEntity", "$._links.self.href");
        mockMvc.perform(post(uri)).andExpect(is2xx());

        // Update
        Map<String, Object> entity = docs.context("routeEntity", "$");
        mockMvc.perform(put(uri).content(docs::updateEntity, entity,
                "route11")).andExpect(is2xx());

        // Delete
        mockMvc.perform(delete(uri)).andExpect(is2xx());
    }

    // Handler
    @Test
    public void contextLoads1() throws Exception {
        List<Route> result;
        boolean hasResult;

        // 40명의 노선 등록
        List<Route> routeList = new ArrayList<>();
        for (int i = 1; i <= 40; i++) {
            routeList.add(docs.newEntity("제" + i + "노선", "출발역" + i, "도착역" + i));
        }
        routeRepository.saveAll(routeList);

        // 1. 노선 이름 Query
        JpaSpecificationBuilder<Route> query = JpaSpecificationBuilder.of(Route.class);
        query.where().and().eq("routeName", "제23노선");
        result = routeRepository.findAll(query.build());
        hasResult = result.stream().anyMatch(u -> "제23노선".equals(u.getRouteName()));
        assertEquals(true, hasResult);

        // 2. 노선 출발역 Query
        JpaSpecificationBuilder<Route> query1 = JpaSpecificationBuilder.of(Route.class);
        query1.where().and().eq("routeStartStation", "출발역5");
        result = routeRepository.findAll(query1.build());
        hasResult = result.stream().anyMatch(u -> "출발역5".equals(u.getRouteStartStation()));
        assertEquals(true, hasResult);

        // 3. 노선 종착역 Query
        JpaSpecificationBuilder<Route> query2 = JpaSpecificationBuilder.of(Route.class);
        query2.where().and().eq("routeEndStation", "도착역40");
        result = routeRepository.findAll(query2.build());
        hasResult = result.stream().anyMatch(u -> "도착역40".equals(u.getRouteEndStation()));
        assertEquals(true, hasResult);

    }

    @Test
    public void contextLoads2() throws Exception {

        // 40명의 노선 등록
        List<Route> routeList = new ArrayList<>();
        for (int i = 1; i <= 40; i++) {
            routeList.add(docs.newEntity("제" + i + "노선", "출발역" + i, "도착역" + i));
        }
        routeRepository.saveAll(routeList);

        // 1. 노선 이름으로 검색
        mockMvc.perform(post("/api/routes/search").content(docs::setSearchName, "제33노선")).andExpect(is2xx());

        // 2. 페이지네이션 - 8개씩 5페이지
        mockMvc.perform(post("/api/routes/search").param("size",
                "8")).andExpect(is2xx()).andDo(print());

        // 3. 정렬, routeName,desc
        mockMvc.perform(post("/api/routes/search").param("sort",
                "routeName,desc")).andExpect(is2xx()).andDo(print());

    }

}
