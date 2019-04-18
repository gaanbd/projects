package com.tvse.uam.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tvse.uam.domain.EmailSettings;

/**
 * EmailSettingsRepository interface to declare Email Settings related
 * repository methods
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@Repository
public interface EmailSettingsRepository extends JpaRepository<EmailSettings, UUID> {

}
