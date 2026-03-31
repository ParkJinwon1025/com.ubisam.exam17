package com.ubisam.exam17.domain;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Table(name = "example_driver")
public class Driver {

    @Id
    @GeneratedValue
    private Long id;

    private String driverName;
    private String driverLicenseNumber;
    private Integer driverCareerYears;

    // 하나의 드라이버가 여러 개의 기차를 몰 수 있음.
    // @OneToMany(mappedBy = "driver")
    // private List<Train> trains;

    @Transient
    private String searchName;

    @Transient
    private String searchLicenseNumber;

    @Transient
    private Integer searchCareerYears;

}
