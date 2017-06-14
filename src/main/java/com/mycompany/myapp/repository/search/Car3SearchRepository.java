package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Car3;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Car3 entity.
 */
public interface Car3SearchRepository extends ElasticsearchRepository<Car3, Long> {
}
