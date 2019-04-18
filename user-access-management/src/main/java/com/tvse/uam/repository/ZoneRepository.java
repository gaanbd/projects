package com.tvse.uam.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tvse.uam.domain.Zone;
/**
 * ZoneRepository interface to declare Zone
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@Repository
public interface ZoneRepository extends JpaRepository<Zone, UUID> {

}
