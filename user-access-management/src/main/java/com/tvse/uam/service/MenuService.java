package com.tvse.uam.service;

import com.tvse.common.dto.ResponseDTO;

/**
 * MenuService interface to declare Menu methods
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
public interface MenuService {
	ResponseDTO<Object> getMenus();
}
