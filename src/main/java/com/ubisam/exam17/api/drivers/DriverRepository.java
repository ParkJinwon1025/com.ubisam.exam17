package com.ubisam.exam17.api.drivers;

import com.ubisam.exam17.domain.Driver;

import io.u2ware.common.data.jpa.repository.RestfulJpaRepository;

public interface DriverRepository extends RestfulJpaRepository<Driver, Long> {

}
