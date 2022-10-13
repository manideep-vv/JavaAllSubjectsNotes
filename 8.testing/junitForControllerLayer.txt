package com.tcs.fresco;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.Hystrix;
import com.netflix.hystrix.HystrixCircuitBreaker;
import com.netflix.hystrix.HystrixCommandKey;
import com.tcs.fresco.service.EmployeeService;

import junit.framework.AssertionFailedError;


	@SpringBootTest
	@RunWith(SpringRunner.class)
	public class EmployeeApplicationTests {

		   private MockMvc mockMvc;
		    
		    @Autowired
		    WebApplicationContext context;
		    

		    @Autowired
		    EmployeeService empService;
		  
		    
		@Before
		public void setup() {
			mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

		}
		
		@Test
		public void testsuccess() {
			try {
                ResultActions resultActions = mockMvc.perform(get("/employees/1"));
                MockHttpServletResponse mockResponse = resultActions.andReturn()
                        .getResponse();
                if(mockResponse.getContentType()!=null) {
					 mockMvc.perform(get("/employees/1" )).andDo(print())
		             .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Arun"))
		             .andExpect(MockMvcResultMatchers.jsonPath("$.education").value("B.E"));
                	}
                else {
                	fail("failed");
                }
				}
			catch(Exception e) {
				fail("failed");
//				throw new Error(e.toString());
//				assertTrue(false);
			}
		}
		
		@Test
		public void testfallback() {
			try {
                ResultActions resultActions = mockMvc.perform(get("/employees/1"));
                MockHttpServletResponse mockResponse = resultActions.andReturn()
                        .getResponse();
                if(mockResponse.getContentType()!=null) {
					for(int i=0; i<=10; i++) {
					long start=	System.currentTimeMillis();
					 mockMvc.perform(get("/employees/199" ))//.andDo(print())
		             .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("dummy_name"))
		             .andExpect(MockMvcResultMatchers.jsonPath("$.education").value("dummy_edu"));
					 long end=	System.currentTimeMillis();
					 long time=end-start;
					 if(i>1) {
						 assertTrue(time<2000);
					 }
					 System.out.println("time taken ----"+time);
					}
					for(int i=0; i<=10; i++) {
					mockMvc.perform(get("/employees/1" )).andDo(print())
		             .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("dummy_name"))
		             .andExpect(MockMvcResultMatchers.jsonPath("$.education").value("dummy_edu"));
					}
					Thread.sleep(60000);
					for(int i=0; i<=10; i++) {
					mockMvc.perform(get("/employees/1" )).andDo(print())
		             .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Arun"))
		             .andExpect(MockMvcResultMatchers.jsonPath("$.education").value("B.E"));
					}
                }
                else {
                	fail("failed");
                }
			}
			catch(Exception e) {
//				throw new Error(e.toString());
//				assertTrue(false);
			}
		}
		
		@Test
		public void testsuccess1() {
			try {
                ResultActions resultActions = mockMvc.perform(get("/employees/bank/1"));
                MockHttpServletResponse mockResponse = resultActions.andReturn()
                        .getResponse();
                if(mockResponse.getContentType()!=null) {
				 mockMvc.perform(get("/employees/bank/1" )).andDo(print())
	             .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Arun"))
	             .andExpect(MockMvcResultMatchers.jsonPath("$.education").value("B.E"))
				 .andExpect(MockMvcResultMatchers.jsonPath("$.bankName").value("BOI"));
                }
                else {
                	fail("failed");
                }
			}
			catch(Exception e) {
				throw new Error(e.toString());
//				assertTrue(false);
			}
		}
		
		@Test
		public void testfallback1() {
			try {
                ResultActions resultActions = mockMvc.perform(get("/employees/bank/1"));
                MockHttpServletResponse mockResponse = resultActions.andReturn()
                        .getResponse();
                if(mockResponse.getContentType()!=null) {
					for(int i=0; i<=10; i++) {
					long start=	System.currentTimeMillis();
					 mockMvc.perform(get("/employees/bank/199" ))//.andDo(print())
		             .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("dummy_name"))
		             .andExpect(MockMvcResultMatchers.jsonPath("$.education").value("dummy_edu"))
					 .andExpect(MockMvcResultMatchers.jsonPath("$.bankName").value("dummy_bank"));
					 long end=	System.currentTimeMillis();
					 long time=end-start;
					 if(i>2) {
						 assertTrue(time<2000);
					 }
					 System.out.println("time taken ----"+time);
					}
					for(int i=0; i<=10; i++) {
					mockMvc.perform(get("/employees/bank/1" )).andDo(print())
		             .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("dummy_name"))
		             .andExpect(MockMvcResultMatchers.jsonPath("$.education").value("dummy_edu"));
					}
					Thread.sleep(60000);
					for(int i=0; i<=10; i++) {
					mockMvc.perform(get("/employees/bank/1" )).andDo(print())
		             .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Arun"))
		             .andExpect(MockMvcResultMatchers.jsonPath("$.education").value("B.E"));
					}
                }
                else {
                	fail("failed");
                }
			}
			catch(Exception e) {
//				throw new Error(e.toString());
//				assertTrue(false);
			}
		}
		
//		@Test
//		public void test() {
//			assertTrue(true);
//		}
		


		 private byte[] toJson(Object r) throws Exception {
		        ObjectMapper map = new ObjectMapper();
		        return map.writeValueAsString(r).getBytes();
		    }
	}