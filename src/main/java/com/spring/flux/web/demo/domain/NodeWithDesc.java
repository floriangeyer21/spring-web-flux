package com.spring.flux.web.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "nodes")
public class NodeWithDesc {
    @Id
    private Long id;
    private String nodeRoot;
    private String nodeDesc;
}

