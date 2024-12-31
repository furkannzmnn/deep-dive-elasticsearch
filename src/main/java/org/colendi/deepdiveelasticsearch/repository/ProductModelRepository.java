package org.colendi.deepdiveelasticsearch.repository;

import org.colendi.deepdiveelasticsearch.model.ProductModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductModelRepository extends ElasticsearchRepository<ProductModel, String> {
}
