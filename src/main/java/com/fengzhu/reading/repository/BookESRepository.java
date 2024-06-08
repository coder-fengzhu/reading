package com.fengzhu.reading.repository;

import com.fengzhu.reading.dataObject.BookES;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Highlight;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookESRepository extends ElasticsearchRepository<BookES, Long> {

    @Highlight(fields = {
            @HighlightField(name = "book_name")
    })
    Page<BookES> findByBookNameOrIntroductionLike(String bookName, String introduction, Pageable pageable);

}
