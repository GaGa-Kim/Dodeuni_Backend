package com.dodeuni.dodeuni.domain.hyu;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface HyuRepository extends JpaRepository<Hyu, Long> {
    List<Hyu> findAllByOrderByCreatedDateTimeAsc();

    @Transactional
    @Modifying
    @Query(value = "truncate table hyu", nativeQuery = true)
    void truncateHyuTable();
}
