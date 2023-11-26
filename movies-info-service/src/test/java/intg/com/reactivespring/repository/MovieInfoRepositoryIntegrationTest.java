package com.reactivespring.repository;

import com.reactivespring.MovieInfoDataFixture;
import com.reactivespring.domain.MovieInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.assertEquals;

// used to scan our app and locate repo classes and make them available. So we do not need the entire Spring Context.
@DataMongoTest
@ActiveProfiles("test")
class MovieInfoRepositoryIntegrationTest {
    @Autowired
    MovieInfoRepository target;

    @BeforeEach
    void setUp() {
        target.saveAll(MovieInfoDataFixture.getMovieInfos())
                .blockLast();
    }

    @AfterEach
    void tearDown() {
        target.deleteAll()
                .block();
    }

    @Test
    void findAll_noArg_willReturnExpectedFlux() {
        // Arrange & Act
        Flux<MovieInfo> actual = target.findAll();

        // Assert
        StepVerifier.create(actual)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void findById_withDarkKnightRisesId_willReturnDarkKnightRisesMono() {
        // Arrange & Act
        Mono<MovieInfo> actual = target.findById("abc")
                .log();

        // Assert
        StepVerifier.create(actual)
                .assertNext(movieInfo -> assertEquals("Dark Knight Rises", movieInfo.getName()))
                .verifyComplete();
    }

    @Test
    void saveMovieInfo_withNewMovieInfo_willReturnTheSavedMovieInfo() {
        // Arrange
        final MovieInfo expectedMovieInfo = new MovieInfo(null,
                "Batman Begins expectedOne",
                2005,
                of("Christian Bale", "Michael Cane"),
                LocalDate.parse("2005-06-15"));

        // Act
        Mono<MovieInfo> actual = target.save(expectedMovieInfo)
                .log();

        // Assert
        StepVerifier.create(actual)
                .assertNext(movieInfo -> assertEquals(expectedMovieInfo.getName(), movieInfo.getName()))
                .verifyComplete();
    }

    @Test
    void saveMovieInfo_withUpdatedMovieInfo_willReturnTheSavedMovieInfo() {
        // Arrange
        final MovieInfo expected = target.findById("abc").block();
        expected.setYear(2021);

        // Act
        Mono<MovieInfo> actual = target.save(expected)
                .log();

        // Assert
        StepVerifier.create(actual)
                .assertNext(movieInfo -> assertEquals(expected.getYear(), movieInfo.getYear()))
                .verifyComplete();
    }

    @Test
    void deleteById_withId_willDeleteMovieInfo() {
        // Arrange & Act
        target.deleteById("abc")
                .block();
        final Flux<MovieInfo> actual = target.findAll();

        // Assert
        StepVerifier.create(actual)
                .expectNextCount(2)
                .verifyComplete();
    }
}