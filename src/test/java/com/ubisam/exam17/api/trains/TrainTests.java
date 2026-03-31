package com.ubisam.exam17.api.trains;

import static io.u2ware.common.docs.MockMvcRestDocs.is2xx;
import static io.u2ware.common.docs.MockMvcRestDocs.post;
import static io.u2ware.common.docs.MockMvcRestDocs.delete;
import static io.u2ware.common.docs.MockMvcRestDocs.get;
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

import com.ubisam.exam17.domain.Train;

import io.u2ware.common.data.jpa.repository.query.JpaSpecificationBuilder;

@SpringBootTest
@AutoConfigureMockMvc
public class TrainTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TrainDocs docs;

    @Autowired
    private TrainRepository trainRepository;

    // CRUD
    @Test
    public void contextLoads() throws Exception {

        // Create
        mockMvc.perform(post("/api/trains").content(docs::newEntity, "train1"))
                .andExpect(is2xx()).andDo(result(docs::context, "trainEntity"));

        // Read
        String uri = docs.context("trainEntity", "$._links.self.href");
        mockMvc.perform(get(uri)).andExpect(is2xx());

        // Update
        Map<String, Object> entity = docs.context("trainEntity", "$");
        mockMvc.perform(put(uri).content(docs::updateEntity, entity, "train12")).andExpect(is2xx());

        // Delete
        mockMvc.perform(delete(uri)).andExpect(is2xx());

    }

    // Handler
    @Test
    public void contextLoads1() throws Exception {
        List<Train> result;
        boolean hasResult;

        // 40개 생성
        List<Train> trainList = new ArrayList<>();
        for (int i = 1; i <= 40; i++) {
            trainList.add(docs.newEntity("KTX" + i, "status" + i, i * 10 + ""));

        }
        trainRepository.saveAll(trainList);

        // 1. Code Query
        JpaSpecificationBuilder<Train> query = JpaSpecificationBuilder.of(Train.class);
        query.where().and().eq("trainCode", "KTX12");
        result = trainRepository.findAll(query.build());
        hasResult = result.stream().anyMatch(u -> "KTX12".equals(u.getTrainCode()));
        assertEquals(true, hasResult);

        // 2. Status Query
        JpaSpecificationBuilder<Train> query1 = JpaSpecificationBuilder.of(Train.class);
        query1.where().and().eq("trainStatus", "status23");
        result = trainRepository.findAll(query1.build());
        hasResult = result.stream().anyMatch(u -> "status23".equals(u.getTrainStatus()));
        assertEquals(true, hasResult);

        // 3. totalSeats Query
        JpaSpecificationBuilder<Train> query2 = JpaSpecificationBuilder.of(Train.class);
        query2.where().and().eq("trainTotalSeats", 200);
        result = trainRepository.findAll(query2.build());
        hasResult = result.stream().anyMatch(u -> 200 == u.getTrainTotalSeats());
        assertEquals(true, hasResult);

    }

    // Search
    @Test
    public void contextLoads2() throws Exception {

        // 기차 40개 생성
        List<Train> trainList = new ArrayList<>();
        for (int i = 1; i <= 40; i++) {
            trainList.add(docs.newEntity("KTX" + i, "status" + i, i * 10 + ""));

        }
        trainRepository.saveAll(trainList);

        // 1. 기차 코드로 검색
        mockMvc.perform(get("/api/trains/search/findByTrainCode").param("trainCode", "KTX5")).andExpect(is2xx());

        // 2. 기차 상태로 검색
        mockMvc.perform(get("/api/trains/search/findByTrainStatus").param("trainStatus", "status33"))
                .andExpect(is2xx());

        // 3. 기차 자리수로 검색
        mockMvc.perform(get("/api/trains/search/findByTrainTotalSeats").param("trainTotalSeats", "21"))
                .andExpect(is2xx());

        // 4. 페이지네이션 : 10개씩 4페이지
        mockMvc.perform(get("/api/trains").param("size", "10")).andExpect(is2xx()).andDo(print());

        // 5. 정렬 : trainName,desc
        mockMvc.perform(get("/api/trains").param("sort", "trainName,desc")).andExpect(is2xx()).andDo(print());
    }

}
