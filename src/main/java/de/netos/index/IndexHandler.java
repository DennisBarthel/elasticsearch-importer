package de.netos.index;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.netos.client.RestClientProxy;

@Component
public class IndexHandler {
	
	@Autowired
	private RestClientProxy clientProxy;

	public void setupIndex() {
		clientProxy.deleteIndex("instruments");
		
		if (!clientProxy.indexExists("instruments")) {
			clientProxy.createIndex("instruments");
		}
	}
}
