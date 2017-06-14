package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Car0;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Car0 entity.
 */
public interface Car0SearchRepository extends ElasticsearchRepository<Car0, Long> {
}
