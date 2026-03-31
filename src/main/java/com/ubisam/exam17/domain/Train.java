package com.ubisam.exam17.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
// import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "example_train")
public class Train {

    @Id
    @GeneratedValue
    private Long id;

    // 기차 코드
    private String trainCode;

    // 기차 상태
    private String trainStatus;

    // 기차 좌석수
    private Integer trainTotalSeats;

    // 하나의 기차에는 하나의 드라이버
    // @ManyToOne
    // private Driver driver;

    // 하나의 기차에는 하나의 노선
    // @ManyToOne
    // private Route route;

}
