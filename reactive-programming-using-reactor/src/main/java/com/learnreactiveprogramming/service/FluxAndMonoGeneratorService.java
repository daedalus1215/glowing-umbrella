package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;

import java.util.List;

public class FluxAndMonoGeneratorService {
    public static void main(String[] args) {
        new FluxAndMonoGeneratorService()
                .namesFlux()
                // access the flux
                .subscribe(name -> {
                    System.out.println("Name is :" + name);
                });
    }

    public Flux<String> namesFlux() {
        // simulate db or remote data source
        return Flux.fromIterable(List.of("Alex", "Ben", "Chloe"));
    }
}
