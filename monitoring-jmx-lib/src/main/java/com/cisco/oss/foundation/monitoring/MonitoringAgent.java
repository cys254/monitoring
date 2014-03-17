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

import com.cisco.oss.foundation.monitoring.component.config.MonitorAndManagementSettings;
import com.cisco.oss.foundation.monitoring.component.data.ComponentInfo;
import com.cisco.oss.foundation.monitoring.exception.AgentAlreadyRegisteredException;
import com.cisco.oss.foundation.monitoring.exception.AgentRegistrationException;
import com.cisco.oss.foundation.monitoring.exception.IncompatibleClassException;
import com.cisco.oss.foundation.monitoring.notification.NotificationInfoMXBean;
import com.cisco.oss.foundation.monitoring.notification.NotificationMXBean;
import com.cisco.oss.foundation.monitoring.notification.NotificationSender;
import com.cisco.oss.foundation.monitoring.serverconnection.ConnectionInfo;
import com.cisco.oss.foundation.monitoring.serverconnection.ServerConnectionActor;
import com.cisco.oss.foundation.monitoring.serverconnection.ServerConnectionActorImpl;
import com.cisco.oss.foundation.monitoring.services.ServiceActor;
import com.cisco.oss.foundation.monitoring.services.ServiceActorImpl;
import com.cisco.oss.foundation.monitoring.services.ServiceInfo;
import fi.jumi.actors.ActorRef;
import fi.jumi.actors.ActorThread;
import fi.jumi.actors.Actors;
import fi.jumi.actors.MultiThreadedActors;
import fi.jumi.actors.eventizers.dynamic.DynamicEventizerProvider;
import fi.jumi.actors.listeners.CrashEarlyFailureHandler;
import fi.jumi.actors.listeners.NullMessageListener;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * MonitoringAgent is the main class of monitoring library. It allows
 * Java components to plug into the Monitoring infrastructure allowing them
 * to expose the monitoring information in the form of JMX.
 *
 * @author manojc
 * @see MonitoringMXBean
 */
public enum MonitoringAgent {
    INSTANCE;

    private static final String COLON = ":";
    private static Map<String, MonitoringAgent> registeredAgents = new HashMap<String, MonitoringAgent>();
    static final Logger LOGGER = LoggerFactory.getLogger(MonitoringAgent.class.getName());
    private MBeanServer mbs;
    private ServerInfo serverInfo;
    private ObjectName appObjectName;
    private ObjectName servicesObjectName;
    private ObjectName connetctionsObjectName;
    private ObjectName monitorAndManagementSettingsObjectName = null;
    private ObjectName componentInfoObjectName = null;
    private JMXConnectorServer rmis;
    private boolean isRegistered = false;
    private boolean isNotificationRegistered = false;
    private Thread serverThread;
    private MonitoringMXBean exposedObject = null;
    private String exposedServiceURL = null;
    private String exposedObjectName = null;
    private Map<String, String> jmxEnvironmentMap = null;
    private JMXServiceURL jurl;
    private MXConfiguration configuration = null;
//    private static ServiceInfo serviceInfo;
//    private static ConnectionInfo connectionInfo;
    private boolean isComponentRegisted = false;
    private boolean isInfraRegisted = false;
    private ObjectName notificationObjectName;
    private static NotificationMXBean notificationDetails = null;

    /**
     * This is the default constructor for <code>MonitoringAgent</code>.
     * <code>register</code> method needs to be explicitly called in order to
     * initialize the monitoring infrastructure.
     *
     * @see #register(MonitoringMXBean)
     */
    private MonitoringAgent() {

    }

//    /**
//     * This constructor not just creates an instance of
//     * <code>MonitoringAgent</code> but also initializes the monitoring
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
/*	public MonitoringAgent(MonitoringMXBean mXBean) throws AgentAlreadyRegisteredException, AgentRegistrationException,
            IncompatibleClassException {

		AppProperties.loadProperties();
		LOGGER.debug("New MonitoringAgent object getting created");
		register(mXBean);
	}*/

//    /**
//     * This constructor not just creates an instance of
//     * <code>MonitoringAgent</code> but also initializes the monitoring
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
	/*public MonitoringAgent(MonitoringMXBean mXBean, MXConfiguration configuration)
			throws AgentAlreadyRegisteredException, AgentRegistrationException, IncompatibleClassException {

		AppProperties.loadProperties(configuration);
		setConfiguration(configuration);
		LOGGER.debug("New MonitoringAgent object getting created");
		register(mXBean);
	}*/
    public static MonitoringAgent getInstance() {
        return INSTANCE;
    }



