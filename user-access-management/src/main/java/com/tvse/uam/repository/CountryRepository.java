package com.tvse.uam.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tvse.uam.domain.Country;

/**
 * CountryRepository interface to declare Country related repository methods
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, UUID> {
	
	@Query("from Country c where c.id = :countryId order by c.countryName asc")
	Country findByCountryId(@Param("countryId") UUID countryId );

}
