package com.spring.flux.web.demo.service.interfaces;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GenericService<T> {

    Mono<T> persist(T t);

    Flux<T> getAll();
}
