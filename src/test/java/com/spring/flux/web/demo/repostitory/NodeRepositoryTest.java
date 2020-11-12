package com.spring.flux.web.demo.repostitory;

import com.spring.flux.web.demo.domain.Node;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DataMongoTest
@RunWith(SpringRunner.class)
@DirtiesContext
class NodeRepositoryTest {
    private final NodeRepository nodeService;

    @Autowired
    public NodeRepositoryTest(NodeRepository nodeService) {
        this.nodeService = nodeService;
    }

    public List<Node> data() {
        return Arrays.asList(new Node(1L, "first node root"),
                new Node(2L, "second node root"),
                new Node(3L, "third node root"),
                new Node(4L, "fourth node root"));
    }

    @Before
    public void setUp() {
        nodeService.deleteAll()
                .thenMany(Flux.fromIterable(data()))
                .flatMap(nodeService::save)
                .doOnNext((Node -> {
                    System.out.println("Inserted Node is :" + Node);
                }))
                .blockLast();

    }

    @Test
    public void getAllItems() {
        StepVerifier.create(nodeService.findAll())
                .expectSubscription()
                .expectNextCount(5)
                .verifyComplete();
    }

    @Test
    public void saveItem() {
        Node node = new Node(5L, "fifth node root");
        Mono<Node> savedItem = nodeService.save(node);
        StepVerifier.create(savedItem.log("saveItem : "))
                .expectSubscription()
                .expectNextMatches(node1 -> (node1.getId() != null && node1.getNodeRoot().equals("fifth node root")))
                .verifyComplete();
    }
}
