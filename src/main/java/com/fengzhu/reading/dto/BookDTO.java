package com.fengzhu.reading.dto;

import com.fengzhu.reading.dataObject.BookAvgRating;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
public class BookDTO {

    private long id;

    private String bookName;

    private String author;

    private long publishDate;

    private String isbn;

    private String introduction;

    private List<TagDTO> tagDTOList;

    private Page<BookReviewDTO> bookReviewDTOList;

    private BookAvgRatingDTO bookAvgRatingDTO;

}
