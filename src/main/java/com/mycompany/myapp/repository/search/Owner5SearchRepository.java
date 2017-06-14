package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Owner5;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Owner5 entity.
 */
public interface Owner5SearchRepository extends ElasticsearchRepository<Owner5, Long> {
}
