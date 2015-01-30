/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.magento.automation.testcases;

import com.magento.api.CatalogProductCreateEntity;
import com.magento.api.CatalogProductEntity;
import com.magento.api.ShoppingCartProductEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class RemoveShoppingCartProductTestCases extends MagentoTestParent {

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("removeShoppingCartProduct");

        List<HashMap<String, Object>> productDefinitions = getTestRunMessageValue("products");

        List<ShoppingCartProductEntity> shoppingCartEntities = new ArrayList<ShoppingCartProductEntity>();
        List<Integer> productIds = new ArrayList<Integer>();

        // Iterate over each product definition and insert
        for (HashMap<String, Object> productDefinition : productDefinitions) {
            String productType = (String) productDefinition.get("type");
            int productSet = (Integer) productDefinition.get("set");
            String productSKU = (String) productDefinition.get("sku");
            CatalogProductCreateEntity attributes = (CatalogProductCreateEntity) productDefinition.get("attributesRef");

            // Get the product ID and the number of items we want to place in the shopping cart
            int productId = createProduct(productType, productSet, productSKU, attributes);
            double qtyToPurchase = (Double) productDefinition.get("qtyToPurchase");

            productIds.add(productId);

            ShoppingCartProductEntity shoppingCartEntity = new ShoppingCartProductEntity();
            shoppingCartEntity.setProduct_id(productId + "");
            shoppingCartEntity.setQty(qtyToPurchase);

            shoppingCartEntities.add(shoppingCartEntity);
        }


        // Create the shopping cart
        String storeId = getTestRunMessageValue("storeId").toString();
        int shoppingCartId = createShoppingCart(storeId);
        upsertOnTestRunMessage("quoteId", shoppingCartId);

        addProductsToShoppingCart(shoppingCartId, shoppingCartEntities);
        initializeTestRunMessage("removeShoppingCartProduct");
        upsertOnTestRunMessage("shoppingCartEntities", shoppingCartEntities);
        upsertOnTestRunMessage("productIds", productIds);

    }

    @Category({RegressionTests.class})
    @Test
    public void testRemoveShoppingCartProduct() {
        try {
            List<ShoppingCartProductEntity> products = getTestRunMessageValue("shoppingCartEntities");
            upsertOnTestRunMessage("productsRef", products);

            boolean result = runFlowAndGetPayload("remove-shopping-cart-product");
            assertTrue(result);

            List<CatalogProductEntity> shoppingCartProducts = runFlowAndGetPayload("list-shopping-cart-products");
            assertTrue(shoppingCartProducts.isEmpty());
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @After
    public void tearDown() throws Exception {
        List<Integer> productIds = getTestRunMessageValue("productIds");
        for (Integer productId : productIds) {
            deleteProductById(productId);
        }
    }
}
