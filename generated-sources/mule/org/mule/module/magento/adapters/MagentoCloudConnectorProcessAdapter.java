
package org.mule.module.magento.adapters;

import javax.annotation.Generated;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.routing.filter.Filter;
import org.mule.module.magento.MagentoCloudConnector;
import org.mule.security.oauth.callback.ProcessCallback;


/**
 * A <code>MagentoCloudConnectorProcessAdapter</code> is a wrapper around {@link MagentoCloudConnector } that enables custom processing strategies.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-RC1", date = "2014-05-06T10:53:30-05:00", comments = "Build master.1926.b0106b2")
public class MagentoCloudConnectorProcessAdapter
    extends MagentoCloudConnectorLifecycleAdapter
    implements ProcessAdapter<MagentoCloudConnectorCapabilitiesAdapter>
{


    public<P >ProcessTemplate<P, MagentoCloudConnectorCapabilitiesAdapter> getProcessTemplate() {
        final MagentoCloudConnectorCapabilitiesAdapter object = this;
        return new ProcessTemplate<P,MagentoCloudConnectorCapabilitiesAdapter>() {


            @Override
            public P execute(ProcessCallback<P, MagentoCloudConnectorCapabilitiesAdapter> processCallback, MessageProcessor messageProcessor, MuleEvent event)
                throws Exception
            {
                return processCallback.process(object);
            }

            @Override
            public P execute(ProcessCallback<P, MagentoCloudConnectorCapabilitiesAdapter> processCallback, Filter filter, MuleMessage message)
                throws Exception
            {
                return processCallback.process(object);
            }

        }
        ;
    }

}
