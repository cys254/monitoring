/*
 * Copyright 2014 Cisco Systems, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.cisco.oss.foundation.monitoring.services;

import com.cisco.oss.foundation.monitoring.ConnectionStatus;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public interface Service {

    /**
     * Description of the service for an Operator.
     */
    String getServiceDescription();

    /**
     * Interface Name as defined in the component.
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
