package com.ubisam.exam17.api.stations;

import com.ubisam.exam17.domain.Station;

import io.u2ware.common.data.jpa.repository.RestfulJpaRepository;

public interface StationRepository extends RestfulJpaRepository<Station, Long> {

}
