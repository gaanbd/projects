package com.tvse.uam.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.InvalidDataAccessResourceUsageException;

import com.tvse.common.dto.ResponseDTO;
import com.tvse.common.exception.ApplicationException;
import com.tvse.common.exception.BusinessException;
import com.tvse.common.exception.DataRepositoryException;
import com.tvse.uam.dto.MenuDTO;
import com.tvse.uam.repository.MenuRepository;
/**
 * TVSE-14 User Navigation Menu Dynamic - Driven from config tables
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
public class TestMenuServiceImpl {
	
	@Mock
	private MenuRepository menuRepository;
	
	@InjectMocks
	private MenuServiceImpl menuServiceImpl; 

	@Before
	public void setUp() throws Exception {
		menuRepository = Mockito.mock(MenuRepository.class);
		menuServiceImpl = new MenuServiceImpl(menuRepository);
	}
	
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testEmptyMenuList() {
		try
		{
			List<MenuDTO> listMenuDTO = new ArrayList<MenuDTO>();
			when(menuRepository.getAllMenus()).thenReturn(listMenuDTO);
			ResponseDTO<Object> responseMap = menuServiceImpl.getMenus();
		}
		catch(BusinessException be) {
			assertEquals("error.menu.not.available",be.getMessage());
		}
	}

	@Test
	public void testExceptionMenuList() {
		try
		{
			when(menuRepository.getAllMenus()).thenThrow(NullPointerException.class);
			ResponseDTO<Object> responseMap = menuServiceImpl.getMenus();
		}
		catch(ApplicationException be) {
			assertTrue(true);
		}
	}

	@Test
	public void testDAOExceptionMenuList() {
		try
		{
			when(menuRepository.getAllMenus()).thenThrow(InvalidDataAccessResourceUsageException.class);
			ResponseDTO<Object> responseMap = menuServiceImpl.getMenus();
		}
		catch(DataRepositoryException be) {
			assertTrue(true);
		}
	}

	@Test
	public void testEmptyMenu() {
		MenuDTO menuDTO = null;
		List<MenuDTO> listMenuDTO = new ArrayList<MenuDTO>();
		listMenuDTO.add(menuDTO);
		when(menuRepository.getAllMenus()).thenReturn(listMenuDTO);
		ResponseDTO<Object> responseDTO = menuServiceImpl.getMenus();
		Map<String, List<MenuDTO>> responseMap = (Map<String, List<MenuDTO>>) responseDTO.getPayload();
		List<MenuDTO> responseList = responseMap.get("Menu");
		assertEquals(0,responseList.size());
	}

	@Test
	public void testParentMenu() {
		MenuDTO menuDTO = new MenuDTO(UUID.fromString("b425a558-3cfe-11e9-8eca-283a4d21f94f"),"Admin","","",null,
				UUID.fromString("4b35104c-3cfe-11e9-8eca-283a4d21f94f"),"1",null,"routerUrl");
		List<MenuDTO> listMenuDTO = new ArrayList<MenuDTO>();
		listMenuDTO.add(menuDTO);
		when(menuRepository.getAllMenus()).thenReturn(listMenuDTO);
		ResponseDTO<Object> responseDTO = menuServiceImpl.getMenus();
		Map<String, List<MenuDTO>> responseMap = (Map<String, List<MenuDTO>>) responseDTO.getPayload();
		List<MenuDTO> responseList = responseMap.get("Menu");
		assertEquals(1,responseList.size());
		assertEquals("b425a558-3cfe-11e9-8eca-283a4d21f94f",responseList.get(0).getId().toString());
	}

	@Test
	public void testOneEmptySubMenu() {
		MenuDTO menuDTO = new MenuDTO(UUID.fromString("b425a558-3cfe-11e9-8eca-283a4d21f94f"),"Admin","","",null,
				UUID.fromString("4b35104c-3cfe-11e9-8eca-283a4d21f94f"),"1",null,"routerUrl");
		MenuDTO menuDTO1 = null;
		List<MenuDTO> listMenuDTO = new ArrayList<MenuDTO>();
		listMenuDTO.add(menuDTO);
		listMenuDTO.add(menuDTO1);
		when(menuRepository.getAllMenus()).thenReturn(listMenuDTO);
		ResponseDTO<Object> responseDTO = menuServiceImpl.getMenus();
		Map<String, List<MenuDTO>> responseMap = (Map<String, List<MenuDTO>>) responseDTO.getPayload();
		List<MenuDTO> responseList = responseMap.get("Menu");
		assertEquals(1,responseList.size());
		assertEquals( 0,responseList.get(0).getSubMenuList().size());
	}

	@Test
	public void testOneSubMenu() {
		MenuDTO menuDTO = new MenuDTO(UUID.fromString("b425a558-3cfe-11e9-8eca-283a4d21f94f"),"Admin","","",null,
				UUID.fromString("4b35104c-3cfe-11e9-8eca-283a4d21f94f"),"1",null,"routerUrl");
		MenuDTO menuDTO1 = new MenuDTO(UUID.fromString("b425a558-3cfe-11e9-8eca-283a4d21f94d"),"Users","","",UUID.fromString("b425a558-3cfe-11e9-8eca-283a4d21f94f"),
				UUID.fromString("4b35104c-3cfe-11e9-8eca-283a4d21f94f"),"1","/uam/users","routerUrl");
		List<MenuDTO> listMenuDTO = new ArrayList<MenuDTO>();
		listMenuDTO.add(menuDTO);
		listMenuDTO.add(menuDTO1);
		when(menuRepository.getAllMenus()).thenReturn(listMenuDTO);
		ResponseDTO<Object> responseDTO = menuServiceImpl.getMenus();
		Map<String, List<MenuDTO>> responseMap = (Map<String, List<MenuDTO>>) responseDTO.getPayload();
		List<MenuDTO> responseList = responseMap.get("Menu");
		assertEquals(1,responseList.size());
		assertEquals(1,responseList.get(0).getSubMenuList().size());
	}

	@Test
	public void testTwoSubMenu() {
		MenuDTO menuDTO = new MenuDTO(UUID.fromString("b425a558-3cfe-11e9-8eca-283a4d21f94f"),"Admin","","",null,
				UUID.fromString("4b35104c-3cfe-11e9-8eca-283a4d21f94f"),"1",null,"routerUrl");
		MenuDTO menuDTO1 = new MenuDTO(UUID.fromString("b425a558-3cfe-11e9-8eca-283a4d21f94d"),"Users","","",UUID.fromString("b425a558-3cfe-11e9-8eca-283a4d21f94f"),
				UUID.fromString("4b35104c-3cfe-11e9-8eca-283a4d21f94f"),"1","/uam/users","routerUrl");
		MenuDTO menuDTO2 = new MenuDTO(UUID.fromString("b425a558-3cfe-11e9-8eca-283a4d21f94c"),"Roles","","",UUID.fromString("b425a558-3cfe-11e9-8eca-283a4d21f94f"),
				UUID.fromString("4b35104c-3cfe-11e9-8eca-283a4d21f94f"),"1","/uam/roles","routerUrl");
		List<MenuDTO> listMenuDTO = new ArrayList<MenuDTO>();
		listMenuDTO.add(menuDTO);
		listMenuDTO.add(menuDTO1);
		listMenuDTO.add(menuDTO2);
		when(menuRepository.getAllMenus()).thenReturn(listMenuDTO);
		ResponseDTO<Object> responseDTO = menuServiceImpl.getMenus();
		Map<String, List<MenuDTO>> responseMap = (Map<String, List<MenuDTO>>) responseDTO.getPayload();
		List<MenuDTO> responseList = responseMap.get("Menu");
		assertEquals(1,responseList.size());
		assertEquals(2,responseList.get(0).getSubMenuList().size());
	}

	@Test
	public void testTwoSubMenuWithOneOrphan() {
		MenuDTO menuDTO = new MenuDTO(UUID.fromString("b425a558-3cfe-11e9-8eca-283a4d21f94f"),"Admin","","",null,
				UUID.fromString("4b35104c-3cfe-11e9-8eca-283a4d21f94f"),"1",null,"routerUrl");
		MenuDTO menuDTO1 = new MenuDTO(UUID.fromString("b425a558-3cfe-11e9-8eca-283a4d21f94d"),"Users","","",UUID.fromString("b425a558-3cfe-11e9-8eca-283a4d21f94f"),
				UUID.fromString("4b35104c-3cfe-11e9-8eca-283a4d21f94f"),"1","/uam/users","routerUrl");
		MenuDTO menuDTO2 = new MenuDTO(UUID.fromString("b425a558-3cfe-11e9-8eca-283a4d21f94c"),"Roles","","",UUID.fromString("b425a558-3cfe-11e9-8eca-283a4d21f94f"),
				UUID.fromString("4b35104c-3cfe-11e9-8eca-283a4d21f94f"),"1","/uam/roles","routerUrl");
		MenuDTO menuDTO3 = new MenuDTO(UUID.fromString("b425a558-3cfe-11e9-8eca-283a4d21f94c"),"Roles","","",UUID.fromString("b425a558-3cfe-11e9-8eca-283a4d21f94d"),
				UUID.fromString("4b35104c-3cfe-11e9-8eca-283a4d21f94f"),"1","/uam/roles1","routerUrl");
		List<MenuDTO> listMenuDTO = new ArrayList<MenuDTO>();
		listMenuDTO.add(menuDTO);
		listMenuDTO.add(menuDTO1);
		listMenuDTO.add(menuDTO2);
		listMenuDTO.add(menuDTO3);
		when(menuRepository.getAllMenus()).thenReturn(listMenuDTO);
		ResponseDTO<Object> responseDTO = menuServiceImpl.getMenus();
		Map<String, List<MenuDTO>> responseMap = (Map<String, List<MenuDTO>>) responseDTO.getPayload();
		List<MenuDTO> responseList = responseMap.get("Menu");
		System.out.println(responseList.size());
		System.out.println(responseList.get(0).getSubMenuList().size());
		assertEquals(1, responseList.size());
		assertEquals(2, responseList.get(0).getSubMenuList().size());
	}

}
