package com.tvse.uam.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tvse.uam.domain.RoleEntitlementPermission;

/**
 * RoleEntitlementPermissionRepository interface to declare RoleEntitlementPermission related repository
 * methods
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@Repository
public interface RoleEntitlementPermissionRepository extends JpaRepository<RoleEntitlementPermission, UUID> {

	void deleteByRoleId(UUID roleId);
}
	