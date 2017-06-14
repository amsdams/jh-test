package com.mycompany.myapp.service;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.repository.*;
import com.mycompany.myapp.repository.search.*;
import org.elasticsearch.indices.IndexAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;

@Service
public class ElasticsearchIndexService {

    private final Logger log = LoggerFactory.getLogger(ElasticsearchIndexService.class);

    private final Car0Repository car0Repository;

    private final Car0SearchRepository car0SearchRepository;

    private final Car1Repository car1Repository;

    private final Car1SearchRepository car1SearchRepository;

    private final Car2Repository car2Repository;

    private final Car2SearchRepository car2SearchRepository;

    private final Car3Repository car3Repository;

    private final Car3SearchRepository car3SearchRepository;

    private final Car4Repository car4Repository;

    private final Car4SearchRepository car4SearchRepository;

    private final Car5Repository car5Repository;

    private final Car5SearchRepository car5SearchRepository;

    private final Owner0Repository owner0Repository;

    private final Owner0SearchRepository owner0SearchRepository;

    private final Owner1Repository owner1Repository;

    private final Owner1SearchRepository owner1SearchRepository;

    private final Owner2Repository owner2Repository;

    private final Owner2SearchRepository owner2SearchRepository;

    private final Owner3Repository owner3Repository;

    private final Owner3SearchRepository owner3SearchRepository;

    private final Owner4Repository owner4Repository;

    private final Owner4SearchRepository owner4SearchRepository;

    private final Owner5Repository owner5Repository;

    private final Owner5SearchRepository owner5SearchRepository;

    private final UserRepository userRepository;

    private final UserSearchRepository userSearchRepository;

    private final ElasticsearchTemplate elasticsearchTemplate;

    public ElasticsearchIndexService(
        UserRepository userRepository,
        UserSearchRepository userSearchRepository,
        Car0Repository car0Repository,
        Car0SearchRepository car0SearchRepository,
        Car1Repository car1Repository,
        Car1SearchRepository car1SearchRepository,
        Car2Repository car2Repository,
        Car2SearchRepository car2SearchRepository,
        Car3Repository car3Repository,
        Car3SearchRepository car3SearchRepository,
        Car4Repository car4Repository,
        Car4SearchRepository car4SearchRepository,
        Car5Repository car5Repository,
        Car5SearchRepository car5SearchRepository,
        Owner0Repository owner0Repository,
        Owner0SearchRepository owner0SearchRepository,
        Owner1Repository owner1Repository,
        Owner1SearchRepository owner1SearchRepository,
        Owner2Repository owner2Repository,
        Owner2SearchRepository owner2SearchRepository,
        Owner3Repository owner3Repository,
        Owner3SearchRepository owner3SearchRepository,
        Owner4Repository owner4Repository,
        Owner4SearchRepository owner4SearchRepository,
        Owner5Repository owner5Repository,
        Owner5SearchRepository owner5SearchRepository,
        ElasticsearchTemplate elasticsearchTemplate) {
        this.userRepository = userRepository;
        this.userSearchRepository = userSearchRepository;
        this.car0Repository = car0Repository;
        this.car0SearchRepository = car0SearchRepository;
        this.car1Repository = car1Repository;
        this.car1SearchRepository = car1SearchRepository;
        this.car2Repository = car2Repository;
        this.car2SearchRepository = car2SearchRepository;
        this.car3Repository = car3Repository;
        this.car3SearchRepository = car3SearchRepository;
        this.car4Repository = car4Repository;
        this.car4SearchRepository = car4SearchRepository;
        this.car5Repository = car5Repository;
        this.car5SearchRepository = car5SearchRepository;
        this.owner0Repository = owner0Repository;
        this.owner0SearchRepository = owner0SearchRepository;
        this.owner1Repository = owner1Repository;
        this.owner1SearchRepository = owner1SearchRepository;
        this.owner2Repository = owner2Repository;
        this.owner2SearchRepository = owner2SearchRepository;
        this.owner3Repository = owner3Repository;
        this.owner3SearchRepository = owner3SearchRepository;
        this.owner4Repository = owner4Repository;
        this.owner4SearchRepository = owner4SearchRepository;
        this.owner5Repository = owner5Repository;
        this.owner5SearchRepository = owner5SearchRepository;
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Async
    @Timed
    public void reindexAll() {
        reindexForClass(Car0.class, car0Repository, car0SearchRepository);
        reindexForClass(Car1.class, car1Repository, car1SearchRepository);
        reindexForClass(Car2.class, car2Repository, car2SearchRepository);
        reindexForClass(Car3.class, car3Repository, car3SearchRepository);
        reindexForClass(Car4.class, car4Repository, car4SearchRepository);
        reindexForClass(Car5.class, car5Repository, car5SearchRepository);
        reindexForClass(Owner0.class, owner0Repository, owner0SearchRepository);
        reindexForClass(Owner1.class, owner1Repository, owner1SearchRepository);
        reindexForClass(Owner2.class, owner2Repository, owner2SearchRepository);
        reindexForClass(Owner3.class, owner3Repository, owner3SearchRepository);
        reindexForClass(Owner4.class, owner4Repository, owner4SearchRepository);
        reindexForClass(Owner5.class, owner5Repository, owner5SearchRepository);
        reindexForClass(User.class, userRepository, userSearchRepository);

        log.info("Elasticsearch: Successfully performed reindexing");
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    private <T, ID extends Serializable> void reindexForClass(Class<T> entityClass, JpaRepository<T, ID> jpaRepository,
                                                              ElasticsearchRepository<T, ID> elasticsearchRepository) {
        elasticsearchTemplate.deleteIndex(entityClass);
        try {
            elasticsearchTemplate.createIndex(entityClass);
        } catch (IndexAlreadyExistsException e) {
            // Do nothing. Index was already concurrently recreated by some other service.
        }
        elasticsearchTemplate.putMapping(entityClass);
        if (jpaRepository.count() > 0) {
            try {
                Method m = jpaRepository.getClass().getMethod("findAllWithEagerRelationships");
                elasticsearchRepository.save((List<T>) m.invoke(jpaRepository));
            } catch (Exception e) {
                elasticsearchRepository.save(jpaRepository.findAll());
            }
        }
        log.info("Elasticsearch: Indexed all rows for " + entityClass.getSimpleName());
    }
}
