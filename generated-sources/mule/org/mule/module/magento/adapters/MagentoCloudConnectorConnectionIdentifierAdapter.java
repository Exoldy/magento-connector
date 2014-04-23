
package org.mule.module.magento.adapters;

import javax.annotation.Generated;
import org.mule.module.magento.MagentoCloudConnector;
import org.mule.module.magento.connection.Connection;


/**
 * A <code>MagentoCloudConnectorConnectionIdentifierAdapter</code> is a wrapper around {@link MagentoCloudConnector } that implements {@link org.mule.devkit.dynamic.api.helper.Connection} interface.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-04-23T03:47:06-05:00", comments = "Build master.1920.518defc")
public class MagentoCloudConnectorConnectionIdentifierAdapter
    extends MagentoCloudConnectorProcessAdapter
    implements Connection
{


    public String getConnectionIdentifier() {
        return super.connectionId();
    }

}
