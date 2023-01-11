package com.example.elasticsearch.es8poc.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ElasticSearchConfig {


    @Value("${elasticsearch.host}")
    private String host;

    @Value("${elasticsearch.port}")
    private int port;

    @PostConstruct
    private void init() {
        log.info("Elastic server {} on port", host, port);
    }


    @Bean
    public RestClient restClient() {
        RestClient restClient = RestClient.builder(
                new HttpHost(host, port)).build();
        return restClient;
    }

    @Bean
    ElasticsearchTransport elasticsearchTransport(RestClient restClient) {
        // Create the transport with a Jackson mapper
        ElasticsearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper());
        return transport;
    }

    @Bean
    ElasticsearchClient elasticsearchClient(ElasticsearchTransport elasticsearchTransport) {
        // And create the API client
        ElasticsearchClient client = new ElasticsearchClient(elasticsearchTransport);
        return client;
    }

}
