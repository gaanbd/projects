package com.tvse.uam.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.MockitoAnnotations.Mock;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.tvse.uam.service.MenuService;

public class TestMenuController {
	
	
	@Mock
    private MenuService menuService;
 
    @InjectMocks
    private MenuController menuController;
 
    private MockMvc mockMvc;
	   
	@Before
	public void setUp() throws Exception {
		  // Process mock annotations
        MockitoAnnotations.initMocks(this);
 
        // Setup Spring test in standalone mode
        this.mockMvc = MockMvcBuilders.standaloneSetup(menuController).build();
	}
	
	@Test
	public void testMenus() throws Exception {
		String url ="/menus/";
	    mockMvc = MockMvcBuilders.standaloneSetup(menuController)
	    		   .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
	    		   .build(); 
	    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url))
		           .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}

}
