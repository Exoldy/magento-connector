
package org.mule.module.magento.connection;

import javax.annotation.Generated;


/**
 * Exception thrown when the release connection operation of the
 *  connection manager fails.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-04-23T03:47:06-05:00", comments = "Build master.1920.518defc")
public class UnableToReleaseConnectionException
    extends Exception
{

     /**
     * Create a new exception
     *
     * @param throwable Inner exception
     */
    public UnableToReleaseConnectionException(Throwable throwable) {
        super(throwable);
    }
}
