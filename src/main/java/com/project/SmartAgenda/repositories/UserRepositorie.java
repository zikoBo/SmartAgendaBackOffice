package com.project.SmartAgenda.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.SmartAgenda.beans.User;

public interface UserRepositorie extends JpaRepository<User, Integer> {
	
	public User findByIdUser(int idUser);
	@Query("FROM User u WHERE u.login =:login AND u.password =:password")
	public User authentication(@Param("login") String login,@Param("password") String password);
	@Query("DELETE FROM Agenda a WHERE a.user.idUser=:idUser")
	public boolean deleteAgendasForUser(@Param("idUser") int idUser);

}
