package com.spring.flux.web.demo.controllers;

import com.spring.flux.web.demo.constants.NodeConstants;
import com.spring.flux.web.demo.domain.NodeWithDesc;
import com.spring.flux.web.demo.domain.dto.NodeWithDescRequestDto;
import com.spring.flux.web.demo.domain.dto.NodeWithDescResponseDto;
import com.spring.flux.web.demo.service.interfaces.NodeWithDescService;
import com.spring.flux.web.demo.service.mappers.NodeWithDescMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(NodeConstants.NODE_WITH_DESC_URL)
public class NodeWithDescController {
    private final NodeWithDescService nodeWithDescService;
    private final NodeWithDescMapper nodeWithDescMapper;

    public NodeWithDescController(NodeWithDescService nodeWithDescService, NodeWithDescMapper nodeWithDescMapper) {
        this.nodeWithDescService = nodeWithDescService;
        this.nodeWithDescMapper = nodeWithDescMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<NodeWithDescResponseDto> persist(
            @RequestBody NodeWithDescRequestDto nodeWithDescRequestDto) {
        NodeWithDesc node = nodeWithDescMapper.mapFromDto(nodeWithDescRequestDto);
        return nodeWithDescService.persist(node).map(nodeWithDescMapper::mapToDto);
    }

    @GetMapping
    public Flux<NodeWithDescResponseDto> getAll() {
        return nodeWithDescService.getAll()
                .map(nodeWithDescMapper::mapToDto);
    }
}
