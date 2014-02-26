package com.cisco.vss.foundation.monitoring;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public interface Service {

    /**
     * Description of the service for an Operator.
     */
    String getServiceDescription();

    /**
     * Either RMI Service Name or Interface Name as defined in the component.
     */
    String getInterfaceName();

    /**
     * Communication protocol e.g. RMI, HTTP etc.
     */
    String getProtocol();

    /**
     * Port Number
     */
    long getPort();

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

    double getTps();

    int getUsedThreads();
}
