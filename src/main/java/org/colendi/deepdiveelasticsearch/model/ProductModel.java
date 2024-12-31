package org.colendi.deepdiveelasticsearch.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Document(indexName = "product_model")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Setting(settingPath = "product-index-analyzer.json")
public class ProductModel {

    @Id
    private String id;
    private String brand;
    private String category;
    private String description;
    @Field(type = FieldType.Text, analyzer = "autocomplete", searchAnalyzer = "standard")
    private String productName;
    private String stockCode;
    private String unitPrice;
}
