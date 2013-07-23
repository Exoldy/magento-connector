package org.mule.module.magento.automation.testcases;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.magento.api.CatalogProductCreateEntity;
import com.magento.api.SalesOrderEntity;
import com.magento.api.SalesOrderItemEntity;
import com.magento.api.ShoppingCartCustomerAddressEntity;
import com.magento.api.ShoppingCartCustomerEntity;
import com.magento.api.ShoppingCartPaymentMethodEntity;
import com.magento.api.ShoppingCartProductEntity;

public class GetOrderTestCases extends MagentoTestParent {
	
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("getOrder");
			
			ShoppingCartCustomerEntity customer = (ShoppingCartCustomerEntity) testObjects.get("customer");
			List<ShoppingCartCustomerAddressEntity> addresses = (List<ShoppingCartCustomerAddressEntity>) testObjects.get("customerAddresses");
			String shippingMethod = testObjects.get("shippingMethod").toString();
			ShoppingCartPaymentMethodEntity paymentMethod = (ShoppingCartPaymentMethodEntity) testObjects.get("paymentMethod");
			
			List<HashMap<String, Object>> products = (List<HashMap<String, Object>>) testObjects.get("products");
			List<ShoppingCartProductEntity> shoppingCartProducts = new ArrayList<ShoppingCartProductEntity>();
			List<Integer> productIds = new ArrayList<Integer>();
			
			for (HashMap<String, Object> product : products) {
				
				// Get the product data
				String productType = (String) product.get("type");
				int productSet = (Integer) product.get("set");
				String productSKU = (String) product.get("sku");
				CatalogProductCreateEntity attributes = (CatalogProductCreateEntity) product.get("attributesRef");
			
				// Create the product and get the product ID
				int productId = createProduct(productType, productSet, productSKU, attributes);
				
				// Get the quantity to place in the shopping cart
				double qtyToPurchase = (Double) product.get("qtyToPurchase");

				// Create the shopping cart product entity
				ShoppingCartProductEntity shoppingCartProduct = new ShoppingCartProductEntity();
				shoppingCartProduct.setProduct_id(productId + "");
				shoppingCartProduct.setQty(qtyToPurchase);
				
				shoppingCartProducts.add(shoppingCartProduct);
				productIds.add(productId);
			}

			String orderId = createShoppingCartOrder(customer, addresses, paymentMethod, shippingMethod, shoppingCartProducts);
			
			testObjects.put("productIds", productIds);		
			testObjects.put("orderId", orderId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testGetOrder() {
		try {
			List<Integer> productIds = (List<Integer>) testObjects.get("productIds");
			String orderId = (String) testObjects.get("orderId");
			String shippingMethod = testObjects.get("shippingMethod").toString();
			ShoppingCartPaymentMethodEntity paymentMethod = (ShoppingCartPaymentMethodEntity) testObjects.get("paymentMethod");
			
			MessageProcessor flow = lookupFlowConstruct("get-order");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			SalesOrderEntity order = (SalesOrderEntity) response.getMessage().getPayload();
			assertNotNull(order);
			
			assertTrue(Integer.parseInt(order.getOrder_id()) == Integer.parseInt(orderId));
			assertTrue(order.getShipping_method().equals(shippingMethod));
			assertTrue(order.getPayment().getMethod().equals(paymentMethod.getMethod()));
			SalesOrderItemEntity[] products = order.getItems();
			for (SalesOrderItemEntity product : products) {
				int productId = Integer.parseInt(product.getProduct_id());
				assertTrue(productIds.contains(productId));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			List<Integer> productIds = (List<Integer>) testObjects.get("productIds");
			for (Integer productId : productIds) {
				deleteProductById(productId);
			}	
			
			String orderId = (String) testObjects.get("orderId");
			cancelOrder(orderId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
