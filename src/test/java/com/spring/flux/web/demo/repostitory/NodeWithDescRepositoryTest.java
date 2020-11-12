package com.spring.flux.web.demo.repostitory;

import com.spring.flux.web.demo.domain.NodeWithDesc;
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
class NodeWithDescRepositoryTest {
    private final NodeWithDescRepository nodeWithDescRepository;

    @Autowired
    public NodeWithDescRepositoryTest(NodeWithDescRepository nodeWithDescRepository) {
        this.nodeWithDescRepository = nodeWithDescRepository;
    }

    public List<NodeWithDesc> data() {
        return Arrays.asList(new NodeWithDesc(1L, "first node root", "first node description"),
                new NodeWithDesc(2L, "second node root", "second node description"),
                new NodeWithDesc(3L, "third node root", "third node description"),
                new NodeWithDesc(4L, "fourth node root", "fourth node description"));
    }

    @Before
    public void setUp() {
        nodeWithDescRepository.deleteAll()
                .thenMany(Flux.fromIterable(data()))
                .flatMap(nodeWithDescRepository::save)
                .doOnNext((item -> {
                    System.out.println("Inserted Item is :" + item);
                }))
                .blockLast();

    }

    @Test
    public void getAllItems() {
        StepVerifier.create(nodeWithDescRepository.findAll())
                .expectSubscription()
                .expectNextCount(5)
                .verifyComplete();
    }

    @Test
    public void saveItem() {
        NodeWithDesc nodeWithDesc = new NodeWithDesc(5L, "fifth node root", "fifth node description");
        Mono<NodeWithDesc> savedItem = nodeWithDescRepository.save(nodeWithDesc);
        StepVerifier.create(savedItem.log("saveItem : "))
                .expectSubscription()
                .expectNextMatches(nodeWithDesc1 -> (nodeWithDesc1.getId() != null
                        && nodeWithDesc1.getNodeRoot().equals("fifth node root")
                        && nodeWithDesc1.getNodeDesc().equals("fifth node description")))
                .verifyComplete();
    }
}
