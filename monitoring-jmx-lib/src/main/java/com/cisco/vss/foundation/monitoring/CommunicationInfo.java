package com.cisco.vss.foundation.monitoring;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum CommunicationInfo {

    INSTANCE;
    static final Logger LOGGER = LoggerFactory.getLogger(CommunicationInfo.class.getName());
    static final Logger AUDITOR = LoggerFactory.getLogger("audit." + CommunicationInfo.class.getName());
    private static final ThreadLocal<Long> lastTransactionStartTimeThreadLocal = new ThreadLocal<Long>();
    private static CommunicationInfo communicationInfo;
    private ServiceInfo serviceInfo;
    private ConnectionInfo connectionInfo;

    private CommunicationInfo() {
        serviceInfo = ServiceInfo.getServiceInfo();
        MonitoringAgent.setServiceInfo(serviceInfo);
        connectionInfo = ConnectionInfo.getConnectionInfo();
        MonitoringAgent.setConnectionInfo(connectionInfo);
        startStatisticsThread();
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

    private static void startStatisticsThread() {
        Thread statisticsThread = new Thread(new MonitoringStatisticsRunnable(), "MonitoringStatistics");
        statisticsThread.start();
    }

    public ServiceInfo getServiceInfo() {
        return serviceInfo;
    }

    public ConnectionInfo getConnetionInfo() {
        return connectionInfo;
    }

    private Service createService(ServiceDetails serviceDetails) {

        ServiceImp service = new ServiceImp();
        service.setFailedRequestCount(0);
        service.setInterfaceName(serviceDetails.getInterfaceName());
        service.setLastFailedRequestDescription("");
        service.setLastFailedRequestTime(null);
        service.setPort(serviceDetails.getPort());
        service.setProtocol(serviceDetails.getProtocol());
        service.setServiceDescription(serviceDetails.getServiceDescription());
        service.setTotalRequestCount(0);
        service.setApiName(serviceDetails.getApiName());
        service.setLastTransactionStartTime(new Date());
//		service.startStatisticsThread();
        return service;
    }

    private ServerConnection createServerConnection(ServerConnectionDetails connetionDetails) {
        ServerConnectionImp serverConnection = new ServerConnectionImp();
        serverConnection.setDestinationPort(connetionDetails.getDestinationPort());
        serverConnection.setFailedRequestCount(0);
        serverConnection.setHostName(connetionDetails.getHostName());
        serverConnection.setInterfaceName(connetionDetails.getInterfaceName());
        serverConnection.setLastFailedRequestDescription("");
        serverConnection.setLastFailedRequestTime(null);
        serverConnection.setServerName(connetionDetails.getServerName());
        serverConnection.setTotalRequestCount(0);
        serverConnection.setApiName(connetionDetails.getApiName());
        return serverConnection;

    }

    public void transactionStarted(ServiceDetails serviceDetails, String apiName, int usedThreads) {

        if (AppProperties.isMonitoringEnabled()) {
            try {

                ServiceDetails tmp = (ServiceDetails) serviceDetails.clone();
                tmp.setApiName(apiName);
                ServiceImp service = (ServiceImp) findService(tmp);
                if (service == null) {
                    service = (ServiceImp) createService(tmp);
                    MonitoringAgent.getServiceInfo().addService(service);
                }
                service.setTotalRequestCount(service.getTotalRequestCount() + 1);
                service.getTotalStatisticsRequestCount().incrementAndGet();
                service.setLastTransactionStartTime(new Date());
                service.setUsedThreads(usedThreads);
                lastTransactionStartTimeThreadLocal.set(System.currentTimeMillis());

            } catch (Exception e) {
//				LOGGER.error("Problem in adding service details" + e);
                AUDITOR.error("Problem in adding service details" + e);
            }
        }
    }

    public void transactionStarted(ServiceDetails serviceDetails, String apiName) {
        transactionStarted(serviceDetails, apiName, -1);
    }

    private Service findService(ServiceDetails serviceDetails) {
        ServiceImp service = null;
        for (int index = 0; index < serviceInfo.getServices().size(); index++) {
            ServiceImp candidateService = (ServiceImp) serviceInfo.getServices().get(index);
            if (candidateService.isEquals(serviceDetails)) {
                service = candidateService;
                break;
            }
        }
        return service;
    }

    public synchronized void transactionFinished(ServiceDetails serviceDetails, String apiName, boolean isFailed, String description) {
        if (AppProperties.isMonitoringEnabled()) {
            try {

                serviceDetails.setApiName(apiName);
                ServiceImp service = (ServiceImp) findService(serviceDetails);
                /*if (service == null) {
                    service = (ServiceImp) createService(serviceDetails);
					MonitoringAgent.getServiceInfo().addService(service);
				}
				service.setTotalRequestCount(service.getTotalRequestCount() + 1);*/
                service.setLastTransactionEndTime(new Date());

                if (isFailed == true) {
                    service.setLastFailedRequestTime(new Date());
                    service.setFailedRequestCount(service.getFailedRequestCount() + 1);
                    service.setLastFailedRequestDescription(description);

                } else {
                    service.setLastSuccessfulRequestTime(new Date());
                }
                if (service.getLastFailedRequestTime() == null || (service.getLastSuccessfulRequestTime().getTime() > service.getLastFailedRequestTime().getTime())) {
                    service.setConnectionStatus(ConnectionStatus.Healthy);
                } else {
                    service.setConnectionStatus(ConnectionStatus.Broken);
                }
//				service.setLastTransactionProcessingTime(service.getLastTransactionEndTime().getTime() - service.getLastTransactionStartTime().getTime());
                long start = lastTransactionStartTimeThreadLocal.get();
                long end = System.currentTimeMillis();
                service.setLastTransactionProcessingTime(end - start);
            } catch (Exception e) {
                LOGGER.trace("Problem in adding service details" + e.getMessage());
                AUDITOR.trace("Problem in adding service details" + e.getMessage());
            }
        }
    }

    public void transactionStarted(ServerConnectionDetails connetionDetails, String apiName) {
        if (AppProperties.isMonitoringEnabled()) {
            try {
                connetionDetails.setApiName(apiName);
                ServerConnectionImp connetion = (ServerConnectionImp) findServerConnections(connetionDetails);
                if (connetion == null) {
                    connetion = (ServerConnectionImp) createServerConnection(connetionDetails);
                    MonitoringAgent.getConnectionInfo().addConnetion(connetion);
                }

                connetion.setLastTransactionStartTime(new Date());
                connetion.setTotalRequestCount(connetion.getTotalRequestCount() + 1);
                connetion.setOpenSince(0);
                connetion.setTransactionOpen(TransactionOpen.Open);

            } catch (Exception e) {
                LOGGER.error("Problem in adding server connetions details" + e.getMessage());
                AUDITOR.error("Problem in adding server connetions details" + e.getMessage());
            }
        }
    }

    private ServerConnection findServerConnections(ServerConnectionDetails connetionDetails) {
        ServerConnectionImp connetion = null;
        for (int index = 0; index < connectionInfo.getServerConnections().size(); index++) {
            ServerConnectionImp candidateConnetion = (ServerConnectionImp) connectionInfo.getServerConnections().get(index);
            if (candidateConnetion.isEquals(connetionDetails)) {
                connetion = candidateConnetion;
                break;
            }
        }
        return connetion;
    }

    public synchronized void transactionFinished(ServerConnectionDetails connetionDetails, String apiName, boolean isFailed, String description) {
        if (AppProperties.isMonitoringEnabled()) {
            try {
                connetionDetails.setApiName(apiName);
                ServerConnectionImp connetion = (ServerConnectionImp) findServerConnections(connetionDetails);
                connetion.setLastTransactionEndTime(new Date());
                if (isFailed == true) {
                    connetion.setLastFailedRequestTime(new Date());
                    connetion.setFailedRequestCount(connetion.getFailedRequestCount() + 1);
                    connetion.setLastFailedRequestDescription(description);
                } else {
                    connetion.setLastSuccessfulRequestTime(new Date());
                }
                if (connetion.getLastFailedRequestTime() == null || (connetion.getLastSuccessfulRequestTime().getTime() > connetion.getLastFailedRequestTime().getTime())) {
                    connetion.setConnectionStatus(ConnectionStatus.Healthy);
                } else {
                    connetion.setConnectionStatus(ConnectionStatus.Broken);
                }
                connetion.setTransactionOpen(TransactionOpen.Closed);
                connetion.setLastTransactionProcessingTime(connetion.getLastTransactionEndTime().getTime() - connetion.getLastTransactionStartTime().getTime());
                connetion.setOpenSince(0);
            } catch (Exception e) {
                LOGGER.trace("Problem in adding server connetions details" + e.getMessage());
                AUDITOR.trace("Problem in adding server connetions details" + e.getMessage());
            }
        }
    }

    private static final class MonitoringStatisticsRunnable implements Runnable {

        @Override
        public void run() {

            while (true) {
                int calculationWindow = AppProperties.getStatisticsCalculationWindow();

                List<Service> services = CommunicationInfo.getCommunicationInfo().getServiceInfo().getServices();
                for (Service service : services) {
                    if (service instanceof ServiceImp) {
                        ServiceImp serviceImp = (ServiceImp) service;
                        long start = serviceImp.getTotalStatisticsStartTime().getAndSet(System.currentTimeMillis());
                        long end = System.currentTimeMillis();
                        long total = serviceImp.getTotalStatisticsRequestCount().getAndSet(0);
                        double elapsed = (end - start) / 1000D;

                        double tps = total / elapsed;
                        serviceImp.setTps(tps);

                        try {
                            if (AUDITOR.isDebugEnabled()) {
                                final Runtime runtime = Runtime.getRuntime();
                                AUDITOR.debug(String.format("JVM Available Resources: Total Memory: %d, Max Memory: %d, Free Memory: %d, Available Processors: %d", runtime.totalMemory(), runtime.maxMemory(), runtime.freeMemory(), runtime.availableProcessors()));
                            }
                        } catch (RuntimeException e) {
                            AUDITOR.error("problem logging jvm monitoring data", e);
                        }
                    }
                }


                try {
                    Thread.sleep(TimeUnit.SECONDS.toMillis(calculationWindow));
                } catch (InterruptedException e) {
                    LOGGER.trace("interrupted", e);
                }

            }

        }


    }
}
