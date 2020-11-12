package com.spring.flux.web.demo.service.mappers;


import com.spring.flux.web.demo.domain.NodeWithDesc;
import com.spring.flux.web.demo.domain.dto.NodeWithDescRequestDto;
import com.spring.flux.web.demo.domain.dto.NodeWithDescResponseDto;
import org.springframework.stereotype.Component;

@Component
public class NodeWithDescMapper {

    public NodeWithDesc mapFromDto(NodeWithDescRequestDto nodeWithDescRequestDto) {
        return NodeWithDesc.builder()
                .id(nodeWithDescRequestDto.getId())
                .nodeRoot(nodeWithDescRequestDto.getNodeRoot())
                .nodeDesc(nodeWithDescRequestDto.getNodeDesc())
                .build();
    }

    public NodeWithDescResponseDto mapToDto(NodeWithDesc nodeWithDesc) {
        return NodeWithDescResponseDto.builder()
                .id(nodeWithDesc.getId())
                .nodeRoot(nodeWithDesc.getNodeRoot())
                .nodeDesc(nodeWithDesc.getNodeDesc())
                .build();
    }
}
