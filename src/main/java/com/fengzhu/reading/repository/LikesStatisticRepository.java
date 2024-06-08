package com.fengzhu.reading.repository;

import com.fengzhu.reading.dataObject.LikesStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikesStatisticRepository extends JpaRepository<LikesStatistic, Long>, JpaSpecificationExecutor<LikesStatistic> {

    Optional<LikesStatistic> findByBusinessIdAndItemId(long businessId, long itemId);

}
