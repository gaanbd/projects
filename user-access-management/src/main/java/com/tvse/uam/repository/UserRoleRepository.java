package com.tvse.uam.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tvse.uam.domain.UserRole;
import com.tvse.uam.dto.UserRoleDTO;

/**
 * UserRoleRepository interface to declare UserRole related repository methods
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {

	@Query("from UserRole where userId=:userId")
	List<UserRole> findByUserId(UUID userId);

	@Query(value = "select u.roleId as roleId,u.userId as userId,group_concat(u.brandId) as brandIds from UserRole u   "
			+ "where u.userId =:userId and u.roleId=:roleId group by u.roleId,u.userId", nativeQuery = true)
	List<Object> getUserRole(@Param("userId") String userId, @Param("roleId") String roleId);

	@Query("select new com.tvse.uam.dto.UserRoleDTO(u.userId,u.roleId) from UserRole u   "
			+ "where u.userId = :userId group by u.userId,u.roleId")
	List<UserRoleDTO> getUserRoleByUserId(UUID userId);

	@Query("select count(id) from UserRole where userId=:userId and roleId=:roleId and brandId=:brandId")
	Long checkUserRoleDetails(UUID userId, UUID roleId, UUID brandId);

	@Query("select count(id) from UserRole where userId=:userId and roleId=:roleId and brandId=:brandId and id not in (:id)")
	Long checkUserRoleDetailsById(UUID userId, UUID roleId, UUID brandId, UUID id);

	@Query("select count(id) from UserRole where userId=:userId and roleId=:roleId")
	Long checkAppUserRoleDetails(UUID userId, UUID roleId);

	@Query("select count(id) from UserRole where userId=:userId and roleId=:roleId and id not in (:id)")
	Long checkAppUserRoleDetailsById(UUID userId, UUID roleId, UUID id);

	void deleteByRoleId(UUID roleId);

	@Query("from UserRole where userId=:userId and roleId=:roleId and brandId=:brandId")
	UserRole checkBrandExists(UUID userId, UUID roleId, UUID brandId);

	@Modifying
	@Query("delete from UserRole where brandId in (:brandIds)")
	void deleteUserRoleByBrandId(@Param("brandIds") List<UUID> brandIds);

	void deleteByUserId(UUID userId);

	@Query("select count(id) from UserRole where roleId=:roleId")
	Long checkUserRoleByRoleId(UUID roleId);

}
