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

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import com.cisco.oss.foundation.monitoring.ConnectionStatus;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.SlidingTimeWindowReservoir;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceImp implements Service {

    public ServiceImp(String interfaceName, long port, String protocol, String serviceDescription, String apiName) {
        this.interfaceName = interfaceName;
        this.port = port;
        this.protocol = protocol;
        this.serviceDescription = serviceDescription;
        this.apiName = apiName;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceImp.class);
    private static final Logger AUDITOR = LoggerFactory.getLogger("audit." + ServiceImp.class.getName());

    Histogram tpsHistogram = new Histogram(new SlidingTimeWindowReservoir(30, TimeUnit.SECONDS));
    long totalRequestCount;
    AtomicLong totalStatisticsRequestCount = new AtomicLong();
    AtomicLong totalStatisticsStartTime = new AtomicLong();
    long failedRequestCount;
    long latency;
    String interfaceName;
    String lastFailedRequestDescription;
    Date lastFailedRequestTime = null;
    long port;
    String protocol;
    String serviceDescription;
    String apiName;
    Date lastSuccessfulRequestTime = new Date();
    Date lastTransactionStartTime =  new Date();
    Date lastTransactionEndTime =  new Date();
    long lastTransactionProcessingTime;
    ConnectionStatus connectionStatus;
    int usedThreads;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServiceImp that = (ServiceImp) o;

        if (port != that.port) return false;
        if (apiName != null ? !apiName.equals(that.apiName) : that.apiName != null) return false;
        if (interfaceName != null ? !interfaceName.equals(that.interfaceName) : that.interfaceName != null)
            return false;
        if (protocol != null ? !protocol.equals(that.protocol) : that.protocol != null) return false;
        if (serviceDescription != null ? !serviceDescription.equals(that.serviceDescription) : that.serviceDescription != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = interfaceName != null ? interfaceName.hashCode() : 0;
        result = 31 * result + (int) (port ^ (port >>> 32));
        result = 31 * result + (protocol != null ? protocol.hashCode() : 0);
        result = 31 * result + (serviceDescription != null ? serviceDescription.hashCode() : 0);
        result = 31 * result + (apiName != null ? apiName.hashCode() : 0);
        return result;
    }

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

//    public void setTotalRequestCount(long totalRequestCount) {
//        this.totalRequestCount = totalRequestCount;
//    }

//    public void setFailedRequestCount(long failedRequestCount) {
//        this.failedRequestCount = failedRequestCount;
//    }

//    public void setInterfaceName(String interfaceName) {
//        this.interfaceName = interfaceName;
//    }

//    public void setLastFailedRequestDescription(String lastFailedRequestDescription) {
//        this.lastFailedRequestDescription = lastFailedRequestDescription;
//    }

//    public void setLastFailedRequestTime(Date lastFailedRequestTime) {
//        this.lastFailedRequestTime = lastFailedRequestTime;
//    }

//    public void setPort(long port) {
//        this.port = port;
//    }

//    public void setProtocol(String protocol) {
//        this.protocol = protocol;
//    }

//    public void setServiceDescription(String serviceDescription) {
//        this.serviceDescription = serviceDescription;
//    }

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

//    public void setLastTransactionStartTime(Date lastTransactionStartTime) {
//        this.lastTransactionStartTime = lastTransactionStartTime;
//    }

    @Override
    public Date getLastTransactionEndTime() {
        return lastTransactionEndTime;
    }

//    public void setLastTransactionEndTime(Date lastTransactionEndTime) {
//        this.lastTransactionEndTime = lastTransactionEndTime;
//    }

    @Override
    public long getLastTransactionProcessingTime() {
        return lastTransactionProcessingTime;
    }

//    public void setLastTransactionProcessingTime(long lastTransactionProcessingTime) {
//        this.lastTransactionProcessingTime = lastTransactionProcessingTime;
//    }

    @Override
    public ConnectionStatus getTransactionStatus() {
        return this.connectionStatus;
    }

//    public void setConnectionStatus(ConnectionStatus connectionStatus) {
//        this.connectionStatus = connectionStatus;
//    }

    public AtomicLong getTotalStatisticsRequestCount() {
        return totalStatisticsRequestCount;
    }

    public double getTps() {
        return tpsHistogram.getSnapshot().getValues().length / 30;
    }

//    public void setTps(double tps) {
//        this.tps = tps;
//    }

    public AtomicLong getTotalStatisticsStartTime() {
        return totalStatisticsStartTime;
    }

    public int getUsedThreads() {
        return usedThreads;
    }

//    public void setUsedThreads(int usedThreads) {
//        this.usedThreads = usedThreads;
//    }

//    public void setApiName(String apiName) {
//        this.apiName = apiName;
//    }

//    public void setLastSuccessfulRequestTime(Date lastSuccessfulRequestTime) {
//        this.lastSuccessfulRequestTime = lastSuccessfulRequestTime;
//    }

    @Override
    public String toString() {
        return "ServiceImp{" +
                "totalRequestCount=" + totalRequestCount +
                ", totalStatisticsRequestCount=" + totalStatisticsRequestCount +
                ", totalStatisticsStartTime=" + totalStatisticsStartTime +
                ", failedRequestCount=" + failedRequestCount +
                ", latency=" + latency +
                ", interfaceName='" + interfaceName + '\'' +
                ", port=" + port +
                ", protocol='" + protocol + '\'' +
                ", serviceDescription='" + serviceDescription + '\'' +
                ", apiName='" + apiName + '\'' +
                ", usedThreads=" + usedThreads +
                '}';
    }
}
