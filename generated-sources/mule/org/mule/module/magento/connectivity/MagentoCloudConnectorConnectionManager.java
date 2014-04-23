
package org.mule.module.magento.connectivity;

import java.util.List;
import javax.annotation.Generated;
import org.apache.commons.pool.KeyedObjectPool;
import org.mule.api.ConnectionException;
import org.mule.api.ConnectionExceptionCode;
import org.mule.api.MetadataAware;
import org.mule.api.MuleContext;
import org.mule.api.MuleEvent;
import org.mule.api.config.MuleProperties;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.context.MuleContextAware;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.devkit.capability.Capabilities;
import org.mule.api.devkit.capability.ModuleCapability;
import org.mule.api.lifecycle.Disposable;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.retry.RetryPolicyTemplate;
import org.mule.common.DefaultResult;
import org.mule.common.DefaultTestResult;
import org.mule.common.FailureType;
import org.mule.common.Result;
import org.mule.common.TestResult;
import org.mule.common.Testable;
import org.mule.common.metadata.ConnectorMetaDataEnabled;
import org.mule.common.metadata.MetaData;
import org.mule.common.metadata.MetaDataFailureType;
import org.mule.common.metadata.MetaDataKey;
import org.mule.common.metadata.NativeQueryMetadataTranslator;
import org.mule.common.query.DsqlQuery;
import org.mule.config.PoolingProfile;
import org.mule.devkit.processor.ExpressionEvaluatorSupport;
import org.mule.module.magento.MagentoCloudConnector;
import org.mule.module.magento.adapters.MagentoCloudConnectorConnectionIdentifierAdapter;
import org.mule.module.magento.connection.ConnectionManager;
import org.mule.module.magento.connection.UnableToAcquireConnectionException;
import org.mule.module.magento.pooling.DevkitGenericKeyedObjectPool;


