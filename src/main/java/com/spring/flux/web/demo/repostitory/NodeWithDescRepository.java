package com.spring.flux.web.demo.repostitory;

import com.spring.flux.web.demo.domain.NodeWithDesc;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeWithDescRepository
        extends ReactiveMongoRepository<NodeWithDesc, Long> {
}
