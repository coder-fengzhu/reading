package com.fengzhu.reading.dataObject;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "likes_business")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikesBusiness extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String businessName;

    private String description;


}
