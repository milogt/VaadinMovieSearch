package com.example.application.repository;

import com.example.application.models.ItunesResponse;
import com.example.application.service.ResponseCallback;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.util.MimeType;
import java.nio.charset.StandardCharsets;
import com.fasterxml.jackson.databind.ObjectMapper;



import javax.print.attribute.standard.Media;

@Repository
public class MovieRepository {

    private final String BASE = "https://itunes.apple.com/search?media=movie&term=%s&limit=%d&offset=%d";
    private ExchangeStrategies strategies = ExchangeStrategies.builder().codecs(clientCodecConfigurer ->
            clientCodecConfigurer.customCodecs().decoder(
                    new Jackson2JsonDecoder(new ObjectMapper(),
                            new MimeType("text", "javascript", StandardCharsets.UTF_8)))
    ).build();

    private WebClient webClient = WebClient.builder().exchangeStrategies(strategies).build();

    public void getMoviesPaged(ResponseCallback<ItunesResponse> callback, String search, int maxResults,
                              int startIndex) {

        String formatted = String.format(BASE, search, maxResults, startIndex);

        WebClient.RequestHeadersSpec<?> spec = this.webClient.get().uri(formatted);

        spec
                .retrieve().toEntity(ItunesResponse.class).subscribe(result -> {

            final ItunesResponse volumesResponse = result.getBody();
            if (null == volumesResponse || null == volumesResponse.getResults()) return;
//            System.out.println(volumesResponse.getResults().get(0).getTrackName());
            callback.operationFinished(volumesResponse);

        });

    }

}
