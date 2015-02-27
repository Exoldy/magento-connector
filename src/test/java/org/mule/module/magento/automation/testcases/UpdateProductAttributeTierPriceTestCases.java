/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.magento.automation.testcases;

import com.magento.api.CatalogProductTierPriceEntity;
import com.magento.api.CustomerGroupEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class UpdateProductAttributeTierPriceTestCases extends MagentoTestParent {

    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("updateProductAttributeTierPrice");
        List<CustomerGroupEntity> customerGroupEntities = runFlowAndGetPayload("list-customer-groups");
        CustomerGroupEntity cge = customerGroupEntities.get(0);
        cge.getCustomer_group_id();

        CatalogProductTierPriceEntity catalogProductTierPriceEntity = getTestRunMessageValue("catalogProductTierPriceEntityRef");
        catalogProductTierPriceEntity.setCustomer_group_id(String.valueOf(cge.getCustomer_group_id()));
        catalogProductTierPriceEntity.setWebsite("0");
        catalogProductTierPriceEntity.setQty(50);
        catalogProductTierPriceEntity.setPrice(9.90);
        upsertOnTestRunMessage("catalogProductTierPriceEntityRef", catalogProductTierPriceEntity);

        Integer productId = runFlowAndGetPayload("create-product");
        upsertOnTestRunMessage("productId", productId);
    }

    @Category({RegressionTests.class})
    @Test
    public void testUpdateProductAttributeTierPrice() {
        try {
            Integer result = runFlowAndGetPayload("update-product-attribute-tier-price");
            assertNotNull(result);
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @After
    public void tearDown() throws Exception {
        int productId = getTestRunMessageValue("productId");
        deleteProductById(productId);
    }

}
