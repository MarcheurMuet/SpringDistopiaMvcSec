package fr.distopia.business;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import fr.distopia.entities.Movie;
import fr.distopia.entities.MovieTheater;

@Service
public interface IBusiness {
	/**
	 * Méthode qui renvoi la liste des Movies en base
	 * @return List<Movie> 
	 * @throws Exception
	 */
	public List<Movie> getMovies() throws Exception;
	/**
	 * Méthode qui renvoi la liste des Movies en base paginée
	 * @param kw est un mot dont on souhaite afficher tous les Movies le contenant 
	 * @param page correspond à la page active côté front, cela assure la pagination
	 * @return Page<Movie>
	 * @throws Exception
	 */
	public Page<Movie> getMoviesPages(String kw, int page) throws Exception;
	/**
	 * Méthode qui renvoi tous les Movies d'une catégorie en base paginée
	 * @param idCat correspond à l'identifiant de la catégorie choisie
	 * @param page correspond à la page active côté front, cela assure la pagination
	 * @return Page<Movie>
	 * @throws Exception
	 */
	public Page<Movie> getMoviesByMovieTheaterPage(Long idCat, int page) throws Exception;
	/**
	 * Méthode qui sauvegarde un Movie en base
	 * @param movie
	 * @throws Exception
	 */
	public void saveMovie(Movie movie) throws Exception;
	/**
	 * Méthode qui renvoi un movie en base à partir de son id
	 * @param id
	 * @return Movie 
	 * @throws Exception
	 */
	public Movie getMovieById(Long id) throws Exception;
	/**
	 * Méthode qui supprime un movie à partir de son id
	 * @param id
	 * @throws Exception
	 */
	public void deleteMovie(Long id) throws Exception;
	/**
	 * Méthode qui renvoi la liste des catégories en base
	 * @return
	 * @throws Exception
	 */
	public List<MovieTheater> getMovieTheater() throws Exception;
	
	/**
	 * Méthode qui ajoute un movie au panier
	 * @param movie
	 */
	public void addMovToCart(Movie movie);	
	/**
	 * Méthode qui supprime un movie du panier à partir d'un id (s'il existe)
	 * @param id
	 */
	public void delMovFromCart(Long id);		
	/**
	 * Méthode qui supprime le panier en cours
	 */
	public void delCart();						
	/**
	 * Méthode qui renvoi le contenu du panier
	 * @return List<Movie>
	 */
	public List<Movie> getCart();				
	/**
	 * Méthode qui renvoi le nombre d'éléments dans le panier en cours
	 * @return int
	 */
	public int getNbCart();						
}
