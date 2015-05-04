/*
 * Copyright 2015 Cisco Systems, Inc.
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
import com.cisco.oss.foundation.monitoring.component.config.*;
import com.cisco.oss.foundation.monitoring.component.data.ComponentInfo;
import com.cisco.oss.foundation.monitoring.exception.AgentAlreadyRegisteredException;
import com.cisco.oss.foundation.monitoring.exception.AgentRegistrationException;
import com.cisco.oss.foundation.monitoring.exception.IncompatibleClassException;
import com.cisco.oss.foundation.monitoring.notification.NotificationInfoMXBean;
import com.cisco.oss.foundation.monitoring.notification.NotificationMXBean;
import com.cisco.oss.foundation.monitoring.notification.NotificationSender;
import com.cisco.oss.foundation.monitoring.serverconnection.ConnectionInfo;
import com.cisco.oss.foundation.monitoring.services.ServiceInfo;
import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.support.RemoteInvocation;

import javax.management.*;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * RMIMonitoringAgent is the main class of monitoring library. It allows
 * Java components to plug into the Monitoring infrastructure allowing them
 * to expose the monitoring information in the form of JMX.
 *
 * @author manojc
 * @see MonitoringMXBean
 */
public enum RMIMonitoringAgent implements MonitoringAgent {
    INSTANCE;
    private static final AtomicBoolean firstTime = new AtomicBoolean(true);
    private static final String COLON = ":";
    private static Logger LOGGER = LoggerFactory.getLogger(RMIMonitoringAgent.class.getName());
    private static NotificationMXBean notificationDetails = null;
    private Configuration configuration;
    //    private static Map<String, RMIMonitoringAgent> registeredAgents = new HashMap<String, RMIMonitoringAgent>();
    private MBeanServer mbs;
    private ServerInfo serverInfo;
    private ObjectName appObjectName;
    private ObjectName servicesObjectName;
    private ObjectName connetctionsObjectName;
    private ObjectName monitorAndManagementSettingsObjectName = null;
    private ObjectName componentInfoObjectName = null;
    private JMXConnectorServer rmis;
    //    private boolean isRegistered = false;
    private boolean isNotificationRegistered = false;
    private Thread serverThread;
    private MonitoringMXBean exposedObject = null;
    private String exposedServiceURL = null;
    private String exposedObjectName = null;
    private Map<String, String> jmxEnvironmentMap = null;
    private JMXServiceURL jurl;
    //    private static ServiceInfo serviceInfo;
//    private static ConnectionInfo connectionInfo;
//    private boolean isComponentRegisted = false;
//    private boolean isInfraRegisted = false;
    private ObjectName notificationObjectName;

    /**
     * This is the default constructor for <code>RMIMonitoringAgent</code>.
     * <code>register</code> method needs to be explicitly called in order to
     * initialize the monitoring infrastructure.
     *
     * @see #register(MonitoringMXBean)
     */
    private RMIMonitoringAgent() {

    }

//    /**
//     * This constructor not just creates an instance of
//     * <code>RMIMonitoringAgent</code> but also initializes the monitoring
//     * infrastructure and registers the mxBean object.
//     *
//     * @param mXBean
//     *
//     * @throws com.cisco.oss.foundation.monitoring.exception.AgentAlreadyRegisteredException
//     * @throws com.cisco.oss.foundation.monitoring.exception.AgentRegistrationException
//     * @throws com.cisco.oss.foundation.monitoring.exception.IncompatibleClassException
//     *
//     * @see MonitoringMXBean
//     */
/*	public RMIMonitoringAgent(MonitoringMXBean mXBean) throws AgentAlreadyRegisteredException, AgentRegistrationException,
            IncompatibleClassException {

		AppProperties.loadProperties();
		LOGGER.debug("New RMIMonitoringAgent object getting created");
		register(mXBean);
	}*/

