package com.dodeuni.dodeuni.domain.hyu;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface HyuRepository extends JpaRepository<Hyu, Long> {
    List<Hyu> findAllByOrderByCreatedDateTimeAsc();

    @Transactional
    @Modifying
    @Query(value = "truncate table hyu", nativeQuery = true)
    void truncateHyuTable();
}
