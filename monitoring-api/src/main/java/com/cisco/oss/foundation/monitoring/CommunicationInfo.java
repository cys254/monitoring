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

package com.cisco.oss.foundation.monitoring;

import com.cisco.oss.foundation.configuration.ConfigurationFactory;
import com.cisco.oss.foundation.monitoring.serverconnection.ConnectionInfo;
import com.cisco.oss.foundation.monitoring.serverconnection.ServerConnectionDetails;
import com.cisco.oss.foundation.monitoring.serverconnection.ServerConnectionImp;
import com.cisco.oss.foundation.monitoring.services.Service;
import com.cisco.oss.foundation.monitoring.services.ServiceDetails;
import com.cisco.oss.foundation.monitoring.services.ServiceImp;
import com.cisco.oss.foundation.monitoring.services.ServiceInfo;
import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public enum CommunicationInfo {

    INSTANCE;
    private Configuration configuration = ConfigurationFactory.getConfiguration();
    public final ExecutorService actorsThreadPool = Executors.newCachedThreadPool();
    static final Logger LOGGER = LoggerFactory.getLogger(CommunicationInfo.class.getName());
    private static final ThreadLocal<Long> lastTransactionStartTimeThreadLocal = new ThreadLocal<Long>();
    private static CommunicationInfo communicationInfo;


    private CommunicationInfo() {
//        startStatisticsThread();
    }

    public static CommunicationInfo getCommunicationInfo() {
//		if (communicationInfo == null) {
//			communicationInfo = new CommunicationInfo();
//			serviceInfo = ServiceInfo.getServiceInfo();
//			MonitoringAgent.setServiceInfo(serviceInfo);
//			connectionInfo = ConnectionInfo.getConnectionInfo();
//			MonitoringAgent.setConnectionInfo(connectionInfo);
//			startStatisticsThread();

//		}
//		return communicationInfo;
        return INSTANCE;
    }

//    private static void startStatisticsThread() {
//        Thread statisticsThread = new Thread(new MonitoringStatisticsRunnable(), "MonitoringStatistics");
//        statisticsThread.start();
//    }



//    private Service createService(ServiceDetails serviceDetails) {
//
//        ServiceImp service = new ServiceImp();
//        service.setFailedRequestCount(0);
//        service.setInterfaceName(serviceDetails.getInterfaceName());
//        service.setLastFailedRequestDescription("");
//        service.setLastFailedRequestTime(null);
//        service.setPort(serviceDetails.getPort());
//        service.setProtocol(serviceDetails.getProtocol());
//        service.setServiceDescription(serviceDetails.getServiceDescription());
//        service.setTotalRequestCount(0);
//        service.setApiName(serviceDetails.getApiName());
//        service.setLastTransactionStartTime(new Date());
////		service.startStatisticsThread();
//        return service;
//    }

//    private ServerConnection createServerConnection(ServerConnectionDetails connetionDetails) {
//        ServerConnectionImp serverConnection = new ServerConnectionImp();
//        serverConnection.setDestinationPort(connetionDetails.getDestinationPort());
//        serverConnection.setFailedRequestCount(0);
//        serverConnection.setHostName(connetionDetails.getHostName());
//        serverConnection.setInterfaceName(connetionDetails.getInterfaceName());
//        serverConnection.setLastFailedRequestDescription("");
//        serverConnection.setLastFailedRequestTime(null);
//        serverConnection.setServerName(connetionDetails.getServerName());
//        serverConnection.setTotalRequestCount(0);
//        serverConnection.setApiName(connetionDetails.getApiName());
//        return serverConnection;
//
//    }

    public void transactionStarted(ServiceDetails serviceDetails, String apiName, int usedThreads) {

        if (configuration.getBoolean(FoundationMonitoringConstants.MONITOR_ENABLED)) {
            try {
                Service service = new ServiceImp(serviceDetails.getInterfaceName(), serviceDetails.getPort(), serviceDetails.getProtocol(), serviceDetails.getServiceDescription(), apiName);
                ServiceInfo.INSTANCE.serviceActor.tell().startTransaction(service, usedThreads);
//                StartServiceTransaction startServiceTransaction = new StartServiceTransaction(service, usedThreads);
//                serviceActor.tell(startServiceTransaction, null);
            } catch (Exception e) {
                LOGGER.error("Problem in adding service details" + e);
            }
        }
    }

    public void transactionStarted(ServiceDetails serviceDetails, String apiName) {
        transactionStarted(serviceDetails, apiName, -1);
    }

//    private Service findService(ServiceDetails serviceDetails) {
//        ServiceImp service = null;
//        for (int index = 0; index < serviceInfo.getServices().size(); index++) {
//            ServiceImp candidateService = (ServiceImp) serviceInfo.getServices().get(index);
//            if (candidateService.isEquals(serviceDetails)) {
//                service = candidateService;
//                break;
//            }
//        }
//        return service;


    public synchronized void transactionFinished(ServiceDetails serviceDetails, String apiName, boolean isFailed, String description) {
        if (configuration.getBoolean(FoundationMonitoringConstants.MONITOR_ENABLED)) {
            try {

                Service service = new ServiceImp(serviceDetails.getInterfaceName(), serviceDetails.getPort(), serviceDetails.getProtocol(), serviceDetails.getServiceDescription(), apiName);
                ServiceInfo.INSTANCE.serviceActor.tell().endTransaction(service, isFailed, description);

            } catch (Exception e) {
                LOGGER.error("Problem in adding service details" + e.getMessage());
            }
        }
    }

    public void transactionStarted(ServerConnectionDetails connetionDetails, String apiName) {
        if (configuration.getBoolean(FoundationMonitoringConstants.MONITOR_ENABLED)) {
            try {

                ServerConnectionImp serverConnection = new ServerConnectionImp(connetionDetails.getDestinationPort(), connetionDetails.getHostName(), connetionDetails.getInterfaceName(), connetionDetails.getServerName(), apiName);
                ConnectionInfo.INSTANCE.serverConnectorActor.tell().startTransaction(serverConnection);

            } catch (Exception e) {
                LOGGER.error("Problem in adding server connections details" + e.getMessage());
            }
        }
    }

//    private ServerConnection findServerConnections(ServerConnectionDetails connetionDetails) {
//        ServerConnectionImp connetion = null;
//        for (int index = 0; index < connectionInfo.getServerConnections().size(); index++) {
//            ServerConnectionImp candidateConnetion = (ServerConnectionImp) connectionInfo.getServerConnections().get(index);
//            if (candidateConnetion.isEquals(connetionDetails)) {
//                connetion = candidateConnetion;
//                break;
//            }
//        }
//        return connetion;
//    }

    public synchronized void transactionFinished(ServerConnectionDetails connetionDetails, String apiName, boolean isFailed, String description) {
        if (configuration.getBoolean(FoundationMonitoringConstants.MONITOR_ENABLED)) {
            try {
                ServerConnectionImp serverConnection = new ServerConnectionImp(connetionDetails.getDestinationPort(), connetionDetails.getHostName(), connetionDetails.getInterfaceName(), connetionDetails.getServerName(), apiName);
                ConnectionInfo.INSTANCE.serverConnectorActor.tell().endTransaction(serverConnection, isFailed, description);
            } catch (Exception e) {
                LOGGER.error("Problem in adding server connections details" + e.getMessage());
            }
        }
    }

}
