package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Owner4;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Owner4 entity.
 */
public interface Owner4SearchRepository extends ElasticsearchRepository<Owner4, Long> {
}
