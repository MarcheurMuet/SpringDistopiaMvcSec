package fr.distopia.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.distopia.entities.SessionList;

public interface SessionListRepository extends JpaRepository<SessionList, Long> {

}
