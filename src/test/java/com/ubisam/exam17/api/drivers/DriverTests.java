package com.ubisam.exam17.api.drivers;

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

import com.ubisam.exam17.domain.Driver;

import io.u2ware.common.data.jpa.repository.query.JpaSpecificationBuilder;

@SpringBootTest
@AutoConfigureMockMvc
public class DriverTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DriverDocs docs;

    @Autowired
    private DriverRepository driverRepository;

    // CRUD
    @Test
    public void contextLoads() throws Exception {

        // Create
        mockMvc.perform(post("/api/drivers").content(docs::newEntity, "driver1")).andExpect(is2xx())
                .andDo(result(docs::context, "driverEntity"));

        // Read
        String uri = docs.context("driverEntity", "$._links.self.href");
        mockMvc.perform(post(uri)).andExpect(is2xx());

        // Update
        Map<String, Object> entity = docs.context("driverEntity", "$");
        mockMvc.perform(put(uri).content(docs::updateEntity, entity, "driver11")).andExpect(is2xx());

        // Delete
        mockMvc.perform(delete(uri)).andExpect(is2xx());
    }

    // Handler
    @Test
    public void contextLoads1() throws Exception {
        List<Driver> result;
        boolean hasResult;

        // 40명의 드라이버 등록
        List<Driver> driverList = new ArrayList<>();
        for (int i = 1; i <= 40; i++) {
            driverList.add(docs.newEntity("드라이버" + i, "License" + i, i + ""));
        }
        driverRepository.saveAll(driverList);

        // 1. 드라이버 이름 query
        JpaSpecificationBuilder<Driver> query = JpaSpecificationBuilder.of(Driver.class);
        query.where().and().eq("driverName", "드라이버33");
        result = driverRepository.findAll(query.build());
        hasResult = result.stream().anyMatch(u -> "드라이버33".equals(u.getDriverName()));
        assertEquals(true, hasResult);

        // 2. 드라이버 면허 번호 query
        JpaSpecificationBuilder<Driver> query1 = JpaSpecificationBuilder.of(Driver.class);
        query1.where().and().eq("driverLicenseNumber", "License22");
        result = driverRepository.findAll(query1.build());
        hasResult = result.stream().anyMatch(u -> "License22".equals(u.getDriverLicenseNumber()));
        assertEquals(true, hasResult);

        // 3. 드라이버 경력 query
        JpaSpecificationBuilder<Driver> query2 = JpaSpecificationBuilder.of(Driver.class);
        query2.where().and().eq("driverCareerYears", 30);
        result = driverRepository.findAll(query2.build());
        hasResult = result.stream().anyMatch(u -> 30 == u.getDriverCareerYears());
        assertEquals(true, hasResult);

    }

    // Search
    @Test
    public void contextLoads2() throws Exception {

        // 40명의 드라이버 등록
        List<Driver> driverList = new ArrayList<>();
        for (int i = 1; i <= 40; i++) {
            driverList.add(docs.newEntity("드라이버" + i, "License" + i, i + ""));
        }
        driverRepository.saveAll(driverList);

        // 1. 드라이버 이름으로 검색
        mockMvc.perform(post("/api/drivers/search").content(docs::setSearchName, "드라이버23")).andExpect(is2xx());

        // 2. 드라이버 면허 번호로 검색
        mockMvc.perform(post("/api/drivers/search").content(docs::setSearchLicenseNumber, "License4"))
                .andExpect(is2xx());

        // 3. 드라이버 경력로 검색
        mockMvc.perform(post("/api/drivers/search").content(docs::setSearchCareerYears, 30))
                .andExpect(is2xx());

        // 4. 페이지네이션 - 8개씩 5페이지
        mockMvc.perform(post("/api/drivers/search").param("size", "8")).andExpect(is2xx()).andDo(print());

        // 5. 정렬, driverName,desc
        mockMvc.perform(post("/api/drivers/search").param("sort", "driverName,desc")).andExpect(is2xx()).andDo(print());

    }

}
