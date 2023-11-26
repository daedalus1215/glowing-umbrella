package com.reactivespring;

import com.reactivespring.domain.MovieInfo;

import java.time.LocalDate;
import java.util.List;

import static java.util.List.of;

public class MovieInfoDataFixture {
    public static List<MovieInfo> getMovieInfos() {
        return of(new MovieInfo(null,
                        "Batman Begins",
                        2005,
                        of("Christian Bale", "Michael Cane"),
                        LocalDate.parse("2005-06-15")),
                new MovieInfo(null,
                        "The Dark Knight",
                        2008,
                        of("Christian Bale", "Michael Cane"),
                        LocalDate.parse("2008-06-15")),
                new MovieInfo("abc",
                        "Dark Knight Rises",
                        2012,
                        of("Christian Bale", "Michael Cane"),
                        LocalDate.parse("2012-06-15")));
    }
}
