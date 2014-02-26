package com.cisco.oss.foundation.monitoring;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceImp implements Service {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceImp.class);
    private static final Logger AUDITOR = LoggerFactory.getLogger("audit." + ServiceImp.class.getName());

    private long totalRequestCount;
    private AtomicLong totalStatisticsRequestCount = new AtomicLong();
    private AtomicLong totalStatisticsStartTime = new AtomicLong();
    private long failedRequestCount;
    private double tps;
    private long latency;
    private String interfaceName;
    private String lastFailedRequestDescription;
    private Date lastFailedRequestTime = null;
    private long port;
    private String protocol;
    private String serviceDescription;
    private String apiName;
    private Date lastSuccessfulRequestTime;
    private Date lastTransactionStartTime;
    private Date lastTransactionEndTime;
    private long lastTransactionProcessingTime;
    private ConnectionStatus connectionStatus;
    private int usedThreads;

    public boolean isEquals(ServiceDetails obj) {

        if (obj == null)
            return false;
        if (interfaceName == null) {
            if (obj.getInterfaceName() != null)
                return false;
        } else if (!interfaceName.equals(obj.getInterfaceName()))
            return false;
        if (port != obj.getPort())
            return false;
        if (protocol == null) {
            if (obj.getProtocol() != null)
                return false;
        } else if (!protocol.equals(obj.getProtocol()))
            return false;
        if (serviceDescription == null) {
            if (obj.getServiceDescription() != null)
                return false;
        } else if (!serviceDescription.equals(obj.getServiceDescription()))
            return false;
        if (apiName == null) {
            if (obj.getApiName() != null)
                return false;
        } else if (!apiName.equals(obj.getApiName()))
            return false;
        return true;
    }

    @Override
    public long getTotalRequestCount() {
        return this.totalRequestCount;
    }

    @Override
    public long getFailedRequestCount() {
        return this.failedRequestCount;
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
    public long getPort() {
        return this.port;
    }

    @Override
    public String getProtocol() {
        return this.protocol;
    }

    @Override
    public String getServiceDescription() {
        return this.serviceDescription;
    }

    public void setTotalRequestCount(long totalRequestCount) {
        this.totalRequestCount = totalRequestCount;
    }

    public void setFailedRequestCount(long failedRequestCount) {
        this.failedRequestCount = failedRequestCount;
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

    public void setPort(long port) {
        this.port = port;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
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

    public AtomicLong getTotalStatisticsRequestCount() {
        return totalStatisticsRequestCount;
    }

    public double getTps() {
        return tps;
    }

    public void setTps(double tps) {
        this.tps = tps;
    }

    public AtomicLong getTotalStatisticsStartTime() {
        return totalStatisticsStartTime;
    }

    public int getUsedThreads() {
        return usedThreads;
    }

    public void setUsedThreads(int usedThreads) {
        this.usedThreads = usedThreads;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public void setLastSuccessfulRequestTime(Date lastSuccessfulRequestTime) {
        this.lastSuccessfulRequestTime = lastSuccessfulRequestTime;
    }

}
