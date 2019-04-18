package com.tvse.uam.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tvse.uam.constants.ApplicationConstants;
import com.tvse.uam.dto.UserRoleDTO;
import com.tvse.uam.service.UserRoleService;

/**
 * @author TTS-Ponni
 *
 */
public class TestUserRoleController {

	@Mock
	private UserRoleService userRoleService;

	@InjectMocks
	private UserRoleController userRoleController;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		// Process mock annotations
		MockitoAnnotations.initMocks(this);
		// Setup Spring test in standalone mode
		this.mockMvc = MockMvcBuilders.standaloneSetup(userRoleController).build();
	}

	@Test
	public void testBrands() throws Exception {
		String url = ApplicationConstants.USERROLES + ApplicationConstants.BRANDS;
		mockMvc = MockMvcBuilders.standaloneSetup(userRoleController)
				.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
		mockMvc.perform(MockMvcRequestBuilders.get(url)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}

	@Test
	public void testApplications() throws Exception {
		String url = ApplicationConstants.USERROLES + ApplicationConstants.APPLICATIONS;
		// Setup Spring test in standalone mode
		this.mockMvc = MockMvcBuilders.standaloneSetup(userRoleController).build();
		mockMvc.perform(MockMvcRequestBuilders.get(url)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}

//	@Test
//	public void testRolesByApplication() throws Exception {
//		String url = ApplicationConstants.USERROLES + ApplicationConstants.APPLICATIONROLES;
//		List<UUID> list = new ArrayList<UUID>();
//		list.add(UUID.randomUUID());
//		// Setup Spring test in standalone mode
//		this.mockMvc = MockMvcBuilders.standaloneSetup(userRoleController).build();
//		mockMvc.perform(MockMvcRequestBuilders.get(url).param(ApplicationConstants.ID, asJsonString(list)))
//				// .content(asJsonString(list))
//				// .contentType(MediaType.APPLICATION_JSON))
//				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
//	}

	@Test
	public void testCreateUserRole() throws Exception {
		String url = ApplicationConstants.USERROLES;
		// Setup Spring test in standalone mode
		this.mockMvc = MockMvcBuilders.standaloneSetup(userRoleController).build();
		UserRoleDTO userRoleDTO = new UserRoleDTO();
		userRoleDTO.setId(UUID.randomUUID());
		List<UserRoleDTO> list = new ArrayList<UserRoleDTO>();
		mockMvc.perform(MockMvcRequestBuilders.post(url).content(asJsonString(list))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}

	@Test
	public void testGetByUserId() throws Exception {
		String url = ApplicationConstants.USERROLES + ApplicationConstants.USERROLES_USERID;
		mockMvc.perform(MockMvcRequestBuilders.delete(url, UUID.randomUUID()))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}

	@Test
	public void testDeleteUserRole() throws Exception {
		String url = ApplicationConstants.USERROLES + ApplicationConstants.USERROLES_DELETE;
		mockMvc.perform(MockMvcRequestBuilders.get(url, UUID.randomUUID()))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}

	private String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
