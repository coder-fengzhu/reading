package com.fengzhu.reading.dataObject;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "book")
@Data
@Builder
@AllArgsConstructor
public class Book extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    private String bookName;

    private String author;

    private long publishDate;

    private String isbn;

    private String introduction;

    public Book() {
    }
}
