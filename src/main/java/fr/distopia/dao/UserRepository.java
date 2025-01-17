package fr.distopia.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.distopia.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
