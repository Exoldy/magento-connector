/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.magento.automation.testcases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import java.io.InputStream;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class UpdateProductAttributeMediaTestCases extends MagentoTestParent {

    @Before
    // This test depends on there being an img.gif file in the classpath (when writing this test there was such a file in src/test/resources).
    public void setUp() throws Exception {
        initializeTestRunMessage("updateProductAttributeMedia");
        Integer productId = runFlowAndGetPayload("create-product");
        upsertOnTestRunMessage("productId", productId);

        InputStream is = this.getClass().getClassLoader().getResourceAsStream("img.gif");
        upsertPayloadContentOnTestRunMessage(is);
        String newImageFilename = runFlowAndGetPayload("create-product-attribute-media");

        upsertOnTestRunMessage("fileName", newImageFilename);
    }

    @Category({RegressionTests.class})
    @Test
    public void testUpdateProductAttributeMedia() {
        try {
            Boolean result = runFlowAndGetPayload("update-product-attribute-media");
            assertTrue(result);
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @After
    public void tearDown() throws Exception {
        int productId = (Integer) getTestRunMessageValue("productId");
        deleteProductById(productId);
    }

}
