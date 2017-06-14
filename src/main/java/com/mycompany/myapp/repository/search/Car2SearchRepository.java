package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Car2;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Car2 entity.
 */
public interface Car2SearchRepository extends ElasticsearchRepository<Car2, Long> {
}
