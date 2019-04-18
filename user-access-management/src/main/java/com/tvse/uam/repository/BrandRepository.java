package com.tvse.uam.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tvse.uam.domain.Brand;

/**
 * BrandRepository interface to declare Role related repository methods
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@Repository
public interface BrandRepository extends JpaRepository<Brand, UUID> {

	@Query("from Brand order by brandName asc")
	List<Brand> findAllBrandDetails();

	@Query("from Brand where id in (:brandIds)")
	List<Brand> getBrands(@Param("brandIds") List<UUID> brandIds);

}
