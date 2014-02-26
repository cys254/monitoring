package com.cisco.vss.foundation.monitoring;

import java.util.Date;

public interface ServerConnection {

    /**
     * Name of the component connected to.
     */
    String getServerName();

    /**
     * Either RMI Service Name or Interface Name as defined in the component.
     */
    String getInterfaceName();

    /**
     * Host Name for connection.
     */
    String getHostName();

    /**
     * Port connected to.
     */
    long getDestinationPort();

    /**
     * Total number of requests on this interface since the component has started.
     */
    long getTotalRequestCount();

    /**
     * Total number of failed requests on this interface since the component has started.
     */
    long getFailedRequestCount();

    /**
     * Date Time of last failure on this interface.
     */
    Date getLastFailedRequestTime();

    /**
     * Description of last failure on this interface for consumption by an operator.
     */
    String getLastFailedRequestDescription();


    Date getLastSuccessfulRequestTime();

    String getMethodName();

    Date getLastTransactionStartTime();

    Date getLastTransactionEndTime();

    long getLastTransactionProcessingTime();

    ConnectionStatus getTransactionStatus();

    long getOpenSince();
}
