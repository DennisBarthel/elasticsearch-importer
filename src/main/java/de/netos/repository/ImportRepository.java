package de.netos.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import de.netos.model.Instrument;

@Repository
public interface ImportRepository extends ElasticsearchRepository<Instrument, String> {

}