    /**
     * Two or more instance/modules of applications sharing same instance of JVM
     * can also share an instance of MonitoringAgent. One instance/module can
     * register into MonitoringAgent by supplying an authentication key. Other
     * instance/module can access already registered instance of MonitoringAgent
     * only if it knows the authentication key in addition to the name and
     * instance of the application.
     *
     * @param name     Registered application name
     * @param instance Registered instance
     * @param authKey  Authentication key supplied while registering
     * @return Instance of MonitoringAgent
     * @see MonitoringAgent#register()
     */
    public static MonitoringAgent getRegistedAgent(String name, String instance, String authKey) {
        LOGGER.debug("Getting MonitoringAgent instance for (" + name + ", " + instance + ")");

        return registeredAgents.get(name + COLON + instance + COLON + authKey);
    }

    /**
     * Returns true if MonitoringAgent is already registered, false otherwise
     *
     * @return true if MonitoringAgent is already registered, false otherwise
     * @see MonitoringAgent#register()
     */
    public synchronized boolean isRegistered() {
        return isRegistered;
    }

    /**
     * Returns JMX Service URL if MonitoringAgent is already registered, null
     * otherwise.
     *
     * @return JMX Service URL if MonitoringAgent is already registered, null
     * otherwise
     * @see MonitoringAgent#isRegistered()
     */
    public synchronized String getExposedServiceURL() {
        return exposedServiceURL;
    }

    /**
     * Returns ObjectName of the MBean if MonitoringAgent is already registered,
     * null otherwise.
     *
     * @return ObjectName of the MBean if MonitoringAgent is already registered,
     * null otherwise
     * @see MonitoringAgent#isRegistered()
     */
    public synchronized String getExposedObjectName() {
        return exposedObjectName;
    }

    /**
     * Returns MonitoringMXBean object passed to the register method.
     *
     * @return MonitoringMXBean object passed to the register method
     * @see com.cisco.oss.foundation.monitoring.MonitoringAgent#register()
     */
    public synchronized MonitoringMXBean getExposedObject() {
        return this.exposedObject;
    }

    /**
     * @return the configuration
     */
    public MXConfiguration getConfiguration() {
        return configuration;
    }

