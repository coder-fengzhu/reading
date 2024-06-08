package com.fengzhu.reading.repository;

import com.fengzhu.reading.dataObject.BookAvgRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookAvgRatingRepository extends JpaRepository<BookAvgRating, Long>, JpaSpecificationExecutor<BookAvgRating> {

    Optional<BookAvgRating> findByBookIdAndEnable(long bookId, boolean enable);

}
