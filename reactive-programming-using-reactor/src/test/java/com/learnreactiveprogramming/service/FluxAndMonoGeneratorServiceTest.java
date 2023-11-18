package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class FluxAndMonoGeneratorServiceTest {
    FluxAndMonoGeneratorService target = new FluxAndMonoGeneratorService();

    @Test
    void namesFlux_noArgument_shouldReturnExpected() {
        // Arrange

        // Act
        Flux<String> actual = target.namesFlux();

        // Assert
        StepVerifier.create(actual)
                .expectNext("Alex", "Ben", "Chloe")
                .verifyComplete();
    }

}