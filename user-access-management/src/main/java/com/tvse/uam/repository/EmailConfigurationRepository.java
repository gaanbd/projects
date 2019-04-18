package com.tvse.uam.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tvse.uam.domain.EmailConfiguration;

/**
 * EmailConfigurationRepository interface to declare Email Configuration related repository methods
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@Repository
public interface EmailConfigurationRepository extends JpaRepository<EmailConfiguration, UUID> {

	EmailConfiguration findEmailConfigurationByEmailConfigName(String templateName);
	
}
