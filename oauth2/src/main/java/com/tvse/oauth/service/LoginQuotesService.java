package com.tvse.oauth.service;

import com.tvse.common.dto.ResponseDTO;
import com.tvse.oauth.domain.LoginQuotes;

/**
 * @author techmango
 *
 */
public interface LoginQuotesService {

	ResponseDTO<LoginQuotes> getLoginQuotes();

}
