package com.fengzhu.reading.dataObject;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book_rating")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookRating extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long bookId;

    private long userId;

    private int score;

    private long rateDate;

}
