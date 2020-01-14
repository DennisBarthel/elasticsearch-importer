package de.netos.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "de.netos.repository")
public class ElasticSearchConfig {

	@Bean(destroyMethod = "close")
	public RestHighLevelClient client() {
		ClientConfiguration configuration = ClientConfiguration.builder().connectedToLocalhost().build();
	
		return RestClients.create(configuration).rest();
	}
}
