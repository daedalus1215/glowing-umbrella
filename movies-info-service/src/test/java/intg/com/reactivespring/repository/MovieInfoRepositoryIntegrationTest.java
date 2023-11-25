package com.reactivespring.repository;

import com.reactivespring.domain.MovieInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

import static java.util.List.of;

// used to scan our app and locate repo classes and make them available. So we do not need the entire Spring Context.
@DataMongoTest
@ActiveProfiles("test")
class MovieInfoRepositoryIntegrationTest {
    @Autowired
    MovieInfoRepository target;

    @BeforeEach
    void setUp() {
        target.saveAll(of(new MovieInfo(null,
                                "Batman Begins",
                                2005,
                                of("Christian Bale", "Michael Cane"),
                                LocalDate.parse("2005-06-15")),
                        new MovieInfo(null,
                                "The Dark Knight",
                                2008,
                                of("Christian Bale", "Michael Cane"),
                                LocalDate.parse("2008-06-15")),
                        new MovieInfo(null,
                                "Dark Knight Rises",
                                2012,
                                of("Christian Bale", "Michael Cane"),
                                LocalDate.parse("2012-06-15"))))
                .blockLast();
    }

    @AfterEach
    void tearDown() {
        target.deleteAll()
                .block();
    }

    @Test
    void findAll_noArg_willReturnExpectedFlux() {
        // Arrange

        // Act
        Flux<MovieInfo> actual = target.findAll();

        // Assert

    }
}