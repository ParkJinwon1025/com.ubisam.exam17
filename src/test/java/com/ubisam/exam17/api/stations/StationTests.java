package com.ubisam.exam17.api.stations;

import static io.u2ware.common.docs.MockMvcRestDocs.delete;
import static io.u2ware.common.docs.MockMvcRestDocs.is2xx;
import static io.u2ware.common.docs.MockMvcRestDocs.post;
import static io.u2ware.common.docs.MockMvcRestDocs.put;
import static io.u2ware.common.docs.MockMvcRestDocs.result;
import static io.u2ware.common.docs.MockMvcRestDocs.print;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.ubisam.exam17.domain.Station;

import io.u2ware.common.data.jpa.repository.query.JpaSpecificationBuilder;

@SpringBootTest
@AutoConfigureMockMvc
public class StationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StationDocs docs;

    @Autowired
    private StationRepository stationRepository;

    // CRUD
    @Test
    public void contextLoads() throws Exception {

        // Create
        mockMvc.perform(post("/api/stations").content(docs::newEntity, "1번 정류장")).andExpect(is2xx())
                .andDo(result(docs::context, "stationEntity"));

        // Read
        String uri = docs.context("stationEntity", "$._links.self.href");
        mockMvc.perform(post(uri)).andExpect(is2xx());

        // Update
        Map<String, Object> entity = docs.context("stationEntity", "$");
        mockMvc.perform(put(uri).content(docs::updateEntity, entity,
                "11번 정류장")).andExpect(is2xx());

        // Delete
        mockMvc.perform(delete(uri)).andExpect(is2xx());
    }

    // Handler
    @Test
    public void contextLoads1() throws Exception {
        List<Station> result;
        boolean hasResult;

        // 40개의 정류장 등록
        List<Station> stationList = new ArrayList<>();
        for (int i = 1; i <= 40; i++) {
            stationList.add(docs.newEntity("제" + i + "정류장", "code" + i));
        }
        stationRepository.saveAll(stationList);

        // 1. 정류장 이름 Query
        JpaSpecificationBuilder<Station> query = JpaSpecificationBuilder.of(Station.class);
        query.where().and().eq("stationName", "제3정류장");
        result = stationRepository.findAll(query.build());
        hasResult = result.stream().anyMatch(u -> "제3정류장".equals(u.getStationName()));
        assertEquals(true, hasResult);

        // 2. 정류장 코드 Query
        JpaSpecificationBuilder<Station> query1 = JpaSpecificationBuilder.of(Station.class);
        query1.where().and().eq("stationCode", "code36");
        result = stationRepository.findAll(query1.build());
        hasResult = result.stream().anyMatch(u -> "code36".equals(u.getStationCode()));
        assertEquals(true, hasResult);

    }

    @Test
    public void contextLoads2() throws Exception {
        // 40개의 정류장 등록
        List<Station> stationList = new ArrayList<>();
        for (int i = 1; i <= 40; i++) {
            stationList.add(docs.newEntity("제" + i + "정류장", "code" + i));
        }
        stationRepository.saveAll(stationList);

        // 1. 정류장 이름으로 검색
        mockMvc.perform(post("/api/stations/search").content(docs::setSearchName, "제20정류장")).andExpect(is2xx());

        // 2. 정류장 코드로 검색
        mockMvc.perform(post("/api/stations/search").content(docs::setSearchCode,
                "code39")).andExpect(is2xx());

        // 3. 페이지네이션 - 8개씩 5페이지
        mockMvc.perform(post("/api/stations/search").param("size",
                "8")).andExpect(is2xx()).andDo(print());

        // 4. 정렬, stationName,desc
        mockMvc.perform(post("/api/stations/search").param("sort",
                "stationName,desc")).andExpect(is2xx()).andDo(print());

    }

}
