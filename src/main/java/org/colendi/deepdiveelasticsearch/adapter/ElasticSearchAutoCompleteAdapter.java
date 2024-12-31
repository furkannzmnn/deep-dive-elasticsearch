package org.colendi.deepdiveelasticsearch.adapter;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import lombok.RequiredArgsConstructor;
import org.colendi.deepdiveelasticsearch.model.ProductModel;
import org.colendi.deepdiveelasticsearch.port.AutoCompletePort;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;


import java.util.List;

@Component
@RequiredArgsConstructor
public class ElasticSearchAutoCompleteAdapter implements AutoCompletePort {

    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<String> autoComplete(String query) {
        Query autoQuery = QueryBuilders.bool(b -> b
                .should(s -> s.prefix(p -> p.field("productName").value(query))).boost(5.0f)
                .should(s -> s.match(m -> m.field("productName").query(query))).boost(1.0f)
        );


        NativeQuery nativeQuery = NativeQuery.builder()
                .withQuery(autoQuery)
                .withPageable(Pageable.ofSize(25))
                .build();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<String> res = elasticsearchOperations.search(nativeQuery, ProductModel.class)
                .getSearchHits()
                .stream()
                .map(hit -> hit.getContent().getCategory())
                .toList();
        stopWatch.stop();
        System.out.println("Time taken to execute query: " + stopWatch.getTotalTimeSeconds());
        return res;
    }
}
