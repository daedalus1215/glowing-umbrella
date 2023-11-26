package com.reactivespring.controller;

import com.reactivespring.MovieInfoDataFixture;
import com.reactivespring.domain.MovieInfo;
import com.reactivespring.services.MovieInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.when;

@WebFluxTest(controllers = MoviesInfoController.class)
@AutoConfigureWebTestClient
public class MovieInfoControllerUnitTest {
    private final static String MOVIES_INFO_URL = "/v1/movieInfos";

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private MovieInfoService movieInfoServiceMock;

    @Test
    void getAllMoviesInfo() {
        // Arrange
        when(movieInfoServiceMock.getAllMovieInfos())
                .thenReturn(Flux.fromIterable(MovieInfoDataFixture.getMovieInfos()));

        // Act & Assert
        webTestClient.get()
                .uri(MOVIES_INFO_URL)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(MovieInfo.class);
    }
}
