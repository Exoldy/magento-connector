
package org.mule.module.magento.processors;

import java.util.List;
import javax.annotation.Generated;
import com.magento.api.OrderItemIdQty;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.config.ConfigurationException;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.registry.RegistrationException;
import org.mule.common.DefaultResult;
import org.mule.common.FailureType;
import org.mule.common.Result;
import org.mule.common.metadata.ConnectorMetaDataEnabled;
import org.mule.common.metadata.DefaultListMetaDataModel;
import org.mule.common.metadata.DefaultMetaData;
import org.mule.common.metadata.DefaultPojoMetaDataModel;
import org.mule.common.metadata.DefaultSimpleMetaDataModel;
import org.mule.common.metadata.MetaData;
import org.mule.common.metadata.MetaDataKey;
import org.mule.common.metadata.MetaDataModel;
import org.mule.common.metadata.OperationMetaDataEnabled;
import org.mule.common.metadata.datatype.DataType;
import org.mule.common.metadata.datatype.DataTypeFactory;
import org.mule.module.magento.MagentoCloudConnector;
import org.mule.module.magento.connectivity.MagentoCloudConnectorConnectionManager;
import org.mule.security.oauth.callback.ProcessCallback;


/**
 * CreateOrderShipmentMessageProcessor invokes the {@link org.mule.module.magento.MagentoCloudConnector#createOrderShipment(java.lang.String, java.util.List, java.lang.String, boolean, boolean)} method in {@link MagentoCloudConnector }. For each argument there is a field in this processor to match it.  Before invoking the actual method the processor will evaluate and transform where possible to the expected argument type.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-RC1", date = "2014-05-06T10:53:30-05:00", comments = "Build master.1926.b0106b2")
public class CreateOrderShipmentMessageProcessor
    extends AbstractConnectedProcessor
    implements MessageProcessor, OperationMetaDataEnabled
{

    protected Object orderId;
    protected String _orderIdType;
    protected Object itemsQuantities;
    protected List<OrderItemIdQty> _itemsQuantitiesType;
    protected Object comment;
    protected String _commentType;
    protected Object sendEmail;
    protected boolean _sendEmailType;
    protected Object includeCommentInEmail;
    protected boolean _includeCommentInEmailType;

    public CreateOrderShipmentMessageProcessor(String operationName) {
        super(operationName);
    }

    /**
     * Obtains the expression manager from the Mule context and initialises the connector. If a target object  has not been set already it will search the Mule registry for a default one.
     * 
     * @throws InitialisationException
     */
    public void initialise()
        throws InitialisationException
    {
    }

    @Override
    public void start()
        throws MuleException
    {
        super.start();
    }

    @Override
    public void stop()
        throws MuleException
    {
        super.stop();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    /**
     * Sets itemsQuantities
     * 
     * @param value Value to set
     */
    public void setItemsQuantities(Object value) {
        this.itemsQuantities = value;
    }

    /**
     * Sets sendEmail
     * 
     * @param value Value to set
     */
    public void setSendEmail(Object value) {
        this.sendEmail = value;
    }

    /**
     * Sets comment
     * 
     * @param value Value to set
     */
    public void setComment(Object value) {
        this.comment = value;
    }

    /**
     * Sets includeCommentInEmail
     * 
     * @param value Value to set
     */
    public void setIncludeCommentInEmail(Object value) {
        this.includeCommentInEmail = value;
    }

    /**
     * Sets orderId
     * 
     * @param value Value to set
     */
    public void setOrderId(Object value) {
        this.orderId = value;
    }

    /**
     * Invokes the MessageProcessor.
     * 
     * @param event MuleEvent to be processed
     * @throws Exception
     */
    public MuleEvent doProcess(final MuleEvent event)
        throws Exception
    {
        Object moduleObject = null;
        try {
            moduleObject = findOrCreate(MagentoCloudConnectorConnectionManager.class, true, event);
            final String _transformedOrderId = ((String) evaluateAndTransform(getMuleContext(), event, CreateOrderShipmentMessageProcessor.class.getDeclaredField("_orderIdType").getGenericType(), null, orderId));
            final List<OrderItemIdQty> _transformedItemsQuantities = ((List<OrderItemIdQty> ) evaluateAndTransform(getMuleContext(), event, CreateOrderShipmentMessageProcessor.class.getDeclaredField("_itemsQuantitiesType").getGenericType(), null, itemsQuantities));
            final String _transformedComment = ((String) evaluateAndTransform(getMuleContext(), event, CreateOrderShipmentMessageProcessor.class.getDeclaredField("_commentType").getGenericType(), null, comment));
            final Boolean _transformedSendEmail = ((Boolean) evaluateAndTransform(getMuleContext(), event, CreateOrderShipmentMessageProcessor.class.getDeclaredField("_sendEmailType").getGenericType(), null, sendEmail));
            final Boolean _transformedIncludeCommentInEmail = ((Boolean) evaluateAndTransform(getMuleContext(), event, CreateOrderShipmentMessageProcessor.class.getDeclaredField("_includeCommentInEmailType").getGenericType(), null, includeCommentInEmail));
            Object resultPayload;
            final ProcessTemplate<Object, Object> processTemplate = ((ProcessAdapter<Object> ) moduleObject).getProcessTemplate();
            resultPayload = processTemplate.execute(new ProcessCallback<Object,Object>() {


                public List<Class<? extends Exception>> getManagedExceptions() {
                    return null;
                }

                public boolean isProtected() {
                    return false;
                }

                public Object process(Object object)
                    throws Exception
                {
                    return ((MagentoCloudConnector) object).createOrderShipment(_transformedOrderId, _transformedItemsQuantities, _transformedComment, _transformedSendEmail, _transformedIncludeCommentInEmail);
                }

            }
            , this, event);
            event.getMessage().setPayload(resultPayload);
            return event;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Result<MetaData> getInputMetaData() {
        return new DefaultResult<MetaData>(new DefaultMetaData(new DefaultListMetaDataModel(getPojoOrSimpleModel(OrderItemIdQty.class))));
    }

    @Override
    public Result<MetaData> getOutputMetaData(MetaData inputMetadata) {
        return new DefaultResult<MetaData>(new DefaultMetaData(getPojoOrSimpleModel(String.class)));
    }

    private MetaDataModel getPojoOrSimpleModel(Class clazz) {
        DataType dataType = DataTypeFactory.getInstance().getDataType(clazz);
        if (DataType.POJO.equals(dataType)) {
            return new DefaultPojoMetaDataModel(clazz);
        } else {
            return new DefaultSimpleMetaDataModel(dataType);
        }
    }

    public Result<MetaData> getGenericMetaData(MetaDataKey metaDataKey) {
        ConnectorMetaDataEnabled connector;
        try {
            connector = ((ConnectorMetaDataEnabled) findOrCreate(MagentoCloudConnectorConnectionManager.class, true, null));
            try {
                Result<MetaData> metadata = connector.getMetaData(metaDataKey);
                if ((Result.Status.FAILURE).equals(metadata.getStatus())) {
                    return metadata;
                }
                if (metadata.get() == null) {
                    return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), "There was an error processing metadata at MagentoCloudConnector at createOrderShipment retrieving was successful but result is null");
                }
                return metadata;
            } catch (Exception e) {
                return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
            }
        } catch (ClassCastException cast) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), "There was an error getting metadata, there was no connection manager available. Maybe you're trying to use metadata from an Oauth connector");
        } catch (ConfigurationException e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        } catch (RegistrationException e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        } catch (IllegalAccessException e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        } catch (InstantiationException e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        } catch (Exception e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        }
    }

}
