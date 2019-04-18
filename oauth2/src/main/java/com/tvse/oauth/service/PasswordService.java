package com.tvse.oauth.service;

import com.tvse.common.dto.ResponseDTO;

/**
 * @author techmango
 *
 */
public interface PasswordService {

	ResponseDTO<String> createForgotPassword(String email);

}
