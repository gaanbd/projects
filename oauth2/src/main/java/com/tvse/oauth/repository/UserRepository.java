package com.tvse.oauth.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tvse.oauth.domain.User;

/**
 * UserRepository interface to declare User related repository methods
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {


	User findByUserName(String username);

	@Query("Select user.userName from User user where user.employeeId" + " not in (:employeeId)")
	List<String> checkExistById(@Param("employeeId") UUID employeeId);

	@Query("SELECT user FROM User user WHERE user.employeeId = :employeeId")
	User findByEmployeeId(@Param("employeeId") String employeeId);

	@Query("SELECT user FROM User user WHERE user.id = :id")
	User findbyId(@Param("id") UUID id);

	@Query("SELECT user FROM User user WHERE user.email = :email")
	User findByEmailId(@Param("email") String email);

}
