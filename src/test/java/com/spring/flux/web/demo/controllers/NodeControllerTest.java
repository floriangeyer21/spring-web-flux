package com.spring.flux.web.demo.controllers;

import static org.junit.Assert.assertTrue;

import com.spring.flux.web.demo.constants.NodeConstants;
import com.spring.flux.web.demo.domain.Node;
import com.spring.flux.web.demo.domain.dto.NodeResponseDto;
import com.spring.flux.web.demo.repostitory.NodeRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class NodeControllerTest {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private NodeRepository nodeRepository;


    public List<Node> data() {
        return Arrays.asList(new Node(1L, "first node root"),
                new Node(2L, "second node root"),
                new Node(3L, "third node root"),
                new Node(4L, "fourth node root"));
    }

    @Before
    public void setUp(){
        nodeRepository.deleteAll()
                .thenMany(Flux.fromIterable(data()))
                .flatMap(nodeRepository::save)
                .doOnNext((node -> {
                    System.out.println("Inserted node is : " + node);
                }))
                .blockLast();
    }

    @Test
    public void getAllItems(){
        webTestClient.get().uri(NodeConstants.NODE_URL)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(NodeResponseDto.class)
                .hasSize(4);

    }

    @Test
    public void getAllItems_approach2(){
        webTestClient.get().uri(NodeConstants.NODE_URL)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Node.class)
                .hasSize(4)
                .consumeWith((response) -> {
                    List<Node> nodes =  response.getResponseBody();
                    nodes.forEach((node) -> {
                        assertTrue(node.getId()!=null);
                    });

                });
    }

    @Test
    public void getAllItems_approach3(){
        Flux<Node> nodeFlux = webTestClient.get().uri(NodeConstants.NODE_URL)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult(Node.class)
                .getResponseBody();
        StepVerifier.create(nodeFlux.log("value from network : "))
                .expectNextCount(4)
                .verifyComplete();
    }

    @Test
    public void createItem(){
        Node item = new Node(5L, "fifth node root");
        webTestClient.post().uri(NodeConstants.NODE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(item), NodeResponseDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo(5L)
                .jsonPath("$.nodeRoot").isEqualTo("fifth node root");
    }
}
