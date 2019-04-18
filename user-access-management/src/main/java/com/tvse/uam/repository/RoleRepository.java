package com.tvse.uam.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tvse.uam.domain.Role;
import com.tvse.uam.dto.RoleDTO;

/**
 * RoleRepository interface to declare Role related repository methods
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

	Role findByRoleName(String roleName);

	List<Role> findAll();

	Role findByRoleDisplayName(String roleDisplayName);

	@Query("select count(*) from Application app, Role r, Entitlement e, RoleEntitlementPermission rep  "
			+ "where app.id = e.applicationId and e.id = rep.entitlementId and rep.roleId = r.id "
			+ "and app.id = :applicationId and r.id = :roleId ")
	int checkRoleApplicationExists(UUID roleId, UUID applicationId);

	@Query("select count(*) from Application app, Role r, Entitlement e, RoleEntitlementPermission rep  "
			+ "where app.id = e.applicationId and e.id = rep.entitlementId and rep.roleId = r.id "
			+ "and app.id = :applicationId and r.id = :roleId and e.id = :entitlementId")
	int checkRoleApplicationEntitlementExists(UUID roleId, UUID applicationId, UUID entitlementId);

	@Query("select rep.operation from Application app, Role r, Entitlement e, RoleEntitlementPermission rep  "
			+ "where app.id = e.applicationId and e.id = rep.entitlementId and rep.roleId = r.id "
			+ "and app.id = :applicationId and r.id = :roleId and e.id = :entitlementId")
	String getOperationOfEntitlement(UUID roleId, UUID applicationId, UUID entitlementId);

	@Query("select new com.tvse.uam.dto.RoleDTO(r.roleName,r.id,r.roleDescription,r.roleDisplayName) from Application a join Entitlement e on e.applicationId = a.id and a.id "
			+ "in (:applicationId) join RoleEntitlementPermission rep "
			+ "on rep.entitlementId = e.id join Role r on r.id = rep.roleId group by r.roleName,r.id, a.id")
	List<RoleDTO> findRoleByApplicationId(@Param("applicationId") List<UUID> id);

//	@Query("select new com.tvse.uam.dto.RoleDTO(r.roleName,r.id,r.roleDescription,r.roleDisplayName) from Application a "
//			+ "join Entitlement e on e.applicationId = a.id and a.id =:applicationId join RoleEntitlementPermission rep "
//			+ "on rep.entitlementId = e.id join Role r on r.id = rep.roleId group by r.roleName, r.id")
//	List<RoleDTO> findRoleByApplicationId(@Param("applicationId") UUID id);
	
	@Query("SELECT distinct new com.tvse.uam.dto.RoleDTO(r.roleName,r.id,r.roleDescription,r.roleDisplayName) FROM Application app JOIN Entitlement e ON app.id = e.applicationId"
			+ " JOIN RoleEntitlementPermission rep ON e.id = rep.entitlementId "
			+ "JOIN Role r ON rep.roleId =r.id  where app.id=:applicationId")
	List<RoleDTO> findRoleByApplicationId(@Param("applicationId") UUID id);

	@Query("select a.id from Application a join Entitlement e on e.applicationId = a.id "
			+ "join RoleEntitlementPermission rep on rep.entitlementId = e.id and "
			+ "rep.roleId =:roleId group by a.id")
	List<UUID> findApplicationCountByRoleId(@Param("roleId") UUID roleId);

	@Query("select distinct(app.applicationName) from Application app, Role r, Entitlement e, RoleEntitlementPermission rep  "
			+ "where app.id = e.applicationId and e.id = rep.entitlementId and rep.roleId = r.id "
			+ "and r.id = :roleId")
	List<String> getApplicationsByRoleId(@Param("roleId") UUID roleID);

}
