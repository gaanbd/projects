package com.tvse.uam.controller;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.MockitoAnnotations.Mock;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tvse.uam.constants.ApplicationConstants;
import com.tvse.uam.dto.UserDTO;
import com.tvse.uam.service.UserService;

public class TestUserController {
	
	
	@Mock
    private UserService userService;
 
    @InjectMocks
    private UserController userController;
 
    private MockMvc mockMvc;
	   
	@Before
	public void setUp() throws Exception {
		  // Process mock annotations
        MockitoAnnotations.initMocks(this);
        // Setup Spring test in standalone mode
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}
	
	@Test
	public void testGetUsers() throws Exception {
		String url = ApplicationConstants.USERS+ApplicationConstants.GETALLUSERDETAILS;
	    mockMvc = MockMvcBuilders.standaloneSetup(userController)
	    		   .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
	    		   .build(); 
//	    mockMvc.perform(MockMvcRequestBuilders.get(url))
//		           .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test
	public void testGetCountry() throws Exception {
		String url = ApplicationConstants.USERS+ApplicationConstants.GETALLCOUNTRY;
        // Setup Spring test in standalone mode
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	    mockMvc.perform(MockMvcRequestBuilders.get(url))
		           .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test
	public void testGetAllStateByCountry() throws Exception {
		String url = ApplicationConstants.USERS+ApplicationConstants.GETALLSTATEBYCOUNTRY;
        // Setup Spring test in standalone mode
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	    mockMvc.perform(MockMvcRequestBuilders.get(url,UUID.randomUUID()))
        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test
	public void testGetCity() throws Exception {
		String url = ApplicationConstants.USERS+ApplicationConstants.GETALLCITYBYSTATE;
        // Setup Spring test in standalone mode
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	    mockMvc.perform(MockMvcRequestBuilders.get(url,UUID.randomUUID()))
		           .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test
	public void testGetLoginName() throws Exception {
		String url = ApplicationConstants.USERS+ApplicationConstants.LOGINNAMECHECK;
        // Setup Spring test in standalone mode
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	    mockMvc.perform(MockMvcRequestBuilders.get(url,"loginName"))
		           .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test
	public void testEmpIdUniqueCheck() throws Exception {
		String url = ApplicationConstants.USERS+ApplicationConstants.EMPLOYEEID_UNIQUE;
        // Setup Spring test in standalone mode
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	    mockMvc.perform(MockMvcRequestBuilders.get(url,"employeeId"))
		           .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test
	public void testCreateUser() throws Exception {
		String url=ApplicationConstants.USERS;
        // Setup Spring test in standalone mode
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
		UserDTO userProfileDTO = new UserDTO();
		userProfileDTO.setId(UUID.randomUUID());
		userProfileDTO.setCityId(UUID.randomUUID());
		userProfileDTO.setState(UUID.randomUUID());
		userProfileDTO.setCountry(UUID.randomUUID());
		userProfileDTO.setUserName("userName");
		userProfileDTO.setFirstName("firstName");
		userProfileDTO.setMobile("1234567890");
		userProfileDTO.setEmail("email@gmail.com");
	    mockMvc.perform(MockMvcRequestBuilders.post(url)
	    		.content(asJsonString(userProfileDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
		           .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test
	public void testUpdateUser() throws Exception {
		String url=ApplicationConstants.USERS+ApplicationConstants.USER;
        // Setup Spring test in standalone mode
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
		UserDTO userProfileDTO = new UserDTO();
		userProfileDTO.setId(UUID.randomUUID());
		userProfileDTO.setCityId(UUID.randomUUID());
		userProfileDTO.setState(UUID.randomUUID());
		userProfileDTO.setCountry(UUID.randomUUID());
		userProfileDTO.setUserName("userName");
		userProfileDTO.setFirstName("firstName");
		userProfileDTO.setMobile("1234567890");
		userProfileDTO.setEmail("email@gmail.com");
	    mockMvc.perform(MockMvcRequestBuilders.post(url)
	    		.content(asJsonString(userProfileDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
		           .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test
	public void testDeleteUser() throws Exception {
		String url =ApplicationConstants.USERS+ApplicationConstants.USERROLES_USERID;
	    mockMvc.perform(MockMvcRequestBuilders.delete(url,UUID.randomUUID()))
		           .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test
	public void testGetByUserId() throws Exception {
		String url =ApplicationConstants.USERS+"/"+ApplicationConstants.GETBYID;
	    mockMvc.perform(MockMvcRequestBuilders.get(url,UUID.randomUUID()))
		           .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test
	public void testUpdateUserStatus() throws Exception {
		String url =ApplicationConstants.USERS+"/"+ApplicationConstants.ID_STATUS;
	    mockMvc.perform(MockMvcRequestBuilders.post(url,UUID.randomUUID()))
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
