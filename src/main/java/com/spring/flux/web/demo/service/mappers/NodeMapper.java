package com.spring.flux.web.demo.service.mappers;

import com.spring.flux.web.demo.domain.Node;
import com.spring.flux.web.demo.domain.dto.NodeRequestDto;
import com.spring.flux.web.demo.domain.dto.NodeResponseDto;
import org.springframework.stereotype.Component;

@Component
public class NodeMapper {

    public Node mapFromDto(NodeRequestDto nodeRequestDto) {
        return Node.builder()
                .id(nodeRequestDto.getId())
                .nodeRoot(nodeRequestDto.getNodeRoot())
                .build();
    }

    public NodeResponseDto mapToDto(Node node) {
        return NodeResponseDto.builder()
                .id(node.getId())
                .nodeRoot(node.getNodeRoot())
                .build();
    }
}
