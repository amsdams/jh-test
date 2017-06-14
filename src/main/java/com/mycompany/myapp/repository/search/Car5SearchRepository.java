package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Car5;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Car5 entity.
 */
public interface Car5SearchRepository extends ElasticsearchRepository<Car5, Long> {
}
