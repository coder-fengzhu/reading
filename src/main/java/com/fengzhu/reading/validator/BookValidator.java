package com.fengzhu.reading.validator;

import com.fengzhu.reading.dto.BookDTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class BookValidator {

    public void validateAddNewBook(BookDTO bookDTO) {
        if (StringUtils.isEmpty(bookDTO.getBookName())) {
            throw new IllegalArgumentException("book name is empty");
        }

        if (StringUtils.isEmpty(bookDTO.getAuthor())) {
            throw new IllegalArgumentException("book author is empty");
        }

    }
}
