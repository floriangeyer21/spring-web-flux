package com.spring.flux.web.demo.controllers;

import static org.junit.Assert.assertTrue;

import com.spring.flux.web.demo.constants.NodeConstants;
import com.spring.flux.web.demo.domain.NodeWithDesc;
import com.spring.flux.web.demo.domain.dto.NodeWithDescResponseDto;
import com.spring.flux.web.demo.repostitory.NodeWithDescRepository;
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
public class NodeWithDescControllerTest {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private NodeWithDescRepository nodeWithDescRepository;


    public List<NodeWithDesc> data() {
        return Arrays.asList(new NodeWithDesc(1L, "first node root", "first node description"),
                new NodeWithDesc(2L, "second node root", "second node description"),
                new NodeWithDesc(3L, "third node root", "third node description"),
                new NodeWithDesc(4L, "fourth node root", "fourth node description"));
    }

    @Before
    public void setUp(){
        nodeWithDescRepository.deleteAll()
                .thenMany(Flux.fromIterable(data()))
                .flatMap(nodeWithDescRepository::save)
                .doOnNext((nodeWithDesc -> {
                    System.out.println("Inserted node with description is : " + nodeWithDesc);
                }))
                .blockLast();
    }

    @Test
    public void getAllItems(){
        webTestClient.get().uri(NodeConstants.NODE_WITH_DESC_URL)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(NodeWithDescResponseDto.class)
                .hasSize(4);

    }

    @Test
    public void getAllItems_approach2(){
        webTestClient.get().uri(NodeConstants.NODE_WITH_DESC_URL)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(NodeWithDescResponseDto.class)
                .hasSize(4)
                .consumeWith((response) -> {
                    List<NodeWithDescResponseDto> items =  response.getResponseBody();
                    items.forEach((nodeWithDesc) -> {
                        assertTrue(nodeWithDesc.getId()!=null);
                    });

                });
    }

    @Test
    public void getAllItems_approach3(){
        Flux<NodeWithDescResponseDto> itemsFlux = webTestClient.get().uri(NodeConstants.NODE_WITH_DESC_URL)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult(NodeWithDescResponseDto.class)
                .getResponseBody();
        StepVerifier.create(itemsFlux.log("value from network : "))
                .expectNextCount(4)
                .verifyComplete();
    }

    @Test
    public void createItem(){
        NodeWithDesc nodeWithDesc = new NodeWithDesc(5L, "fifth node root", "fifth node description");
        webTestClient.post().uri(NodeConstants.NODE_WITH_DESC_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(nodeWithDesc), NodeWithDescResponseDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo(5L)
                .jsonPath("$.nodeRoot").isEqualTo("fifth node root")
                .jsonPath("$.nodeDesc").isEqualTo("fifth node description");
    }
}
