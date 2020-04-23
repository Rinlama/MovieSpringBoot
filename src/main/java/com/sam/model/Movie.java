package com.sam.model;

public class Movie {
	
	public String Title;
	public String Year;
	public String imdbID;
	public String Type;
	public String Poster;
	public Movie(String title, String year, String imdbID, String type, String poster) {
		super();
		Title = title;
		Year = year;
		this.imdbID = imdbID;
		Type = type;
		Poster = poster;
	}
	
}
