package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Car4;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Car4 entity.
 */
public interface Car4SearchRepository extends ElasticsearchRepository<Car4, Long> {
}
