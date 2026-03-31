package com.ubisam.exam17.domain;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Table(name = "example_station")
public class Station {

    @Id
    @GeneratedValue
    private Long id;

    private String stationName;
    private String stationCode;

    // 하나의 역에는 여러 개의 호선이 존재
    // @ManyToMany
    // private List<Route> routes;

    @Transient
    private String searchName;

    @Transient
    private String searchCode;

}
