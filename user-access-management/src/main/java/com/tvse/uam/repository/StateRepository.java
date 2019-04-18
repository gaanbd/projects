package com.tvse.uam.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tvse.uam.domain.State;

/**
 * StateRepository interface to declare Role related repository methods
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@Repository
public interface StateRepository extends JpaRepository<State, UUID>{

	@Query("from State s where s.countryId = :countryId order by s.stateName asc")
	List<State> findAllByCountryId(@Param("countryId") UUID countryId);
	
	@Query("from State s where s.id = :stateId")
	State findByStateId(@Param("stateId") UUID stateId );
	
}
