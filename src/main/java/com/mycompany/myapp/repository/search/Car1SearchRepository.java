package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Car1;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Car1 entity.
 */
public interface Car1SearchRepository extends ElasticsearchRepository<Car1, Long> {
}
