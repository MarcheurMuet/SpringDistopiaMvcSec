package fr.distopia.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.distopia.entities.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
	public List<Movie> findByName(String name);
	public List<Movie> findByNameContains(String name);
	public List<Movie> findByNameAndPrice(String name, double price);
	public List<Movie> findByNameContainsAndPriceGreaterThan(String name, double price);
	
	@Query("select A from Movie A where A.name like %:x% and A.price > :y")
	public List<Movie> searchMovies(@Param("x")String name,@Param("y")double price);
	
	@Query("select A from Movie A where A.id= :id")
	public Movie findOne(@Param("id")long id);
	
	@Modifying
	@Transactional
	@Query("update Movie a set a.price= :p where a.id= :id")
	public void update(@Param("p")double price,@Param("id")long id);
	
	public List<Movie> findByDescriptionAndName(String description, String name);
	
	public List<Movie> findByCategoryId(Long categoryId);
	
	@SuppressWarnings("null")
	public Page<Movie> findAll(Pageable pageable);
	
	public Page<Movie> findByDescriptionContains(String description , Pageable pageable);
	public Page<Movie> findByCategoryId(Long categoryId , Pageable pageable);
    public Page<Movie> findByMovieTheaterId(Long idCat, PageRequest of);
    
    List<Movie> findByTitleAndSessionPrice(String title, double sessionPrice);
    
    List<Movie> findByDescriptionAndTitle(String description, String title);
    public void deleteById(Long id);
}
