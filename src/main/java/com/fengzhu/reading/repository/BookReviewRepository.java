package com.fengzhu.reading.repository;

import com.fengzhu.reading.dataObject.BookReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BookReviewRepository extends JpaRepository<BookReview, Long>, JpaSpecificationExecutor<BookReview>  {

    Page<BookReview> findByBookId(long bookId, Pageable pageable);

}
