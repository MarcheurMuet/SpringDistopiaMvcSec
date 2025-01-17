package fr.distopia.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

//import javax.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import fr.distopia.dao.MovieRepository;
import fr.distopia.dao.MovieTheaterRepository;
import fr.distopia.entities.Movie;
import fr.distopia.entities.MovieTheater;
import fr.distopia.entities.User;

@Service
public class IBusinessImpl implements IBusiness {
	@Autowired
	MovieRepository movieRepository;

	@Autowired
	MovieTheaterRepository movietheaterRepository;
	
	private HashMap<Long, Movie> cart;
	@Getter
    @Setter
    private User user;
	
	public IBusinessImpl() {
		cart = new HashMap<>();
		user = null;
	}

	@Override
	public void addMovToCart(Movie movie) {	
		Movie a = cart.get(movie.getIdMovie()); 
		if(a != null) {		
			a.setPlace(a.getPlace()+1);
		}
		else cart.put((long) movie.getIdMovie(), movie);		
	}

	@Override
	public void delMovFromCart(Long id) {
		cart.remove(id);		
	}

	@Override
	public void delCart() {
		cart.clear();		
	}

	@Override
	public List<Movie> getCart() {
		return new ArrayList<>(cart.values());
	}

	public double getTotalAmount() {
		double total = 0;
		for(Movie movie : cart.values()) {
			total += movie.getPrice() * movie.getPlace();
		}
		return total;
	}
	
	public boolean isEmpty() {
		return cart.isEmpty();
	}

    @Override
	public int getNbCart() {
		return cart.size();
	}
	
	@Override
	public List<Movie> getMovies() throws Exception {
		return movieRepository.findAll();
	}
	
	@Override
	public Movie getMovieById(Long id) throws Exception {
		Optional<Movie> optional = movieRepository.findById(id.intValue());
		return optional.isPresent() ? optional.get() : null;
	}
	
	@Override
	public void saveMovie(Movie movie) throws Exception {
		movieRepository.save(movie);		
	}
	
	@Override
	public Page<Movie> getMoviesByMovieTheaterPage(Long idCat, int page) throws Exception {
		return movieRepository.findByMovieTheaterId(idCat, PageRequest.of(page, 5));
	}

	@Override
	public Page<Movie> getMoviesPages(String kw, int page) throws Exception {		
		return movieRepository.findByDescriptionContains(kw , PageRequest.of(page, 5));
	}

	@Override
	public void deleteMovie(Long id) throws Exception {
		movieRepository.deleteById(id);			
	}
	
	public List<MovieTheater> getMovieTheaters() throws Exception {
		return movietheaterRepository.findAll();
	}
	
	public MovieTheater getMovieTheaterById(Long id) throws Exception {
		return movietheaterRepository.getById(id);
	}

	public void order() {
		try {
			Thread.sleep(500);
		} catch (Exception e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}
	
	public String great() {
		return "Hello World";
	}

	@Override
	public List<MovieTheater> getMovieTheater() throws Exception {
		return movietheaterRepository.findByName("Name");
	}
}
