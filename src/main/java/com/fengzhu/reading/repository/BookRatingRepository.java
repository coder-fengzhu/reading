package com.fengzhu.reading.repository;

import com.fengzhu.reading.dataObject.BookRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRatingRepository extends JpaRepository<BookRating, Long>, JpaSpecificationExecutor<BookRating> {

    List<BookRating> findByBookIdAndRateDateBetween(long bookId, long startTime, long endTime);

}
