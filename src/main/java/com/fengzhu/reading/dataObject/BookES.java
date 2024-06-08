package com.fengzhu.reading.dataObject;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "canal_book")
@Data
public class BookES {

    @Id
    private long id;

    @Field(name = "book_name", type = FieldType.Text, analyzer = "ik_smart", searchAnalyzer = "ik_smart")
    private String bookName;

    @Field(type = FieldType.Text, analyzer = "ik_smart", searchAnalyzer = "ik_smart")
    private String author;

    @Field(name = "publish_date")
    private long publishDate;

    @Field
    private String isbn;

    @Field(type = FieldType.Text, analyzer = "ik_smart", searchAnalyzer = "ik_smart")
    private String introduction;

}
