package org.colendi.deepdiveelasticsearch.config;

import co.elastic.clients.util.ContentType;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static org.apache.http.client.utils.URLEncodedUtils.CONTENT_TYPE;

@Configuration
public class ElasticConfig {
    @Bean
    public RestClient getRestClient() {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("", ""));
        return RestClient.builder(new HttpHost("localhost", 9200))
                .setHttpClientConfigCallback(httpClientBuilder -> {
                    httpClientBuilder.disableAuthCaching();
                    httpClientBuilder.setDefaultHeaders(List.of(
                            new BasicHeader(
                                    CONTENT_TYPE, ContentType.APPLICATION_JSON)));
                    httpClientBuilder.addInterceptorLast((HttpResponseInterceptor) (response, context) -> response
                            .addHeader("X-Elastic-Product", "Elasticsearch"));
                    return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                }).build();
    }
}
