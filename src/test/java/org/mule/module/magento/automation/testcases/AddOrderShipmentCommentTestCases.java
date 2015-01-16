/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.magento.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.magento.api.CatalogProductCreateEntity;
import com.magento.api.OrderItemIdQty;
import com.magento.api.ShoppingCartCustomerAddressEntity;
import com.magento.api.ShoppingCartCustomerEntity;
import com.magento.api.ShoppingCartPaymentMethodEntity;
import com.magento.api.ShoppingCartProductEntity;
import org.mule.modules.tests.ConnectorTestUtils;

public class AddOrderShipmentCommentTestCases extends MagentoTestParent {

	@Before
	public void setUp() throws Exception {
			initializeTestRunMessage("addOrderShipmentComment");
			
			ShoppingCartCustomerEntity customer = getTestRunMessageValue("customer");
			List<ShoppingCartCustomerAddressEntity> addresses = getTestRunMessageValue("customerAddresses");
			String shippingMethod = getTestRunMessageValue("shippingMethod");
			ShoppingCartPaymentMethodEntity paymentMethod = getTestRunMessageValue("paymentMethod");
			
			List<HashMap<String, Object>> products = getTestRunMessageValue("products");
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

			upsertOnTestRunMessage("shoppingCartProducts", shoppingCartProducts);

			String storeId = getTestRunMessageValue("storeId");
			int quoteId = createShoppingCart(storeId);
			
			String orderId = createShoppingCartOrder(quoteId, customer, addresses, paymentMethod, shippingMethod, shoppingCartProducts);
			upsertOnTestRunMessage("orderId", orderId);
			
			List<OrderItemIdQty> quantities = new ArrayList<OrderItemIdQty>();
			
			for (ShoppingCartProductEntity shoppingCartProduct : shoppingCartProducts) {
				OrderItemIdQty item = new OrderItemIdQty(Integer.parseInt(shoppingCartProduct.getProduct_id()), shoppingCartProduct.getQty());
				quantities.add(item);
			}
			
			String shipmentId = createOrderShipment(orderId, quantities);
			initializeTestRunMessage("addOrderShipmentComment");
			upsertOnTestRunMessage("productIds", productIds);
			upsertOnTestRunMessage("shipmentId", shipmentId);
	}
	
	@Category({RegressionTests.class})
	@Test
	public void testAddOrderShipmentComment() {
		try {
			Boolean result = runFlowAndGetPayload("add-order-shipment-comment");
			assertTrue(result);
		}
		catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
	@After
	public void tearDown() {
		try {
			List<Integer> productIds = getTestRunMessageValue("productIds");
			for (Integer productId : productIds) {
				deleteProductById(productId);
			}	
			clearSalesTables();
		}
		catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
}
