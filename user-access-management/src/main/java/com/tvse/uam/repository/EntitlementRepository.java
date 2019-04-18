package com.tvse.uam.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tvse.uam.domain.Entitlement;

/**
 * EntitlementRepository interface to declare Entitlement related repository methods
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@Repository
public interface EntitlementRepository extends JpaRepository<Entitlement, UUID> {

	List<Entitlement> findByApplicationId(UUID applicationId);
}