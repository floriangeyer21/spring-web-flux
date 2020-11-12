package com.spring.flux.web.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "nodes")
public class Node {
    @Transient
    public static final String SEQUENCE_NAME = "book_sequence";

    @Id
    private Long id;
    private String nodeRoot;
}