    //    /**
//     * This constructor not just creates an instance of
//     * <code>RMIMonitoringAgent</code> but also initializes the monitoring
//     * infrastructure with the custom configuration, and registers the mxBean
//     * object.
//     *
//     * @param mXBean
//     * @param configuration
//     * @throws com.cisco.oss.foundation.monitoring.exception.AgentAlreadyRegisteredException
//     * @throws com.cisco.oss.foundation.monitoring.exception.AgentRegistrationException
//     * @throws com.cisco.oss.foundation.monitoring.exception.IncompatibleClassException
//     * @see MonitoringMXBean
//     * @see MXConfiguration
//     * @see com.cisco.oss.foundation.monitoring.component.config.MonitorAndManagementSettingsMXBean
//     */
    /*public RMIMonitoringAgent(MonitoringMXBean mXBean, MXConfiguration configuration)
            throws AgentAlreadyRegisteredException, AgentRegistrationException, IncompatibleClassException {

		AppProperties.loadProperties(configuration);
		setConfiguration(configuration);
		LOGGER.debug("New RMIMonitoringAgent object getting created");
		register(mXBean);
	}*/
//    public static RMIMonitoringAgent getInstance() {
//        return INSTANCE;
//    }


    //    /**
//     * Two or more instance/modules of applications sharing same instance of JVM
//     * can also share an instance of RMIMonitoringAgent. One instance/module can
//     * register into RMIMonitoringAgent by supplying an authentication key. Other
//     * instance/module can access already registered instance of RMIMonitoringAgent
//     * only if it knows the authentication key in addition to the name and
//     * instance of the application.
//     *
//     * @param name     Registered application name
//     * @param instance Registered instance
//     * @param authKey  Authentication key supplied while registering
//     * @return Instance of RMIMonitoringAgent
//     */
//    public static RMIMonitoringAgent getRegistedAgent(String name, String instance, String authKey) {
//        LOGGER.debug("Getting RMIMonitoringAgent instance for (" + name + ", " + instance + ")");
//
//        return registeredAgents.get(name + COLON + instance + COLON + authKey);
//    }
    private static URL getSpringUrl() {

        final ProtectionDomain protectionDomain = RemoteInvocation.class.getProtectionDomain();
        String errorMessage = "creation of jar file failed for UNKNOWN reason";

        if (protectionDomain == null) {

            errorMessage = "class protection domain is null";

        } else {

            final CodeSource codeSource = protectionDomain.getCodeSource();

            if (codeSource == null) {

                errorMessage = "class code source is null";

            } else {

                final URL location = codeSource.getLocation();

                if (location == null) {

                    errorMessage = "class code source location is null";

                } else {

                    return location;

                }

            }

        }

        throw new UnsupportedOperationException(errorMessage);

    }

    private static MonitorAndManagementSettings getMonitorAndManagementSettings() {
        MonitorAndManagementSettings settings = new MonitorAndManagementSettings();
        ControlSettings controlSettings = new ControlSettings();
        OtherSettings otherSettings = new OtherSettings();
        controlSettings.setStartCommandSettings(getStartCommandSettings());
        controlSettings.setStopCommandSettings(getStopCommandSettings());
        controlSettings.setStatusCommandSettings(getStatusCommandSettings());
        otherSettings.setCommandLine(System.getenv("_PROCESS_ID_LIST"));
        settings.setControlSettings(controlSettings);
        settings.setOtherSettings(otherSettings);
        return settings;
    }

    private static StartCommandSettings getStartCommandSettings() {
        StartCommandSettings startCommandSettings = new StartCommandSettings();
        startCommandSettings.setCommand(System.getenv("_START_COMMAND"));
        startCommandSettings.setMatchPolicy(MatchPolicy.PartialMatch);
        startCommandSettings.setSuccessIndication(System.getenv("_START_COMMAND_SUCCESS"));
        return startCommandSettings;
    }

    private static StopCommandSettings getStopCommandSettings() {
        StopCommandSettings stopCommandSettings = new StopCommandSettings();
        stopCommandSettings.setCommand(System.getenv("_STOP_COMMAND"));
        stopCommandSettings.setMatchPolicy(MatchPolicy.PartialMatch);
        stopCommandSettings.setSuccessIndication(System.getenv("_STOP_COMMAND_SUCCESS"));
        return stopCommandSettings;
    }

    private static StatusCommandSettings getStatusCommandSettings() {
        StatusCommandSettings statusCommandSettings = new StatusCommandSettings();
        statusCommandSettings.setCommand(System.getenv("_STATUS_COMMAND"));
        statusCommandSettings.setMatchPolicy(MatchPolicy.PartialMatch);
        statusCommandSettings.setUpIndication(System.getenv("_UP_INDICATION"));
        statusCommandSettings.setDownIndication(System.getenv("_DOWN_INDICATION"));
        return statusCommandSettings;
    }


