package com.tvse.uam.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.tvse.uam.domain.Menu;
import com.tvse.uam.dto.MenuDTO;

/**
 * MenuRepository interface to declare Menu related repository methods
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */

@Repository
public interface MenuRepository extends JpaRepository<Menu, UUID> {

	@Query("SELECT new com.tvse.uam.dto.MenuDTO(menuAlias.id,menuAlias.menuName,menuAlias.menuKey,menuAlias.menuIcon,menuAlias.parentId,"
			+ "menuAlias.entitlementId,menuAlias.sequenceNumber, entitleAlias.apiUrl,entitleAlias.routerUrl) from Menu menuAlias LEFT JOIN Entitlement entitleAlias "
			+ " ON menuAlias.entitlementId=entitleAlias.id order by menuAlias.parentId,menuAlias.sequenceNumber")
	List<MenuDTO> getAllMenus();

}