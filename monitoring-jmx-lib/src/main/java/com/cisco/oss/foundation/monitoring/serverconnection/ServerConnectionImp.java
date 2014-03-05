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

package com.cisco.oss.foundation.monitoring.serverconnection;

import com.cisco.oss.foundation.monitoring.ConnectionStatus;
import com.cisco.oss.foundation.monitoring.TransactionOpen;

import java.util.Date;

public class ServerConnectionImp implements ServerConnection {

    public ServerConnectionImp(long destinationPort, String hostName, String interfaceName, String serverName, String apiName) {
        this.destinationPort = destinationPort;
        this.hostName = hostName;
        this.interfaceName = interfaceName;
        this.serverName = serverName;
        this.apiName = apiName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServerConnectionImp that = (ServerConnectionImp) o;

        if (destinationPort != that.destinationPort) return false;
        if (apiName != null ? !apiName.equals(that.apiName) : that.apiName != null) return false;
        if (hostName != null ? !hostName.equals(that.hostName) : that.hostName != null) return false;
        if (interfaceName != null ? !interfaceName.equals(that.interfaceName) : that.interfaceName != null)
            return false;
        if (serverName != null ? !serverName.equals(that.serverName) : that.serverName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (destinationPort ^ (destinationPort >>> 32));
        result = 31 * result + (hostName != null ? hostName.hashCode() : 0);
        result = 31 * result + (interfaceName != null ? interfaceName.hashCode() : 0);
        result = 31 * result + (serverName != null ? serverName.hashCode() : 0);
        result = 31 * result + (apiName != null ? apiName.hashCode() : 0);
        return result;
    }

    long destinationPort;
    long failedRequestCount;
    String hostName;
    String interfaceName;
    String lastFailedRequestDescription;
    Date lastFailedRequestTime;
    String serverName;
    long totalRequestCount;
    String apiName;
    Date lastSuccessfulRequestTime;
    Date lastTransactionStartTime;
    Date lastTransactionEndTime;
    long lastTransactionProcessingTime;
    ConnectionStatus connectionStatus;
    TransactionOpen transactionOpen;
    long openSince;

    @Override
    public long getDestinationPort() {
        return this.destinationPort;
    }

    @Override
    public long getFailedRequestCount() {
        return this.failedRequestCount;
    }

    @Override
    public String getHostName() {
        return this.hostName;
    }

    @Override
    public String getInterfaceName() {
        return this.interfaceName;
    }

    @Override
    public String getLastFailedRequestDescription() {
        return this.lastFailedRequestDescription;
    }

    @Override
    public Date getLastFailedRequestTime() {
        return this.lastFailedRequestTime;
    }

    @Override
    public String getServerName() {
        return this.serverName;
    }


    @Override
    public long getTotalRequestCount() {
        return this.totalRequestCount;
    }




    @Override
    public String getMethodName() {
        return this.apiName;
    }



    @Override
    public Date getLastSuccessfulRequestTime() {
        return this.lastSuccessfulRequestTime;
    }

    @Override
    public Date getLastTransactionStartTime() {
        return lastTransactionStartTime;
    }



    @Override
    public Date getLastTransactionEndTime() {
        return lastTransactionEndTime;
    }



    @Override
    public long getLastTransactionProcessingTime() {
        return lastTransactionProcessingTime;
    }



    @Override
    public ConnectionStatus getTransactionStatus() {
        return this.connectionStatus;
    }



    public long getOpenSince() {
        return openSince;
    }



    public TransactionOpen getTransactionOpen() {
        return transactionOpen;
    }




}
