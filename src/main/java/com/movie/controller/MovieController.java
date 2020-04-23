package com.movie.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sam.model.Movie;

@Controller
public class MovieController {
 
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Autowired(required=true)
	private RestTemplate restTemplate;
	
	@GetMapping("/movie")
	public String index(Model model,@RequestParam(name = "search") String search){		
		String quote = restTemplate.getForObject(
			"http://www.omdbapi.com/?s="+search+"&apikey=", String.class);
		
		JsonParser parser = new JsonParser();

		JsonElement jsonTree = parser.parse(quote);
		
		JsonObject jsonObject = jsonTree.getAsJsonObject();
		
		JsonArray searchArray=(JsonArray) jsonObject.get("Search");
		
		ArrayList<Movie> movieList=new ArrayList<Movie>();
		
		for(int i=0;i<searchArray.size();i++){
		
			movieList.add(new Movie(
					searchArray.get(i).getAsJsonObject().get("Title").getAsString(),
					searchArray.get(i).getAsJsonObject().get("Year").getAsString(),
					searchArray.get(i).getAsJsonObject().get("imdbID").getAsString(),
					searchArray.get(i).getAsJsonObject().get("Type").getAsString(),
					searchArray.get(i).getAsJsonObject().get("Poster").getAsString()
					));
		}
		
		model.addAttribute("movielist",movieList);
		
		//return quote;
		return ("index");
	}
	
}
