/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.magento.api.customer;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

/**
 * Facade for the Magento Inventory API 
 * @author flbulgarelli
 *
 * @param <AttributesCollectionType> the representation of a collection of attributes
 * @param <ExceptionType> the kind of exception thrown
 */
public interface MagentoInventoryClient<AttributesCollectionType, ExceptionType extends Exception>
{

    /**
     * Retrieve stock data by product ids
     * 
     * @param productIdsOrSkus a not empty list of product ids or skus whose attributes to list
     * @return a list of stock items attributes
     */
    AttributesCollectionType listStockItems(@NotNull List<String> productIdsOrSkus)
        throws ExceptionType;

    /**
     * Update product stock data given its id or sku
     * 
     * @param productIdOrSku the product id or sku of the product to update
     * @param the attributes a non empty map of attributes to update 
     */
    void updateStockItem(@NotNull String productIdOrSku, @NotNull Map<String, Object> attributes)
        throws ExceptionType;

}
