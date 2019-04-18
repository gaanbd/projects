package com.tvse.uam.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tvse.uam.domain.User;

/**
 * UserRepository interface to declare User related repository methods
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

	User findByUserName(String username);

	@Query("select user from User user where user.employeeId = :employeeId")
	User findByEmployeeId(@Param("employeeId") String employeeId);

	@Query("select user from User user where user.id = :id")
	User findbyId(@Param("id") UUID id);

	@Modifying
	@Query("update User set isActive=:isActive,updatedBy=:updatedBy where id=:id")
	void updateUserStatus(@Param("id") UUID id, @Param("updatedBy") UUID updatedBy,
			@Param("isActive") Boolean isActive);

	@Query("select users from User users")
	Page<User> getAllUsers(Pageable pageableRequest);

	@Query("select distinct role.roleName from User user,Role role,UserRole userRole,Brand brand where (:id) = userRole.userId and role.id = userRole.roleId")
	List<String> getUserRoles(@Param("id") UUID id);

	@Query("select distinct brand.brandName from User user,Role role,UserRole userRole,Brand brand where (:id) = userRole.userId and role.id = userRole.roleId and userRole.brandId=brand.id")
	List<String> getUserBrands(@Param("id") UUID id);

}
