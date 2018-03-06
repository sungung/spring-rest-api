package com.sungung.api.springrestapi;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@WebMvcTest
public class MockMvcTests {

	@Autowired
	private MockMvc mvc;
	
	@Test
	public void testMockMvc() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get("/ping").accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void testUriVersionController() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get("/v1/customers/1").accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void testInvalidUriVersionController() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get("/v3/customers/1").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isUnsupportedMediaType());		
	}
	
	@Test
	public void testMediaTypeVersionController() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/customers/1").header("Content-Type", "application/vnd.api-v1.0+json"))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void testInvalidMediaTypeVersionController() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get("/customers/1").header("Content-Type", "application/vnd.api-v1.2+json"))
			.andExpect(MockMvcResultMatchers.status().isUnsupportedMediaType());
	}
	
	@Test
	public void testCustomerHeaderVersionController() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/header/customers/1").header("apiVersion", "v1"))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
}
