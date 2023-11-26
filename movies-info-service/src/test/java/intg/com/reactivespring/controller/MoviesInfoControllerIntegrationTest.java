package com.reactivespring.controller;

import com.reactivespring.MovieInfoDataFixture;
import com.reactivespring.domain.MovieInfo;
import com.reactivespring.repository.MovieInfoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class MoviesInfoControllerIntegrationTest {

    private final static String MOVIES_INFO_URL = "/v1/movieInfos";

    @Autowired
    MovieInfoRepository movieInfoRepository;

    @Autowired
    WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        movieInfoRepository.saveAll(MovieInfoDataFixture.getMovieInfos())
                .blockLast();
    }

    @AfterEach
    void tearDown() {
        movieInfoRepository.deleteAll()
                .block();
    }

    @Test
    void addMovieInfo() {
        // Arrange
        MovieInfo movieInfo = MovieInfoDataFixture.getMovieInfos().get(0);
        MovieInfo expected = new MovieInfo(null,
                movieInfo.getName() + " New!",
                movieInfo.getYear(),
                movieInfo.getCast(),
                movieInfo.getRelease_date());

        // Act & Assert
        webTestClient.post()
                .uri(MOVIES_INFO_URL)
                .bodyValue(expected)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(MovieInfo.class)
                .consumeWith(movieInfoEntityExchangeResult -> {
                    final MovieInfo actual = movieInfoEntityExchangeResult.getResponseBody();
                    assertEquals(expected.getName(), actual.getName());
                    assertNotNull(actual.getMovieInfoId());
                });
    }
}