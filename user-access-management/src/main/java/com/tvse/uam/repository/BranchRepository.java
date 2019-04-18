package com.tvse.uam.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tvse.uam.domain.Branch;

/**
 * BranchRepository interface to declare Branch
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@Repository
public interface BranchRepository extends JpaRepository<Branch, UUID> {
	
	@Query("from Branch where zoneId=:zoneId")
	List<Branch> findByZoneId(UUID zoneId);
	
}
