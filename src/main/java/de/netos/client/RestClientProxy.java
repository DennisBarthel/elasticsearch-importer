package de.netos.client;

import java.io.IOException;
import java.util.Arrays;

import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest.AliasActions;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.ElasticsearchException;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RestClientProxy {

	@Autowired
	private RestHighLevelClient client;
	
	public boolean indexExists(String indexName) {
		GetIndexRequest request = new GetIndexRequest(indexName);
		
		try {
			return client.indices().exists(request, RequestOptions.DEFAULT);
		} catch (IOException e) {
			log.error("Error while checking existance of index {}", indexName, e);
			throw new ElasticsearchException("Error while for indexExists request: " + request.toString(), e);
		}
	}
	
	public boolean createIndex(String indexName) {
		CreateIndexRequest request = new CreateIndexRequest(indexName);
		
		try {
			return client.indices().create(request, RequestOptions.DEFAULT).isAcknowledged();
		} catch (IOException e) {
			throw new ElasticsearchException("Error while for createIndex request: " + request.toString(), e);
		}
	}

	public boolean deleteIndex(String indexName) {
		DeleteIndexRequest request = new DeleteIndexRequest(indexName);

		try {
			return client.indices().delete(request, RequestOptions.DEFAULT).isAcknowledged();
		} catch (IOException e) {
			log.error("Error while deleting index {}", indexName, e);
		}
		
		return false;
	}
	
	public boolean updateAlias(AliasActions... actions) {
		IndicesAliasesRequest request = new IndicesAliasesRequest();
		
		Arrays.stream(actions).forEach(request::addAliasAction);
		
		try {
			return client.indices().updateAliases(request, RequestOptions.DEFAULT).isAcknowledged();
		} catch (IOException e) {
			log.error("Error while updating alias: {}", request.toString(), e);
			throw new ElasticsearchException("Error while for updateAlias request: " + request.toString(), e);
		}
	}
}
