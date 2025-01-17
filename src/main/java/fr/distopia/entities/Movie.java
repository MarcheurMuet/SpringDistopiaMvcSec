package fr.distopia.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

@Entity
public class Movie implements Serializable {	
	private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMovie;
    private String title;
    private int duration;
    private String description;
    private int place;
    private int price;
    private String name; 

    @ManyToOne
    @JoinColumn(name = "movie_theater_id")
    private MovieTheater movieTheater;

    public Movie(int idMovie, String title, int duration, String description, int place, int price, String name) {
        this.setTitle(title);
        this.setDuration(duration);
        this.setDescription(description);
        this.setPlace(place);
        this.setPrice(price);
        this.setName(name);
    }

    // Add getter and setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public MovieTheater getMovieTheater() {
        return movieTheater;
    }

    public void setMovieTheater(MovieTheater movieTheater) {
        this.movieTheater = movieTheater;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Object getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
    }

    public int getPrice() {
    return price;
    }

    private void setPrice(int price) {
        this.price = price;
    }
}