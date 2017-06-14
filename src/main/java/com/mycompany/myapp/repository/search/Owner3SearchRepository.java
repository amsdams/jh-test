package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Owner3;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Owner3 entity.
 */
public interface Owner3SearchRepository extends ElasticsearchRepository<Owner3, Long> {
}
