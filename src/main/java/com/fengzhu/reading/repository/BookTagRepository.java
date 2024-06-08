package com.fengzhu.reading.repository;

import com.fengzhu.reading.dataObject.BookTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookTagRepository extends JpaRepository<BookTag, Long>, JpaSpecificationExecutor<BookTag>  {

    void deleteByBookId(long bookId);

    @Query(value = "select * from book_tag where tag_group_id=:tagGroupId and tag_value & :tagValue > 0", nativeQuery = true)
    Page<BookTag> findByTag(@Param("tagGroupId") long tagGroupId, @Param("tagValue") long tagValue, Pageable pageable);

    List<BookTag> findByBookId(Long bookId);
}
