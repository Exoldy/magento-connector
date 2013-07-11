package org.mule.module.magento.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.magento.api.CustomerCustomerEntity;
import com.magento.api.CustomerCustomerEntityToCreate;

public class GetCustomerTestCases extends MagentoTestParent {

	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("getCustomer");
			
			CustomerCustomerEntityToCreate customer = (CustomerCustomerEntityToCreate) testObjects.get("customer");
			testObjects.put("email", customer.getEmail());
			testObjects.put("password", customer.getPassword());
			testObjects.put("firstname", customer.getFirstname());
			testObjects.put("lastname", customer.getLastname());
			
			MessageProcessor flow = lookupFlowConstruct("create-customer");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			int customerId = (Integer) response.getMessage().getPayload();
			testObjects.put("customerId", customerId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({RegressionTests.class})
	@Test
	public void testGetCustomer() {
		try {
			
			int customerId = (Integer) testObjects.get("customerId");
			CustomerCustomerEntityToCreate customer = (CustomerCustomerEntityToCreate) testObjects.get("customer");

			MessageProcessor flow = lookupFlowConstruct("get-customer");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			CustomerCustomerEntity createdCustomer = (CustomerCustomerEntity) response.getMessage().getPayload();

			assertTrue(createdCustomer.getCustomer_id() == customerId);
			assertTrue(createdCustomer.getEmail().equals(customer.getEmail()));
			assertTrue(createdCustomer.getFirstname().equals(customer.getFirstname()));
			assertTrue(createdCustomer.getLastname().equals(customer.getLastname()));
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			MessageProcessor flow = lookupFlowConstruct("delete-customer");
			MuleEvent response = flow.process(getTestEvent(testObjects));
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
