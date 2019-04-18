package com.tvse.oauth.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tvse.oauth.domain.EmailConfiguration;

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
