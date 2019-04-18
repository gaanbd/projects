package com.tvse.uam.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tvse.uam.domain.Application;
import com.tvse.uam.dto.ApplicationDTO;

/**
 * ApplicationRepository interface to declare Application related repository
 * methods
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@Repository
public interface ApplicationRepository extends JpaRepository<Application, UUID> {

	@Query("select distinct new com.tvse.uam.dto.ApplicationDTO(app.id,app.applicationName,app.applicationDescription) from Application app, Role r, Entitlement e, RoleEntitlementPermission rep  "
			+ "where app.id = e.applicationId and e.id = rep.entitlementId and rep.roleId = r.id and r.id = :roleId")
	List<ApplicationDTO> getApplicationsByRoleId(@Param("roleId") UUID roleID);

}