/**
 * A {@code MagentoCloudConnectorConnectionManager} is a wrapper around {@link MagentoCloudConnector } that adds connection management capabilities to the pojo.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-04-23T03:07:34-05:00", comments = "Build master.1920.518defc")
public class MagentoCloudConnectorConnectionManager
    extends ExpressionEvaluatorSupport
    implements MetadataAware, MuleContextAware, ProcessAdapter<MagentoCloudConnectorConnectionIdentifierAdapter> , Capabilities, Disposable, Initialisable, Testable, ConnectorMetaDataEnabled, NativeQueryMetadataTranslator, ConnectionManager<MagentoCloudConnectorConnectionKey, MagentoCloudConnectorConnectionIdentifierAdapter>
{

    /**
     * 
     */
    private String username;
    /**
     * 
     */
    private String password;
    /**
     * 
     */
    private String address;
    /**
     * Mule Context
     * 
     */
    protected MuleContext muleContext;
    /**
     * Flow Construct
     * 
     */
    protected FlowConstruct flowConstruct;
    /**
     * Connector Pool
     * 
     */
    private KeyedObjectPool connectionPool;
    protected PoolingProfile poolingProfile;
    protected RetryPolicyTemplate retryPolicyTemplate;
    private final static String MODULE_NAME = "Magento";
    private final static String MODULE_VERSION = "2.1.2-SNAPSHOT";
    private final static String DEVKIT_VERSION = "3.5.0-SNAPSHOT";
    private final static String DEVKIT_BUILD = "master.1920.518defc";
    private final static String MIN_MULE_VERSION = "3.5";

    /**
     * Sets muleContext
     * 
     * @param value Value to set
     */
    public void setMuleContext(MuleContext value) {
        this.muleContext = value;
    }

    /**
     * Retrieves muleContext
     * 
     */
    public MuleContext getMuleContext() {
        return this.muleContext;
    }

    /**
     * Sets flowConstruct
     * 
     * @param value Value to set
     */
    public void setFlowConstruct(FlowConstruct value) {
        this.flowConstruct = value;
    }

    /**
     * Retrieves flowConstruct
     * 
     */
    public FlowConstruct getFlowConstruct() {
        return this.flowConstruct;
    }

    /**
     * Sets poolingProfile
     * 
     * @param value Value to set
     */
    public void setPoolingProfile(PoolingProfile value) {
        this.poolingProfile = value;
    }

    /**
     * Retrieves poolingProfile
     * 
     */
    public PoolingProfile getPoolingProfile() {
        return this.poolingProfile;
    }

    /**
     * Sets retryPolicyTemplate
     * 
     * @param value Value to set
     */
    public void setRetryPolicyTemplate(RetryPolicyTemplate value) {
        this.retryPolicyTemplate = value;
    }

    /**
     * Retrieves retryPolicyTemplate
     * 
     */
    public RetryPolicyTemplate getRetryPolicyTemplate() {
        return this.retryPolicyTemplate;
    }

    /**
     * Sets username
     * 
     * @param value Value to set
     */
    public void setUsername(String value) {
        this.username = value;
    }

    /**
     * Retrieves username
     * 
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Sets address
     * 
     * @param value Value to set
     */
    public void setAddress(String value) {
        this.address = value;
    }

    /**
     * Retrieves address
     * 
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * Sets password
     * 
     * @param value Value to set
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Retrieves password
     * 
     */
    public String getPassword() {
        return this.password;
    }

    public void initialise() {
        connectionPool = new DevkitGenericKeyedObjectPool(new MagentoCloudConnectorConnectionFactory(this), poolingProfile);
        if (retryPolicyTemplate == null) {
            retryPolicyTemplate = muleContext.getRegistry().lookupObject(MuleProperties.OBJECT_DEFAULT_RETRY_POLICY_TEMPLATE);
        }
    }

    @Override
    public void dispose() {
        try {
            connectionPool.close();
        } catch (Exception e) {
        }
    }

    public MagentoCloudConnectorConnectionIdentifierAdapter acquireConnection(MagentoCloudConnectorConnectionKey key)
        throws Exception
    {
        return ((MagentoCloudConnectorConnectionIdentifierAdapter) connectionPool.borrowObject(key));
    }

    public void releaseConnection(MagentoCloudConnectorConnectionKey key, MagentoCloudConnectorConnectionIdentifierAdapter connection)
        throws Exception
    {
        connectionPool.returnObject(key, connection);
    }

    public void destroyConnection(MagentoCloudConnectorConnectionKey key, MagentoCloudConnectorConnectionIdentifierAdapter connection)
        throws Exception
    {
        connectionPool.invalidateObject(key, connection);
    }

    /**
     * Returns true if this module implements such capability
     * 
     */
    public boolean isCapableOf(ModuleCapability capability) {
        if (capability == ModuleCapability.LIFECYCLE_CAPABLE) {
            return true;
        }
        if (capability == ModuleCapability.CONNECTION_MANAGEMENT_CAPABLE) {
            return true;
        }
        return false;
    }

    @Override
    public<P >ProcessTemplate<P, MagentoCloudConnectorConnectionIdentifierAdapter> getProcessTemplate() {
        return new ManagedConnectionProcessTemplate(this, muleContext);
    }

    @Override
    public MagentoCloudConnectorConnectionKey getDefaultConnectionKey() {
        return new MagentoCloudConnectorConnectionKey(getUsername(), getPassword(), getAddress());
    }

    @Override
    public MagentoCloudConnectorConnectionKey getEvaluatedConnectionKey(MuleEvent event)
        throws Exception
    {
        if (event!= null) {
            final String _transformedUsername = ((String) evaluateAndTransform(muleContext, event, this.getClass().getDeclaredField("username").getGenericType(), null, getUsername()));
            if (_transformedUsername == null) {
                throw new UnableToAcquireConnectionException("Parameter username in method initialiseConnector can't be null because is not @Optional");
            }
            final String _transformedPassword = ((String) evaluateAndTransform(muleContext, event, this.getClass().getDeclaredField("password").getGenericType(), null, getPassword()));
            if (_transformedPassword == null) {
                throw new UnableToAcquireConnectionException("Parameter password in method initialiseConnector can't be null because is not @Optional");
            }
            final String _transformedAddress = ((String) evaluateAndTransform(muleContext, event, this.getClass().getDeclaredField("address").getGenericType(), null, getAddress()));
            if (_transformedAddress == null) {
                throw new UnableToAcquireConnectionException("Parameter address in method initialiseConnector can't be null because is not @Optional");
            }
            return new MagentoCloudConnectorConnectionKey(_transformedUsername, _transformedPassword, _transformedAddress);
        }
        return getDefaultConnectionKey();
    }

    public String getModuleName() {
        return MODULE_NAME;
    }

    public String getModuleVersion() {
        return MODULE_VERSION;
    }

    public String getDevkitVersion() {
        return DEVKIT_VERSION;
    }

    public String getDevkitBuild() {
        return DEVKIT_BUILD;
    }

    public String getMinMuleVersion() {
        return MIN_MULE_VERSION;
    }

    public TestResult test() {
        MagentoCloudConnectorConnectionIdentifierAdapter connection = null;
        DefaultTestResult result;
        MagentoCloudConnectorConnectionKey key = getDefaultConnectionKey();
        try {
            connection = acquireConnection(key);
            result = new DefaultTestResult(Result.Status.SUCCESS);
        } catch (Exception e) {
            try {
                destroyConnection(key, connection);
            } catch (Exception ie) {
            }
            result = ((DefaultTestResult) buildFailureTestResult(e));
        } finally {
            if (connection!= null) {
                try {
                    releaseConnection(key, connection);
                } catch (Exception ie) {
                }
            }
        }
        return result;
    }

    public DefaultResult buildFailureTestResult(Exception exception) {
        DefaultTestResult result;
        if (exception instanceof ConnectionException) {
            ConnectionExceptionCode code = ((ConnectionException) exception).getCode();
            if (code == ConnectionExceptionCode.UNKNOWN_HOST) {
                result = new DefaultTestResult(Result.Status.FAILURE, exception.getMessage(), FailureType.UNKNOWN_HOST, exception);
            } else {
                if (code == ConnectionExceptionCode.CANNOT_REACH) {
                    result = new DefaultTestResult(Result.Status.FAILURE, exception.getMessage(), FailureType.RESOURCE_UNAVAILABLE, exception);
                } else {
                    if (code == ConnectionExceptionCode.INCORRECT_CREDENTIALS) {
                        result = new DefaultTestResult(Result.Status.FAILURE, exception.getMessage(), FailureType.INVALID_CREDENTIALS, exception);
                    } else {
                        if (code == ConnectionExceptionCode.CREDENTIALS_EXPIRED) {
                            result = new DefaultTestResult(Result.Status.FAILURE, exception.getMessage(), FailureType.INVALID_CREDENTIALS, exception);
                        } else {
                            if (code == ConnectionExceptionCode.UNKNOWN) {
                                result = new DefaultTestResult(Result.Status.FAILURE, exception.getMessage(), FailureType.UNSPECIFIED, exception);
                            } else {
                                result = new DefaultTestResult(Result.Status.FAILURE, exception.getMessage(), FailureType.UNSPECIFIED, exception);
                            }
                        }
                    }
                }
            }
        } else {
            result = new DefaultTestResult(Result.Status.FAILURE, exception.getMessage(), FailureType.UNSPECIFIED, exception);
        }
        return result;
    }

    @Override
    public Result<List<MetaDataKey>> getMetaDataKeys() {
        MagentoCloudConnectorConnectionIdentifierAdapter connection = null;
        MagentoCloudConnectorConnectionKey key = getDefaultConnectionKey();
        try {
            connection = acquireConnection(key);
            try {
                return new DefaultResult<List<MetaDataKey>>(connection.getMetadataKeys(), (Result.Status.SUCCESS));
            } catch (Exception e) {
                return new DefaultResult<List<MetaDataKey>>(null, (Result.Status.FAILURE), "There was an error retrieving the metadata keys from service provider after acquiring connection, for more detailed information please read the provided stacktrace", MetaDataFailureType.ERROR_METADATA_KEYS_RETRIEVER, e);
            }
        } catch (Exception e) {
            try {
                destroyConnection(key, connection);
            } catch (Exception ie) {
            }
            return ((DefaultResult<List<MetaDataKey>> ) buildFailureTestResult(e));
        } finally {
            if (connection!= null) {
                try {
                    releaseConnection(key, connection);
                } catch (Exception ie) {
                }
            }
        }
    }

    @Override
    public Result<MetaData> getMetaData(MetaDataKey metaDataKey) {
        MagentoCloudConnectorConnectionIdentifierAdapter connection = null;
        MagentoCloudConnectorConnectionKey key = getDefaultConnectionKey();
        try {
            connection = acquireConnection(key);
            try {
                return new DefaultResult<MetaData>(connection.getMetadata(metaDataKey));
            } catch (Exception e) {
                return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), getMetaDataException(metaDataKey), MetaDataFailureType.ERROR_METADATA_RETRIEVER, e);
            }
        } catch (Exception e) {
            try {
                destroyConnection(key, connection);
            } catch (Exception ie) {
            }
            return ((DefaultResult<MetaData> ) buildFailureTestResult(e));
        } finally {
            if (connection!= null) {
                try {
                    releaseConnection(key, connection);
                } catch (Exception ie) {
                }
            }
        }
    }

    private String getMetaDataException(MetaDataKey key) {
        if ((key!= null)&&(key.getId()!= null)) {
            return ("There was an error retrieving metadata from key: "+(key.getId()+" after acquiring the connection, for more detailed information please read the provided stacktrace"));
        } else {
            return "There was an error retrieving metadata after acquiring the connection, MetaDataKey is null or its id is null, for more detailed information please read the provided stacktrace";
        }
    }

    @Override
    public Result<String> toNativeQuery(DsqlQuery query) {
        MagentoCloudConnectorConnectionIdentifierAdapter connection = null;
        Result<String> result;
        MagentoCloudConnectorConnectionKey key = getDefaultConnectionKey();
        try {
            connection = acquireConnection(key);
            result = new DefaultResult<String>(connection.toNativeQuery(query).toString());
        } catch (Exception e) {
            try {
                destroyConnection(key, connection);
            } catch (Exception ie) {
            }
            result = new DefaultResult<String>(null, Result.Status.FAILURE, e.getMessage());
        } finally {
            if (connection!= null) {
                try {
                    releaseConnection(key, connection);
                } catch (Exception ie) {
                }
            }
        }
        return result;
    }

}
