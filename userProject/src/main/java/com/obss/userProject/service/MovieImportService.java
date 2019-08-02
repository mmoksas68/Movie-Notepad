package com.obss.userProject.service;

import com.obss.userProject.classes.Movie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class MovieImportService {
    @Value("${api.key}")
    private String apiKey;

    public Movie importMovie(String title){

        String[] search = title.split(" ");
        String urlMaker = "http://www.omdbapi.com/?t=";
        for(String titleParts: search)
            urlMaker += titleParts + "+";

        urlMaker += apiKey;

        Movie movie = new Movie();

        try{
            RestTemplate restTemplate = new RestTemplate();
            String resp = restTemplate.getForObject(urlMaker, String.class);

            JsonParser springParser = JsonParserFactory.getJsonParser();
            Map<String, Object> map = springParser.parseMap(resp);

            movie.setDirector((String) map.get("Director"));
            movie.setGenre((String) map.get("Genre"));
            movie.setTitle((String) map.get("Title"));
            movie.setRuntime((String) map.get("Runtime"));
            movie.setReleased((String) map.get("Released"));
            movie.setPoster((String) map.get("Poster"));
            if(!map.get("imdbRating").equals("N/A"))
                movie.setImdbRating(Float.parseFloat((String)map.get("imdbRating")));
            else
                movie.setImdbRating(new Float(0));
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        if(movie.getTitle() != null)
            return movie;
        else
            return null;
    }
}
