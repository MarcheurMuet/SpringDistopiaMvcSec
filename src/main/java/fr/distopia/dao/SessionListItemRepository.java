package fr.distopia.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.distopia.entities.SessionList;

public interface SessionListItemRepository extends JpaRepository<SessionList, Long>{

}
