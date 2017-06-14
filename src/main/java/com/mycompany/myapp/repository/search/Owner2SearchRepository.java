package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Owner2;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Owner2 entity.
 */
public interface Owner2SearchRepository extends ElasticsearchRepository<Owner2, Long> {
}
