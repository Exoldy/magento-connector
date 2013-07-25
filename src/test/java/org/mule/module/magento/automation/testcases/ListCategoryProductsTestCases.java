package org.mule.module.magento.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.magento.api.CatalogAssignedProduct;
import com.magento.api.CatalogProductCreateEntity;

public class ListCategoryProductsTestCases extends MagentoTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("listCategoryProducts");
			
			MessageProcessor createCategoryFlow = lookupFlowConstruct("create-category");
			MuleEvent res =  createCategoryFlow.process(getTestEvent(testObjects));
			Integer categoryId = (Integer) res.getMessage().getPayload();
			testObjects.put("categoryId", categoryId);
			
			CatalogProductCreateEntity catalogProductCreateEntity = (CatalogProductCreateEntity) testObjects.get("attributesRef");
			catalogProductCreateEntity.setCategory_ids(new String[] { String.valueOf(categoryId) } );
			
			MessageProcessor createProductFlow = lookupFlowConstruct("create-product");
			MuleEvent res2 = createProductFlow.process(getTestEvent(testObjects));
			Integer productId = (Integer) res2.getMessage().getPayload();
			testObjects.put("productId", productId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testListCategoryProducts() {
		try {
			MessageProcessor flow = lookupFlowConstruct("list-category-products");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<CatalogAssignedProduct> catalogAssignedProducts = (List<CatalogAssignedProduct>) response.getMessage().getPayload();
			assertEquals("There should be one product in the category", 1, catalogAssignedProducts.size());
			
			CatalogAssignedProduct catalogAssignedProduct = catalogAssignedProducts.get(0);
			assertEquals("Assert that the product id is what it is expected to be", testObjects.get("productId"), catalogAssignedProduct.getProduct_id());
			
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			int productId = (Integer) testObjects.get("productId");
			int categoryId = (Integer) testObjects.get("categoryId");
			deleteProductById(productId);
			deleteCategory(categoryId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
