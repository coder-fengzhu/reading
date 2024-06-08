package com.fengzhu.reading.dataObject;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "likes_statistic")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LikesStatistic extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long businessId;

    private long itemId;

    private long likeCount;

}
