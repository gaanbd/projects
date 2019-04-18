package com.tvse.uam.controller;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tvse.common.dto.ResponseDTO;
import com.tvse.uam.constants.ApplicationConstants;
import com.tvse.uam.service.MenuService;

/**
 * MenuController Class to get our MENU rest api's
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@RestController
@RequestMapping(ApplicationConstants.MENUS)
public class MenuController {

	private static final Logger LOGGER = LogManager.getLogger(MenuController.class.getName());

	private MenuService menuService;

	@Inject
	public MenuController(MenuService menuService) {
		this.menuService = menuService;
	}

	/* Role Creation and Permission Api's finished */
	/**
	 * get api to get all the menus & submenus
	 */
	@GetMapping
	public ResponseEntity<ResponseDTO<Object>> getMenus(Pageable pagable) {
		LOGGER.info("Start getMenus() in Controller");
		return new ResponseEntity<>(menuService.getMenus(), HttpStatus.OK);
	}

}
