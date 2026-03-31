package com.ubisam.exam17.domain;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
// import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Table(name = "example_route")
public class Route {

    @Id
    @GeneratedValue
    private Long id;

    private String routeName;
    private String routeStartStation;
    private String routeEndStation;

    // 하나의 노선에는 여러 가지 기차가 존재함.
    // @OneToMany(mappedBy = "route")
    // private List<Train> trains;

    // 하나의 노선에는 여러 가지 Station 존재
    // private List<Station> stations;

    @Transient
    private String searchName;

}
