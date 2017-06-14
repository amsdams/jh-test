package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Owner1;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Owner1 entity.
 */
public interface Owner1SearchRepository extends ElasticsearchRepository<Owner1, Long> {
}
