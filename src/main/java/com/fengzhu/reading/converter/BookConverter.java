package com.fengzhu.reading.converter;

import com.fengzhu.reading.dataObject.Book;
import com.fengzhu.reading.dataObject.BookES;
import com.fengzhu.reading.dto.BookDTO;

public class BookConverter {

    public static Book convertToBook(BookDTO bookDTO) {
        if (bookDTO == null) {
            return null;
        }
        return Book.builder()
                .id(bookDTO.getId())
                .bookName(bookDTO.getBookName())
                .author(bookDTO.getAuthor())
                .introduction(bookDTO.getIntroduction())
                .isbn(bookDTO.getIsbn())
                .publishDate(bookDTO.getPublishDate())
                .build();

    }

    public static BookDTO converToBookDTO(Book book) {
        if (book == null) {
            return null;
        }
        return BookDTO.builder()
                .id(book.getId())
                .bookName(book.getBookName())
                .author(book.getAuthor())
                .introduction(book.getIntroduction())
                .isbn(book.getIsbn())
                .publishDate(book.getPublishDate()).build();
    }

    public static BookDTO converToBookDTO(BookES book) {
        if (book == null) {
            return null;
        }
        return BookDTO.builder()
                .id(book.getId())
                .bookName(book.getBookName())
                .author(book.getAuthor())
                .introduction(book.getIntroduction())
                .isbn(book.getIsbn())
                .publishDate(book.getPublishDate()).build();
    }


}