    /**
     * Registers the mxBean object and exposes it to the outside world. Any
     * changes to this object will automatically be reflected and seen by the
     * monitoring applications.
     *
     * @param mxBean
     * @throws com.cisco.oss.foundation.monitoring.exception.AgentAlreadyRegisteredException
     * @throws com.cisco.oss.foundation.monitoring.exception.AgentRegistrationException
     * @throws com.cisco.oss.foundation.monitoring.exception.IncompatibleClassException
     * @see RMIMonitoringAgent#unregister()
     */

    private synchronized void register(MonitoringMXBean mxBean) throws AgentAlreadyRegisteredException,
            AgentRegistrationException, IncompatibleClassException {

        LOGGER.info("Registering RMIMonitoringAgent for " + ComponentInfo.INSTANCE.getName() + COLON + ComponentInfo.INSTANCE.getInstance());
        final URL jarUrl = getSpringUrl();

        System.setProperty("java.rmi.server.codebase", jarUrl.toString().replace(" ", "%20"));


        try {

            Utility.validateJavaVersion();
            this.exposedObject = mxBean;
            Utility.validateGenericParams(this.exposedObject);

            String serviceURL = Utility.getServiceURL(configuration, this.exposedObject);
            String strAppObjectName = null;

            strAppObjectName = javaRegister(mxBean, serviceURL);
            Runtime.getRuntime().addShutdownHook(new ShutdownHookThread());

//            registeredAgents.put(ComponentInfo.INSTANCE.getName() + COLON + ComponentInfo.INSTANCE.getInstance() + COLON + authKey, this);
            exposedServiceURL = serviceURL;
            exposedObjectName = strAppObjectName;
            LOGGER.info("RMIMonitoringAgent successfully registered. Java Version=" + System.getProperty("java.version")
                    + ", URL=" + exposedServiceURL + ", ObjectName=" + exposedObjectName);
            TransactionMonitorThread.getInstance().startTread();
        } catch (MalformedURLException muEx) {
            String message = "Failed to register RMIMonitoringAgent. Name/Instance attributes does not follow the naming standard.";
            LOGGER.error(message, muEx);
            throw new AgentRegistrationException(message, muEx);
        } catch (MalformedObjectNameException monEx) {
            String message = "Failed to register RMIMonitoringAgent. Name/Instance attributes does not follow the naming standard.";
            LOGGER.error(message, monEx);
            throw new AgentRegistrationException(message, monEx);
        } catch (InstanceAlreadyExistsException existsEx) {
            String message = "Failed to register RMIMonitoringAgent. There is an another instance of RMIMonitoringAgent already registered for "
                    + ComponentInfo.INSTANCE.getName() + COLON + ComponentInfo.INSTANCE.getInstance();
            LOGGER.error(message, existsEx);
            throw new AgentAlreadyRegisteredException(message, existsEx);
        } catch (NotCompliantMBeanException notComplEx) {
            String message = "Failed to register RMIMonitoringAgent. The MonitoringMXBean object tried to register is not JMX compliant.";
            LOGGER.error(message, notComplEx);
            throw new IncompatibleClassException(message, notComplEx);
        } catch (MBeanRegistrationException mrE) {
            String message = "Failed to register RMIMonitoringAgent. Check the log for more details.";
            LOGGER.error(message, mrE);
            throw new AgentRegistrationException(message, mrE);
        } catch (IOException ioEx) {
            String message = "Failed to register RMIMonitoringAgent. Check the log for more details.";
            LOGGER.error(message, ioEx);
            throw new AgentRegistrationException(message, ioEx);
        }
    }

    private String javaRegister(MonitoringMXBean mxBean, String serviceURL) throws MalformedObjectNameException,
            IOException, InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
        serverInfo = new ServerInfo(mxBean, configuration);

        String strAppObjectName = Utility.getObjectName("Application", this.exposedObject);

        jurl = new JMXServiceURL(serviceURL);
        appObjectName = new ObjectName(strAppObjectName);

        jmxEnvironmentMap = null;

        int agentPort = configuration.getInt(FoundationMonitoringConstants.MX_PORT);
        if (!RMIRegistryManager.isRMIRegistryRunning(configuration, agentPort)) {
            RMIRegistryManager.startRMIRegistry(configuration, agentPort);
        } else {
            LOGGER.info("rmiregistry is already running on port " + agentPort);
        }

