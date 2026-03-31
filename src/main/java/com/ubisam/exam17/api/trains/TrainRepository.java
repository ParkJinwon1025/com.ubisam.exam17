package com.ubisam.exam17.api.trains;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ubisam.exam17.domain.Train;

public interface TrainRepository extends JpaRepository<Train, Long>, JpaSpecificationExecutor<Train> {

    List<Train> findByTrainCode(String trainCode);

    List<Train> findByTrainStatus(String trainStatus);

    List<Train> findByTrainTotalSeats(Integer trainTotalSeats);

}
