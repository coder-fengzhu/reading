package com.fengzhu.reading.dataObject;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "book_tag")
@Builder
@Data
@AllArgsConstructor
public class BookTag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long bookId;

    private long tagGroupId;

    private long tagValue;

    public BookTag() {
    }
}
