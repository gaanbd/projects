package com.tvse.uam.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tvse.uam.domain.City;

/**
 * CityRepository interface to declare Role related repository methods
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
public interface CityRepository extends JpaRepository<City, UUID> {
	
	@Query("from City c where c.stateId = :stateId order by c.cityName asc")
	List<City> findAllByStateId(@Param("stateId") UUID stateId);
	
	@Query("from City c where c.id = :cityId")
	City findByCityId(@Param("cityId") UUID cityId );
	
	@Query("select c.cityName from City c where c.id = :cityId")
	String findCityNameByCityId(@Param("cityId") UUID cityId );

}
