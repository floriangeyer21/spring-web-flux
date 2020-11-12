package com.spring.flux.web.demo.repostitory;

import com.spring.flux.web.demo.domain.Node;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeRepository extends ReactiveMongoRepository<Node, Long> {
}
