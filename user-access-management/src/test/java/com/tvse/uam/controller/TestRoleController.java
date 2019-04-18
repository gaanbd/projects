package com.tvse.uam.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.MockitoAnnotations.Mock;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tvse.uam.constants.ApplicationConstants;
import com.tvse.uam.dto.ApplicationEntitlementDTO;
import com.tvse.uam.service.RoleService;

public class TestRoleController {

	@Mock
	private RoleService roleService;

	@InjectMocks
	private RoleController roleController;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		// Process mock annotations
		MockitoAnnotations.initMocks(this);

		// Setup Spring test in standalone mode
		this.mockMvc = MockMvcBuilders.standaloneSetup(roleController).build();
	}

	@Test
	public void testGetAllRoles() throws Exception {
		String url = "/roles/";
		mockMvc = MockMvcBuilders.standaloneSetup(roleController)
				.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
		mockMvc.perform(MockMvcRequestBuilders.get(url).param("searchParam", ""))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}

	@Test
	public void testRoleValidation() throws Exception {
		String url = "/roles/validaterole/{applicationId}/{role}/{from}";
		mockMvc.perform(MockMvcRequestBuilders.get(url, UUID.randomUUID(), "1", "1"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}

	@Test
	public void testEntitlements() throws Exception {
		String url = "/roles/entitlements";
		mockMvc.perform(MockMvcRequestBuilders.get(url)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}

	@Test
	public void testCreateRole() throws Exception {
		String url = "/roles/createrole";
		ApplicationEntitlementDTO ApplicationEntitlementDTO = new ApplicationEntitlementDTO();
		List<ApplicationEntitlementDTO> list = new ArrayList<ApplicationEntitlementDTO>();
		list.add(ApplicationEntitlementDTO);
		mockMvc.perform(MockMvcRequestBuilders.post(url).content(asJsonString(list))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.param(ApplicationConstants.ROLENAME, "").param(ApplicationConstants.ROLEDESCRIPTION, "")
				.param(ApplicationConstants.ROLEDISPLAYNAME, "")).andExpect(MockMvcResultMatchers.status().isCreated())
				.andReturn();
	}

	@Test
	public void testUpdateRole() throws Exception {
		String url = "/roles/updaterole";
		ApplicationEntitlementDTO ApplicationEntitlementDTO = new ApplicationEntitlementDTO();
		List<ApplicationEntitlementDTO> list = new ArrayList<ApplicationEntitlementDTO>();
		list.add(ApplicationEntitlementDTO);
		mockMvc.perform(MockMvcRequestBuilders.post(url).content(asJsonString(list))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.param(ApplicationConstants.ROLENAME, "").param(ApplicationConstants.ROLEDESCRIPTION, "")
				.param(ApplicationConstants.ROLEID, "").param(ApplicationConstants.ROLEDISPLAYNAME, ""))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}

	@Test
	public void testDeleteRole() throws Exception {
		String url = "/roles/{roleId}";
		mockMvc.perform(MockMvcRequestBuilders.delete(url, UUID.randomUUID()))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}

	@Test
	public void testGetUserRoleByRoleId() throws Exception {
		String url = "/roles/candelete/{roleId}";
		mockMvc.perform(MockMvcRequestBuilders.get(url, UUID.randomUUID()))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}

	@Test
	public void testGetRoleById() throws Exception {
		String url = "/roles/{roleId}";
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
