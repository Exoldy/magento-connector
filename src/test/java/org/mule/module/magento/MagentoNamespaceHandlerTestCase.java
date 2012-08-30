/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */


package org.mule.module.magento;

import org.mule.construct.Flow;
import org.mule.tck.FunctionalTestCase;

/**
 * Test for the xml mapping of the {@link MagentoCloudConnector}
 */
public class MagentoNamespaceHandlerTestCase extends FunctionalTestCase {
    public void testNamespaceConfig() throws Exception {
        assertNotNull(lookupFlowConstruct("GetOrderShipment"));
    }

    public void testNamespaceConfig2() throws Exception {
        assertNotNull(lookupFlowConstruct("ListOrdersInvoices"));
    }

    @Override
    protected String getConfigResources() {
        return "magento-namespace-config.xml";
    }

    private Flow lookupFlowConstruct(String name) {
        return (Flow) muleContext.getRegistry().lookupFlowConstruct(name);
    }

}
