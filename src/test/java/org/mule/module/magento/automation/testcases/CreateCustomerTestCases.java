package org.mule.module.magento.automation.testcases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.magento.api.CustomerCustomerEntityToCreate;

public class CreateCustomerTestCases extends MagentoTestParent {

	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("createCustomer");
			
			// Get the customer object from testObjects
			// Place individual variables in testObjects
			CustomerCustomerEntityToCreate customer = (CustomerCustomerEntityToCreate) testObjects.get("customer");
			testObjects.put("email", customer.getEmail());
			testObjects.put("password", customer.getPassword());
			testObjects.put("firstname", customer.getFirstname());
			testObjects.put("lastname", customer.getLastname());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testCreateCustomer() {
		try {
			// Create the customer
			MessageProcessor flow = lookupFlowConstruct("create-customer");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			// Get the ID of the created customer
			Integer customerId =(Integer) response.getMessage().getPayload();
			assertNotNull(customerId);
			
			// Put the customerId in testObjects so that we can use it to delete
			testObjects.put("customerId", customerId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			// Delete the customer
			MessageProcessor flow = lookupFlowConstruct("delete-customer");
			MuleEvent response = flow.process(getTestEvent(testObjects));
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