        String serviceName = serviceURL.substring(serviceURL.indexOf("jmxrmi/"));
        if (isServiceExported(configuration, serviceName)) {
            MonitoringClient client = new MonitoringClient(serviceURL, strAppObjectName);
            if (client.connect()) {
                client.disconnect();
            } else {
                jmxEnvironmentMap = Utility.prepareJmxEnvironmentMap();
                LOGGER.info("Found a stale entry for " + serviceName + " in rmiregistry , it will be overwritten");
            }
        }
        mbs = ManagementFactory.getPlatformMBeanServer();
        rmis = JMXConnectorServerFactory.newJMXConnectorServer(jurl, jmxEnvironmentMap, mbs);

        mbs.registerMBean(mxBean, appObjectName);
        registerComponentInfo();
        registerMonitoringConfiguration();
        registerServices();
        registerConnections();
        registerNotificationDetails();
        rmis.start();

        if (mxBean instanceof INotifier) {
            INotifier notifier = (INotifier) mxBean;
            notifier.setNotificationSender(serverInfo);
        }

        serverThread = new ServerRecoveryDaemon();
        serverThread.start();

        return strAppObjectName;
    }

    public ObjectInstance registerMBean(Object object) {

        String objName = FoundationMonitoringConstants.DOMAIN_NAME + ":name=" + ComponentInfo.INSTANCE.getName();

        if ((ComponentInfo.INSTANCE.getInstance() != null) && (!ComponentInfo.INSTANCE.getInstance().trim().equals(""))) {
            objName = objName + ",instance=" + ComponentInfo.INSTANCE.getInstance();
        }

        objName = objName + ",contentSource=" + object.getClass().getSimpleName();

        try {
            return mbs.registerMBean(object, new ObjectName(objName));
        } catch (Exception e) {
            LOGGER.error("Problem registering bean: {}. error is: {}", objName, e, e);
            throw new IllegalArgumentException(e);
        }
    }

    private void registerComponentInfo() throws MalformedObjectNameException, InstanceAlreadyExistsException,
            MBeanRegistrationException, NotCompliantMBeanException {
        String strMonComponentObjectName = Utility.getObjectName("ComponentInfo", this.exposedObject);
        this.componentInfoObjectName = new ObjectName(strMonComponentObjectName);
        mbs.registerMBean(ComponentInfo.INSTANCE, this.componentInfoObjectName);

    }

    private void registerMonitoringConfiguration() throws MalformedObjectNameException, InstanceAlreadyExistsException,
            MBeanRegistrationException, NotCompliantMBeanException {
        MonitorAndManagementSettings monitorAndManagementSettings = getMonitorAndManagementSettings();
        String strMonConfigObjectName = Utility.getObjectName("MonitorAndManagementSettings", this.exposedObject);
        this.monitorAndManagementSettingsObjectName = new ObjectName(strMonConfigObjectName);
        mbs.registerMBean(monitorAndManagementSettings, this.monitorAndManagementSettingsObjectName);

    }

    private void registerServices() {
        try {
            String strMonConfigObjectName = Utility.getObjectName("ServiceInfo", this.exposedObject);
            servicesObjectName = new ObjectName(strMonConfigObjectName);
            mbs.registerMBean(ServiceInfo.INSTANCE, servicesObjectName);
        } catch (MalformedObjectNameException e) {
            LOGGER.trace("Failed to register services" + e.getMessage());
        } catch (InstanceAlreadyExistsException e) {
            LOGGER.trace("Failed to register services" + e.getMessage());
        } catch (MBeanRegistrationException e) {
            LOGGER.trace("Failed to register services" + e.getMessage());
        } catch (NotCompliantMBeanException e) {
            LOGGER.trace("Failed to register services" + e.getMessage());
        }

    }

    public void sendNotification(NotificationInfoMXBean data) {
        try {
            if (!isNotificationRegistered) {

                registerNotificationDetails();
            }
            notificationDetails.sendNotification(data);
        } catch (Exception e) {
            LOGGER.trace("Failed to invoke sendNotification Method" + e.getMessage());
        }
    }

    private void registerNotificationDetails() {
        try {
            notificationDetails = new NotificationMXBean();
            String strNotifObjectName = Utility.getObjectName("NotificationMXBean", this.exposedObject);
            notificationObjectName = new ObjectName(strNotifObjectName);
            mbs.registerMBean(notificationDetails, notificationObjectName);
            isNotificationRegistered = true;
        } catch (MalformedObjectNameException e) {
            LOGGER.trace("Failed to register services" + e.getMessage());
        } catch (InstanceAlreadyExistsException e) {
            LOGGER.trace("Failed to register services" + e.getMessage());
        } catch (MBeanRegistrationException e) {
            LOGGER.trace("Failed to register services" + e.getMessage());
        } catch (NotCompliantMBeanException e) {
            LOGGER.trace("Failed to register services" + e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.trace("Failed to register services" + e.getMessage());
        }

    }


    private void registerConnections() {
        try {
            String strMonConfigObjectName = Utility.getObjectName("ConnectionInfo", this.exposedObject);
            connetctionsObjectName = new ObjectName(strMonConfigObjectName);
            mbs.registerMBean(ConnectionInfo.INSTANCE, connetctionsObjectName);
        } catch (MalformedObjectNameException e) {
            LOGGER.trace("Failed to register connetctions" + e.getMessage());
        } catch (InstanceAlreadyExistsException e) {
            LOGGER.trace("Failed to register connetctions" + e.getMessage());
        } catch (MBeanRegistrationException e) {
            LOGGER.trace("Failed to register connetctions" + e.getMessage());
        } catch (NotCompliantMBeanException e) {
            LOGGER.trace("Failed to register connetctions" + e.getMessage());
        }
    }

    private void unregisterMonitoringConfiguration() throws InstanceNotFoundException, MBeanRegistrationException {
        if (this.monitorAndManagementSettingsObjectName != null) {
            mbs.unregisterMBean(this.monitorAndManagementSettingsObjectName);
        }

    }

    private void unregisterServices() {
        try {
            if (this.servicesObjectName != null)
                mbs.unregisterMBean(this.servicesObjectName);
        } catch (InstanceNotFoundException e) {
            LOGGER.trace("Failed to unregister services" + e.getMessage());
        } catch (MBeanRegistrationException e) {
            LOGGER.trace("Failed to unregister services" + e.getMessage());
        }
    }

    private void unregisterComponentInfo() {
        try {
            if (this.connetctionsObjectName != null)
                mbs.unregisterMBean(this.componentInfoObjectName);
        } catch (InstanceNotFoundException e) {
            LOGGER.trace("Failed to unregister ComponentInfo" + e.getMessage());
        } catch (MBeanRegistrationException e) {
            LOGGER.trace("Failed to unregister ComponentInfo" + e.getMessage());
        }
    }

    private void unregisterConnetctions() {
        try {
            if (this.connetctionsObjectName != null)
                mbs.unregisterMBean(this.connetctionsObjectName);
        } catch (InstanceNotFoundException e) {
            LOGGER.trace("Failed to unregister server connetctions" + e.getMessage());
        } catch (MBeanRegistrationException e) {
            LOGGER.trace("Failed to unregister server connetctions" + e.getMessage());
        }
    }

    /**
     * Unregisters <code>RMIMonitoringAgent</code> instance and makes the
     * application unavailable for monitoring. Calling unregister() on a
     * RMIMonitoringAgent that is already unregistered will have no effect.
     *
     * @throws com.cisco.oss.foundation.monitoring.exception.AgentRegistrationException
     */
    private void unregister() throws AgentRegistrationException {


        LOGGER.info("Unregistering RMIMonitoringAgent for " + ComponentInfo.INSTANCE.getName() + COLON
                + ComponentInfo.INSTANCE.getInstance());

        try {
            serverThread.interrupt();
            rmis.stop();
            mbs.unregisterMBean(appObjectName);
            unregisterComponentInfo();
            unregisterMonitoringConfiguration();
            unregisterServices();
            unregisterConnetctions();

            if (notificationObjectName != null && mbs.isRegistered(notificationObjectName)) {
                mbs.unregisterMBean(notificationObjectName);
            }

            LOGGER.info("RMIMonitoringAgent successfully unregistered. Java Version=" + System.getProperty("java.version")
                    + ", URL=" + exposedServiceURL + ", ObjectName=" + exposedObjectName);

            exposedServiceURL = null;
            exposedObjectName = null;
            isNotificationRegistered = false;
        } catch (IOException ex) {
            String message = "Failed to unregister RMIMonitoringAgent. Check the log for more details.";
            LOGGER.error(message, ex);
            throw new AgentRegistrationException(message, ex);
        } catch (InstanceNotFoundException ex) {
            String message = "Failed to unregister RMIMonitoringAgent. Check the log for more details.";
            LOGGER.error(message, ex);
            throw new AgentRegistrationException(message, ex);
        } catch (MBeanRegistrationException ex) {
            String message = "Failed to unregister RMIMonitoringAgent. Check the log for more details.";
            LOGGER.error(message, ex);
            throw new AgentRegistrationException(message, ex);
        } catch (LinkageError ex) {

        }
    }

    private NotificationSender getNotificationSender() {
        return serverInfo;
    }

    private MonitoringAgentMXBean getAgentDetails() {
        return serverInfo;
    }

    private boolean isServiceExported(Configuration configuration, String serviceName) {
        boolean isJmxServiceExported = RMIRegistryManager.isServiceExported(configuration, configuration.getInt(FoundationMonitoringConstants.MX_PORT), serviceName);
        return isJmxServiceExported;
    }

    /**
     * empty implementation. this is just needed for the initial init and registration of the monitoring agent.
     * this function can be called repeatedly with no side effects.
     */
    public void register() {
        register(ConfigurationFactory.getConfiguration());
    }

    public void register(Configuration configuration) {
        this.configuration = configuration;
        if (firstTime.compareAndSet(true, false)) {
            try {
                CommunicationInfo.getCommunicationInfo().setConfiguration(configuration);
                register(new DefaultMonitoringMXBean());
            } catch (Exception e) {
                LOGGER.error("error creating monitoring agent: {}", e, e);
            }
        }
    }

    final class ShutdownHookThread extends Thread {
        public void run() {
            try {
                LOGGER.debug("ShutdownHookThread called.");
                unregister();
                ServiceInfo.INSTANCE.serviceActorThread.stop();
                ConnectionInfo.INSTANCE.serverConnectionActorThread.stop();
                CommunicationInfo.INSTANCE.actorsThreadPool.shutdown();
            } catch (AgentRegistrationException agentregEx) {
                LOGGER.debug("ShutdownHookThread failed to unregister RMIMonitoringAgent.");
            }
        }
    }

    private class ServerRecoveryDaemon extends Thread {
        private static final String SERVER_RECOVERY_DAEMON_POLLING = "foundation.mx.recoverydaemon.polling";
        private long timeInterval = 20000;

        public ServerRecoveryDaemon() {
            timeInterval = Long.parseLong(System.getProperty(SERVER_RECOVERY_DAEMON_POLLING, "20000"));
        }

        @Override
        public void run() {
            LOGGER.info("ServerRecoveryDaemon started.");
            while (true) {
                try {
                    Thread.sleep(timeInterval);
                    synchronized (RMIMonitoringAgent.this) {
                        String serviceName = exposedServiceURL.substring(exposedServiceURL.indexOf("jmxrmi/"));
                        if (!isServiceExported(configuration, serviceName)) {
                            LOGGER.warn("RMI Connector Server " + serviceName + " is found to be not running on port "
                                    + configuration.getInt(FoundationMonitoringConstants.MX_PORT) + ", reregistering RMI Connector Server.");

                            try {
                                rmis.stop();
                            } catch (IOException e) {
                                LOGGER.error("Failed to stop JMX RMI Connector server.");
                            }

                            RMIRegistryManager.startRMIRegistry(configuration, configuration.getInt(FoundationMonitoringConstants.MX_PORT));

                            rmis = JMXConnectorServerFactory.newJMXConnectorServer(jurl, jmxEnvironmentMap, mbs);
                            rmis.start();
                            LOGGER.info("Recreated RMI Connector Server.");
                        }
                    }
                } catch (InterruptedException e) {
                    break;
                } catch (Exception e) {
                    LOGGER.error("ServerRecoveryDaemon failed to reregister RMI Connector Server on port "
                            + configuration.getInt(FoundationMonitoringConstants.MX_PORT) + ". Next attempt will be made after " + timeInterval
                            + "ms.", e);
                }
            }

            LOGGER.info("ServerRecoveryDaemon stopped.");
        }
    }


}
