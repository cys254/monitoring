package com.cisco.oss.foundation.monitoring;

import java.util.Date;

public class ServerConnectionImp implements ServerConnection {

    private long destinationPort;
    private long failedRequestCount;
    private String hostName;
    private String interfaceName;
    private String lastFailedRequestDescription;
    private Date lastFailedRequestTime;
    private String serverName;
    private long totalRequestCount;
    private String apiName;
    private Date lastSuccessfulRequestTime;
    private Date lastTransactionStartTime;
    private Date lastTransactionEndTime;
    private long lastTransactionProcessingTime;
    private ConnectionStatus connectionStatus;
    private TransactionOpen transactionOpen;
    private long openSince;

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

    public void setDestinationPort(long destinationPort) {
        this.destinationPort = destinationPort;
    }

    public void setFailedRequestCount(long failedRequestCount) {
        this.failedRequestCount = failedRequestCount;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public void setLastFailedRequestDescription(String lastFailedRequestDescription) {
        this.lastFailedRequestDescription = lastFailedRequestDescription;
    }

    public void setLastFailedRequestTime(Date lastFailedRequestTime) {
        this.lastFailedRequestTime = lastFailedRequestTime;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }


    public void setTotalRequestCount(long totalRequestCount) {
        this.totalRequestCount = totalRequestCount;
    }


    public boolean isEquals(ServerConnectionDetails obj) {

        if (obj == null)
            return false;

        if (destinationPort != obj.getDestinationPort())
            return false;
        if (hostName == null) {
            if (obj.getHostName() != null)
                return false;
        } else if (!hostName.equals(obj.getHostName()))
            return false;
        if (interfaceName == null) {
            if (obj.getInterfaceName() != null)
                return false;
        } else if (!interfaceName.equals(obj.getInterfaceName()))
            return false;
        if (apiName == null) {
            if (obj.getApiName() != null)
                return false;
        } else if (!apiName.equals(obj.getApiName()))
            return false;
        if (serverName == null) {
            if (obj.getServerName() != null)
                return false;
        } else if (!serverName.equals(obj.getServerName()))
            return false;


        return true;
    }


    @Override
    public String getMethodName() {
        return this.apiName;
    }


    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public void setLastSuccessfulRequestTime(Date lastSuccessfulRequestTime) {
        this.lastSuccessfulRequestTime = lastSuccessfulRequestTime;
    }

    @Override
    public Date getLastSuccessfulRequestTime() {
        return this.lastSuccessfulRequestTime;
    }

    @Override
    public Date getLastTransactionStartTime() {
        return lastTransactionStartTime;
    }

    public void setLastTransactionStartTime(Date lastTransactionStartTime) {
        this.lastTransactionStartTime = lastTransactionStartTime;
    }

    @Override
    public Date getLastTransactionEndTime() {
        return lastTransactionEndTime;
    }

    public void setLastTransactionEndTime(Date lastTransactionEndTime) {
        this.lastTransactionEndTime = lastTransactionEndTime;
    }

    @Override
    public long getLastTransactionProcessingTime() {
        return lastTransactionProcessingTime;
    }

    public void setLastTransactionProcessingTime(long lastTransactionProcessingTime) {
        this.lastTransactionProcessingTime = lastTransactionProcessingTime;
    }

    @Override
    public ConnectionStatus getTransactionStatus() {
        return this.connectionStatus;
    }

    public void setConnectionStatus(ConnectionStatus connectionStatus) {
        this.connectionStatus = connectionStatus;
    }

    public long getOpenSince() {
        return openSince;
    }

    public void setOpenSince(long openSince) {
        this.openSince = openSince;
    }

    public TransactionOpen getTransactionOpen() {
        return transactionOpen;
    }

    public void setTransactionOpen(TransactionOpen transactionOpen) {
        this.transactionOpen = transactionOpen;
    }


}
