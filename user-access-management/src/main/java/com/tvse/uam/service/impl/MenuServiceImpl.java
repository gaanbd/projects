package com.tvse.uam.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.tvse.common.dto.ResponseDTO;
import com.tvse.common.exception.ApplicationException;
import com.tvse.common.exception.BusinessException;
import com.tvse.common.exception.DataRepositoryException;
import com.tvse.uam.constants.ApplicationConstants;
import com.tvse.uam.constants.MessageConstants;
import com.tvse.uam.dto.MenuDTO;
import com.tvse.uam.repository.MenuRepository;
import com.tvse.uam.service.MenuService;
import com.tvse.uam.util.CommonUtil;

/**
 * UserAccessServiceImpl class to implements UserAccessService interface methods
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@Service(value = "menuService")
@Transactional
public class MenuServiceImpl implements MenuService {

	private static final Logger LOGGER = LogManager.getLogger(MenuServiceImpl.class.getName());
	private MenuRepository menuRepository;

	@Inject
	public MenuServiceImpl(@NotNull MenuRepository menuRepository) {
		this.menuRepository = menuRepository;
	}

	/*
	 * Role Creation and Role Permission Services finished
	 */

	/**
	 * This method is used to get all menus
	 * 
	 * @return map of List<MenuDTO>
	 */
	@Override
	public ResponseDTO<Object> getMenus() {
		LOGGER.debug("getMenus() Intiated :");
		Map<String, List<MenuDTO>> menuMap = new HashMap<>();
		try {
			List<MenuDTO> menuDomainList = menuRepository.getAllMenus();
			List<MenuDTO> menuList = new ArrayList<>();
			if (CollectionUtils.isEmpty(menuDomainList)) {
				throw new BusinessException(MessageConstants.MENU_NOT_AVAILABLE);
			}
			menuDomainList.forEach(parentMenuDto -> {
				if (!ObjectUtils.isEmpty(parentMenuDto) && parentMenuDto.getParentId() == null) {
					List<MenuDTO> subMenuList = new ArrayList<>();
					doSubMenuList(menuDomainList, parentMenuDto, subMenuList);
					// Add Sub Menus under parent
					parentMenuDto.setSubMenuList(subMenuList);
					// Parent Menu
					menuList.add(parentMenuDto);
				}
			});
			menuMap.put(ApplicationConstants.MENU, menuList);
			LOGGER.debug("getMenus() Terminated :");
		} catch (BusinessException be) {
			LOGGER.error(CommonUtil.errorFormat(be));
			throw be;
		} catch (DataAccessException dae) {
			LOGGER.error(CommonUtil.errorFormat(dae));
			throw new DataRepositoryException();
		} catch (Exception e) {
			LOGGER.error(CommonUtil.errorFormat(e));
			throw new ApplicationException();
		}
		return new ResponseDTO<>(false, ApplicationConstants.SUCCESS_CODE_200,
				CommonUtil.populateMessage(MessageConstants.MENU_DATA_LOADED), menuMap);
	}

	/**
	 * To load SubMenu
	 * 
	 * @param menuDomainList
	 * @param parentMenuDto
	 * @param subMenuList
	 */
	private void doSubMenuList(List<MenuDTO> menuDomainList, MenuDTO parentMenuDto, List<MenuDTO> subMenuList) {
		menuDomainList.forEach(subMenuDto -> {
			// Sub Menu
			if (!ObjectUtils.isEmpty(subMenuDto) && subMenuDto.getParentId() != null
					&& subMenuDto.getParentId().toString().equals(parentMenuDto.getId().toString()))
				subMenuList.add(subMenuDto);
		});
	}
}