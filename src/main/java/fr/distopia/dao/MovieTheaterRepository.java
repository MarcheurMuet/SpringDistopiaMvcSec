package fr.distopia.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.distopia.entities.MovieTheater;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface MovieTheaterRepository extends JpaRepository<MovieTheater,Long> {
    public List<MovieTheater> findByName(String name);
    @SuppressWarnings("null")
    public Optional<MovieTheater> findById(Long id);
    public List<MovieTheater> findAllByOrderByNameDesc();

    @Transactional
    @Modifying
    @Query("update MovieTheater c set c.name = ?1 where c.id = ?2")
    public void saveById(String name, Long id);
}
