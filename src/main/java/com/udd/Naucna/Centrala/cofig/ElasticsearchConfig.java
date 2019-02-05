package com.udd.Naucna.Centrala.cofig;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.udd.Naucna.Centrala.repository")
@ComponentScan(basePackages = {"ccom.udd.Naucna.Centrala.dto"})
public class ElasticsearchConfig {

	@Value("${elasticsearch.home:C:\\Program Files\\Java\\elasticsearch-6.4.2}")
    private String elasticsearchHome;

    @Value("${elasticsearch.cluster.name:radovi}")
    private String clusterName;

    @Bean
    public Client client() {
        try {
            final Settings elasticsearchSettings = Settings.builder()
                    .put("client.transport.sniff", true)
                    .put("path.home", elasticsearchHome)
                    .put("cluster.name", clusterName).build();
            TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));

            return client;
        } catch (UnknownHostException ex) {
            Logger.getLogger(ElasticsearchConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchTemplate(client());
    }

}