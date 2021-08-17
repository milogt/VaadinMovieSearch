package com.example.application.service;

import com.example.application.models.ItunesResponse;
import com.example.application.repository.MovieRepository;
import org.springframework.stereotype.Service;


@Service
public class MovieService {

    public static final int MAX_RESULTS = 20;
    private MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }


    public void getMoviesPaged(ResponseCallback<ItunesResponse> callback, String search, int maxResults,
                              int startIndex) {

        System.out.println("fetching movies -> " + startIndex + " to "
                + (startIndex + MAX_RESULTS - 1));

        movieRepository.getMoviesPaged(callback, search, maxResults, startIndex);

    }

}
