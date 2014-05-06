
package org.mule.module.magento.connectivity;

import javax.annotation.Generated;


/**
 * A tuple of connection parameters
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-RC1", date = "2014-05-06T10:53:30-05:00", comments = "Build master.1926.b0106b2")
public class MagentoCloudConnectorConnectionKey {

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

    public MagentoCloudConnectorConnectionKey(String username, String password, String address) {
        this.username = username;
        this.password = password;
        this.address = address;
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

    @Override
    public int hashCode() {
        int result = ((this.username!= null)?this.username.hashCode(): 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MagentoCloudConnectorConnectionKey)) {
            return false;
        }
        MagentoCloudConnectorConnectionKey that = ((MagentoCloudConnectorConnectionKey) o);
        if (((this.username!= null)?(!this.username.equals(that.username)):(that.username!= null))) {
            return false;
        }
        return true;
    }

}
