package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class FluxAndMonoGeneratorService {
    public static void main(String[] args) {
        new FluxAndMonoGeneratorService()
                .namesFlux()
                // access the flux
                .subscribe(name -> {
                    System.out.println("Flux Name is :" + name);
                });

        new FluxAndMonoGeneratorService()
                .nameMono()
                .subscribe(name -> {
                    System.out.println("Mono Name is " + name);
                });
    }

    public Flux<String> namesFlux() {
        // simulate db or remote data source
        return Flux.fromIterable(List.of("Alex", "Ben", "Chloe")).log();
    }

    public Flux<String> namesFluxTransformation() {
        // simulate db or remote data source
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(String::toUpperCase)
                .log();
    }

    public Mono<String> nameMono() {
        return Mono.just("alex");
    }
}
