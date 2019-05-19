package com.deduplication.service;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.deduplication.dto.Customer;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DeDuplicationServiceTest {
	
	@Autowired
	private DeDuplicationService deDuplicationService;
	
	@Test
	public void assertDetermineIf2CustomersAreDupsSuccessScenario() {
		Customer customer1 = new Customer();
		customer1.setFirstName("Kyle");
		customer1.setLastName("test");
		customer1.setEmail("testemail@email.com");
		customer1.setPhone("");
		customer1.setAddress1("");
		customer1.setAddress2("");
		customer1.setZip("02021");
		customer1.setCompany("McDermott LLC");
		customer1.setStateLong("");
		customer1.setState("MA");
		customer1.setCity("");
		
		Customer customer2 = new Customer();
		customer2.setFirstName("Kyle");
		customer2.setLastName("test");
		customer2.setEmail("testemail@email.com");
		customer1.setPhone("");
		customer1.setAddress1("");
		customer1.setAddress2("");
		customer1.setZip("02021");
		customer1.setCompany("McDerbott LLC");
		customer1.setStateLong("");
		customer1.setState("MA");
		customer1.setCity("");
		
		assertTrue(deDuplicationService.determineIf2CustomersAreDups(customer1, customer2));
	}
	
	@Test
	public void assertDetermineIf2CustomersAreDupsFailureScenario() {
		Customer customer1 = new Customer();
		customer1.setFirstName("Kyle");
		customer1.setLastName("test");
		customer1.setEmail("testeail@email.com");
		customer1.setPhone("");
		customer1.setAddress1("");
		customer1.setAddress2("");
		customer1.setZip("");
		customer1.setCompany("McDerbott LLC");
		customer1.setStateLong("");
		customer1.setState("");
		customer1.setCity("");
		
		Customer customer2 = new Customer();
		customer2.setFirstName("Kyle");
		customer2.setLastName("test");
		customer2.setEmail("testemail@email.com");
		customer2.setPhone("a");
		customer2.setAddress1("b");
		customer2.setAddress2("c");
		customer2.setZip("d");
		customer2.setCompany("e");
		customer2.setStateLong("f");
		customer2.setState("g");
		customer2.setCity("h");
		
		assertTrue(!deDuplicationService.determineIf2CustomersAreDups(customer1, customer2));
	}
	
	@Test
	public void assertisSameSuccessScenario() {
		assertTrue(deDuplicationService.isSame("cricket", "criket"));
	}
	
	@Test
	public void assertisSameFailureScenario() {
		assertTrue(!deDuplicationService.isSame("cricket", "criketgame"));
	}

}
