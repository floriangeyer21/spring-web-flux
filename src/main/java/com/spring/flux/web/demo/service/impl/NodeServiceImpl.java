package com.spring.flux.web.demo.service.impl;

import com.spring.flux.web.demo.domain.Node;
import com.spring.flux.web.demo.repostitory.NodeRepository;
import com.spring.flux.web.demo.service.interfaces.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class NodeServiceImpl implements NodeService {
    private final NodeRepository nodeRepository;

    @Autowired
    public NodeServiceImpl(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    @Override
    public Mono<Node> persist(Node node) {
        return nodeRepository.save(node);
    }

    @Override
    public Flux<Node> getAll() {
        return nodeRepository.findAll();
    }
}