    /**
     * Sets the configuration to be used while registering the Monitoring Agent.
     * This method should be called before the agent is registered.
     *
     * @param configuration
     * @throws com.cisco.oss.foundation.monitoring.exception.AgentAlreadyRegisteredException if the agent is already registered
     */
    @SuppressWarnings("deprecation")
    public void setConfiguration(MXConfiguration configuration) throws AgentAlreadyRegisteredException {
        if (isRegistered()) {
            LOGGER.warn("Attempt to set the configuration after MonitoringAgent is registered.");
            throw new AgentAlreadyRegisteredException("Configuration should be set before the agent is registered");
        }

        this.configuration = configuration;
        AppProperties.loadProperties(configuration);
        LOGGER.info("Setting MonitoringAgent configuration. agentPort=" + configuration.getAgentPort()
                + ", exportedPort=" + configuration.getExportedPort() + ", monitorAndManagementSettings "
                + ((configuration.getMonitorAndManagementSettings() == null) ? "not " : "") + "set");
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
     * @see MonitoringAgent#unregister()
     */
    public synchronized void register(MonitoringMXBean mxBean) throws AgentAlreadyRegisteredException,
            AgentRegistrationException, IncompatibleClassException {
        AppProperties.loadProperties();
        if (AppProperties.isMonitoringEnabled() == false) {
            LOGGER.info("Monitoring is disabled");
            return;
        }
        if (isComponentRegisted == true) {
            throw new AgentAlreadyRegisteredException();
        }
        register(mxBean, null);
        isComponentRegisted = true;
    }

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

    public synchronized void register()

    {
        AppProperties.loadProperties();
        if (AppProperties.isMonitoringEnabled() == false) {
            LOGGER.info("Monitoring is disabled");
            return;
        }
        if (isRegistered == true) {
            return;
        }
        MonitoringMXBean defaultRegistration = new DefaultMonitoringMXBean();
        DefaultMonitoringMXBean.setEnvironment("Dummy", "Dummy");
        try {
            register(defaultRegistration, null);
            isInfraRegisted = true;
        } catch (IncompatibleClassException e) {
            LOGGER.error("Interface name is not according to JMX standard");
        } catch (AgentRegistrationException e) {
            LOGGER.trace("Infra is already register with monitoring", e);
        } catch (AgentAlreadyRegisteredException e) {
            LOGGER.trace("Infra is already register with monitoring", e);
        }


    }

    /**
     * Registers the mxBean object and exposes it to the outside world. Any
     * changes to this object will automatically be reflected and seen by the
     * monitoring applications.
     *
     * @param mxBean
     * @param authKey Authentication key, required in case MonitoringAgent instance
     *                needs to be shared
     * @throws com.cisco.oss.foundation.monitoring.exception.AgentAlreadyRegisteredException
     * @throws com.cisco.oss.foundation.monitoring.exception.AgentRegistrationException
     * @throws com.cisco.oss.foundation.monitoring.exception.IncompatibleClassException
     * @see MonitoringAgent#unregister()
     * @see MonitoringAgent#register()
     */

    public synchronized void register(MonitoringMXBean mxBean, String authKey) throws AgentAlreadyRegisteredException,
            AgentRegistrationException, IncompatibleClassException {

        LOGGER.info("Registering MonitoringAgent for " + AppProperties.getComponentInfo(mxBean).getName() + COLON + AppProperties.getComponentInfo(mxBean).getInstance());
        AppProperties.loadProperties();
        final URL jarUrl = getSpringUrl();

        System.setProperty("java.rmi.server.codebase", jarUrl.toString().replace(" ", "%20"));
        if (isRegistered == true && isComponentRegisted == false) {
            unregister();
        }

        try {
            AppProperties.determineHostDetails();

            Utility.validateJavaVersion();
            this.exposedObject = mxBean;
            Utility.validateGenericParams(this.exposedObject);

            String serviceURL = Utility.getServiceURL(this.exposedObject);
            String strAppObjectName = null;

            strAppObjectName = javaRegister(mxBean, serviceURL);
            Runtime.getRuntime().addShutdownHook(new ShutdownHookThread());

            registeredAgents.put(AppProperties.getComponentInfo(mxBean).getName() + COLON + AppProperties.getComponentInfo(mxBean).getInstance() + COLON + authKey, this);
            exposedServiceURL = serviceURL;
            exposedObjectName = strAppObjectName;
            LOGGER.info("MonitoringAgent successfully registered. Java Version=" + AppProperties.getJavaVersion()
                    + ", URL=" + exposedServiceURL + ", ObjectName=" + exposedObjectName);
            TransactionMonitorThread.getInstance().startTread();
        } catch (MalformedURLException muEx) {
            String message = "Failed to register MonitoringAgent. Name/Instance attributes does not follow the naming standard.";
            LOGGER.error(message, muEx);
            throw new AgentRegistrationException(message, muEx);
        } catch (MalformedObjectNameException monEx) {
            String message = "Failed to register MonitoringAgent. Name/Instance attributes does not follow the naming standard.";
            LOGGER.error(message, monEx);
            throw new AgentRegistrationException(message, monEx);
        } catch (InstanceAlreadyExistsException existsEx) {
            String message = "Failed to register MonitoringAgent. There is an another instance of MonitoringAgent already registered for "
                    + AppProperties.getComponentInfo(mxBean).getName() + COLON + AppProperties.getComponentInfo(mxBean).getInstance();
            LOGGER.error(message, existsEx);
            throw new AgentAlreadyRegisteredException(message, existsEx);
        } catch (NotCompliantMBeanException notComplEx) {
            String message = "Failed to register MonitoringAgent. The MonitoringMXBean object tried to register is not JMX compliant.";
            LOGGER.error(message, notComplEx);
            throw new IncompatibleClassException(message, notComplEx);
        } catch (MBeanRegistrationException mrE) {
            String message = "Failed to register MonitoringAgent. Check the log for more details.";
            LOGGER.error(message, mrE);
            throw new AgentRegistrationException(message, mrE);
        } catch (IOException ioEx) {
            String message = "Failed to register MonitoringAgent. Check the log for more details.";
            LOGGER.error(message, ioEx);
            throw new AgentRegistrationException(message, ioEx);
        }
    }

    private String javaRegister(MonitoringMXBean mxBean, String serviceURL) throws MalformedObjectNameException,
            IOException, InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
        serverInfo = new ServerInfo(mxBean);

        String strAppObjectName = Utility.getObjectName("Application", this.exposedObject);

        jurl = new JMXServiceURL(serviceURL);
        appObjectName = new ObjectName(strAppObjectName);

        jmxEnvironmentMap = null;

        if (!RMIRegistryManager.isRMIRegistryRunning(AppProperties.getAgentPort())) {
            RMIRegistryManager.startRMIRegistry(AppProperties.getAgentPort());
        } else {
            LOGGER.info("rmiregistry is already running on port " + AppProperties.getAgentPort());
        }

        String serviceName = serviceURL.substring(serviceURL.indexOf("jmxrmi/"));
        if (isServiceExported(serviceName)) {
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

        isRegistered = true;

        if (mxBean instanceof INotifier) {
            INotifier notifier = (INotifier) mxBean;
            notifier.setNotificationSender(serverInfo);
        }

        serverThread = new ServerRecoveryDaemon();
        serverThread.start();

        return strAppObjectName;
    }

    private void registerComponentInfo() throws MalformedObjectNameException, InstanceAlreadyExistsException,
            MBeanRegistrationException, NotCompliantMBeanException {
        ComponentInfo componentInfo = AppProperties.getComponentInfo(this.exposedObject);
        String strMonComponentObjectName = Utility.getObjectName("ComponentInfo", this.exposedObject);
        this.componentInfoObjectName = new ObjectName(strMonComponentObjectName);
        mbs.registerMBean(componentInfo, this.componentInfoObjectName);

    }

    private void registerMonitoringConfiguration() throws MalformedObjectNameException, InstanceAlreadyExistsException,
            MBeanRegistrationException, NotCompliantMBeanException {
        MonitorAndManagementSettings monitorAndManagementSettings = AppProperties.getMonitorAndManagementSettings();
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
     * Unregisters <code>MonitoringAgent</code> instance and makes the
     * application unavailable for monitoring. Calling unregister() on a
     * MonitoringAgent that is already unregistered will have no effect.
     *
     * @throws com.cisco.oss.foundation.monitoring.exception.AgentRegistrationException
     * @see MonitoringAgent#register()
     */
    public synchronized void unregister() throws AgentRegistrationException {
        if (!isRegistered) {
            return;
        }

        LOGGER.info("Unregistering MonitoringAgent for " + AppProperties.getComponentInfo(this.exposedObject).getName() + COLON
                + AppProperties.getComponentInfo(this.exposedObject).getInstance());

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

            isRegistered = false;
            LOGGER.info("MonitoringAgent successfully unregistered. Java Version=" + AppProperties.getJavaVersion()
                    + ", URL=" + exposedServiceURL + ", ObjectName=" + exposedObjectName);

            exposedServiceURL = null;
            exposedObjectName = null;
            isComponentRegisted = false;
            isInfraRegisted = false;
            isNotificationRegistered = false;
            AppProperties.clearComponentInfo();
        } catch (IOException ex) {
            String message = "Failed to unregister MonitoringAgent. Check the log for more details.";
            LOGGER.error(message, ex);
            throw new AgentRegistrationException(message, ex);
        } catch (InstanceNotFoundException ex) {
            String message = "Failed to unregister MonitoringAgent. Check the log for more details.";
            LOGGER.error(message, ex);
            throw new AgentRegistrationException(message, ex);
        } catch (MBeanRegistrationException ex) {
            String message = "Failed to unregister MonitoringAgent. Check the log for more details.";
            LOGGER.error(message, ex);
            throw new AgentRegistrationException(message, ex);
        } catch (LinkageError ex) {

        }
    }

    public NotificationSender getNotificationSender() {
        return serverInfo;
    }

    public MonitoringAgentMXBean getAgentDetails() {
        return serverInfo;
    }

    private boolean isServiceExported(String serviceName) {
        boolean isJmxServiceExported = RMIRegistryManager.isServiceExported(AppProperties
                .getAgentPort(), serviceName);
        return isJmxServiceExported;
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
                LOGGER.debug("ShutdownHookThread failed to unregister MonitoringAgent.");
            }
        }
    }

    private class ServerRecoveryDaemon extends Thread {
        private long timeInterval = 20000;
        private static final String SERVER_RECOVERY_DAEMON_POLLING = "foundation.mx.recoverydaemon.polling";

        public ServerRecoveryDaemon() {
            timeInterval = Long.parseLong(System.getProperty(SERVER_RECOVERY_DAEMON_POLLING, "20000"));
        }

        @Override
        public void run() {
            LOGGER.info("ServerRecoveryDaemon started.");
            while (true) {
                try {
                    Thread.sleep(timeInterval);
                    synchronized (MonitoringAgent.this) {
                        String serviceName = exposedServiceURL.substring(exposedServiceURL.indexOf("jmxrmi/"));
                        if (!isServiceExported(serviceName)) {
                            LOGGER.warn("RMI Connector Server " + serviceName + " is found to be not running on port "
                                    + AppProperties.getAgentPort() + ", reregistering RMI Connector Server.");

                            try {
                                rmis.stop();
                            } catch (IOException e) {
                                LOGGER.error("Failed to stop JMX RMI Connector server.");
                            }

                            RMIRegistryManager.startRMIRegistry(AppProperties.getAgentPort());

                            rmis = JMXConnectorServerFactory.newJMXConnectorServer(jurl, jmxEnvironmentMap, mbs);
                            rmis.start();
                            LOGGER.info("Recreated RMI Connector Server.");
                        }
                    }
                } catch (InterruptedException e) {
                    break;
                } catch (Exception e) {
                    LOGGER.error("ServerRecoveryDaemon failed to reregister RMI Connector Server on port "
                            + AppProperties.getAgentPort() + ". Next attempt will be made after " + timeInterval
                            + "ms.", e);
                }
            }

            LOGGER.info("ServerRecoveryDaemon stopped.");
        }
    }

//    public static ServiceInfo getServiceInfo() {
//        return serviceInfo;
//    }

//    public static ConnectionInfo getConnectionInfo() {
//        return connectionInfo;
//    }

//    public static void setConnectionInfo(ConnectionInfo connetionInfo) {
//        MonitoringAgent.connectionInfo = connetionInfo;
//    }

//    public static void setServiceInfo(ServiceInfo serviceInfo) {
//        MonitoringAgent.serviceInfo = serviceInfo;
//    }

    public boolean isComponentRegisted() {
        return isComponentRegisted;
    }

    public void setComponentRegisted(boolean isComponentRegisted) {
        this.isComponentRegisted = isComponentRegisted;
    }

    public boolean isInfraRegisted() {
        return isInfraRegisted;
    }

    public void setInfraRegisted(boolean isInfraRegisted) {
        this.isInfraRegisted = isInfraRegisted;
    }




}
