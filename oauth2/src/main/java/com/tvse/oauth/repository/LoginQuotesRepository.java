package com.tvse.oauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tvse.oauth.domain.LoginQuotes;


/**
 * ApplicationRepository interface to declare Application related repository
 * methods
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@Repository
public interface LoginQuotesRepository extends JpaRepository<LoginQuotes, Integer> {

	LoginQuotes getById(Integer value);
}
