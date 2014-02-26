package com.cisco.oss.foundation.monitoring;

import java.util.Date;

public interface DBConnection {

    /**
     * DataBase schema name.
     */
    String getDBSchemaName();

    /**
     * URL used to connect to the DB.
     */
    String getURL();

    /**
     * Total number of Active DataBase connections.
     */
    long getActiveDbConnections();

    /**
     * Total number of idle DataBase connections.
     */
    long getIdleDbConnections();

    /**
     * Total number of requests to this DB since the component has started.
     */
    long getTotalRequestCount();

    /**
     * Total number of failed requests to this DB since the component has started.
     */
    long getFailedRequestCount();

    /**
     * Date Time of last failure on DB.
     */
    Date getLastFailedRequestTime();

    /**
     * Description of last failure on this interface for consumption by an operator.
     */
    String getLastFailedRequestDescription();
}
