package org.mule.module.magento.automation.testcases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.magento.api.CatalogAttributeEntity;

public class ListProductAttributesTestCases extends MagentoTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context
					.getBean("listProductAttributes");
			MessageProcessor createProductFlow = lookupFlowConstruct("create-product");
			MuleEvent response = createProductFlow
					.process(getTestEvent(testObjects));
			response.getMessage().getPayload();
			testObjects.put("productId", response.getMessage().getPayload());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@SuppressWarnings("unchecked")
	@Category({ SmokeTests.class, RegressionTests.class })
	@Test
	public void testListProductAttributes() {
		try {
			MessageProcessor flow = lookupFlowConstruct("list-product-attributes");

			MuleEvent response = flow.process(getTestEvent(testObjects));
			List<CatalogAttributeEntity> catalogAttributeEntities = (List<CatalogAttributeEntity>) response
					.getMessage().getPayload();
			assertNotNull(catalogAttributeEntities);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@After
	public void tearDown() {
		try {
			int productId = (Integer) testObjects.get("productId");
			deleteProductById(productId);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
