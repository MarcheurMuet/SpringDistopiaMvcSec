package fr.distopia.job;

import fr.distopia.dao.MovieRepository;
import fr.distopia.dao.MovieTheaterRepository;
import fr.distopia.entities.Movie;
import fr.distopia.entities.MovieTheater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DevelopmentTools {
    @Autowired
    private MovieTheaterRepository movietheaterRepository;

    @Autowired
    private MovieRepository movieRepository;

    // Remplit la base de données movies de test.
    public void fillDatabase() {
        movieRepository.deleteAll();
        movietheaterRepository.deleteAll();

        MovieTheater Gaumont = new MovieTheater(1, "Gaumont", null);
        MovieTheater Pate = new MovieTheater(2, "Pate", null);
        MovieTheater Universalisse = new MovieTheater(3, "Universalisse", null);

        movietheaterRepository.save(Gaumont);
        movietheaterRepository.save(Pate);
        movietheaterRepository.save(Universalisse);

        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie(1, "Venom", 120, "Action movie", 48, 17, null));
        movies.add(new Movie(2, "Spiderman 3", 180, "-6",120,18,null));
        movies.add(new Movie(3, "Super Mario Bros", 160, "-3",90,16,null));
        movies.add(new Movie(4, "Pandora", 165, "-16",75,17,null));
        movies.add(new Movie(5, "Mr Nobody", 175, "-12",130,17,null));
        movies.add(new Movie(6, "Interstelar", 185, "-12",25,19,null));
        movies.add(new Movie(7, "Timbleback", 130, "-8",55,17,null));
        movies.add(new Movie(8, "Cadaveroom", 135, "-16",31,17,null));
        movies.add(new Movie(9, "Sonic", 120, "0",88,17,null));
        movies.add(new Movie(10, "Witcher", 110, "-16",215,15,null));

        movieRepository.saveAll(movies);
    }
}
