/*
 * Copyright (c) NDS Limited 2010.
 * All rights reserved.
 * No part of this program may be reproduced, translated or transmitted,
 * in any form or by any means, electronic, mechanical, photocopying,
 * recording or otherwise, or stored in any retrieval system of any nature,
 * without written permission of the copyright holder.
 */

package com.cisco.vss.foundation.monitoring;

import java.net.UnknownHostException;

import org.apache.commons.configuration.Configuration;

import com.cisco.vss.foundation.configuration.ConfigurationFactory;
import com.cisco.vss.foundation.monitoring.component.config.ControlSettings;
import com.cisco.vss.foundation.monitoring.component.config.MatchPolicy;
import com.cisco.vss.foundation.monitoring.component.config.MonitorAndManagementSettings;
import com.cisco.vss.foundation.monitoring.component.config.OtherSettings;
import com.cisco.vss.foundation.monitoring.component.config.StartCommandSettings;
import com.cisco.vss.foundation.monitoring.component.config.StatusCommandSettings;
import com.cisco.vss.foundation.monitoring.component.config.StopCommandSettings;
import com.cisco.vss.foundation.monitoring.component.data.ComponentInfo;

/**
 * This class is responsible for maintaining configuration details.
 *
 * @author manojc
 */
final class AppProperties {
    private static final String DOMAIN_NAME = "nds.mx";
    private static final String MX_PORT = "service.mxagentRegistry.port";
    private static final String EXPORTED_PORT = "service.mxagentRegistry.innerPort";
    private static final String MONITOR_ENABLED = "service.mxagentRegistry.monitoringEnabled";
    private static final String CALCULATIONS_WINDOW = "service.mxagentRegistry.statisticsCalculationWindow";
    private static final String RMIREGISTRY_MAXHEAPSIZE = "service.mxagentRegistry.rmiregistryMaxHeapSize";
    private static final String IN_PROC_RMI = "service.mxagentRegistry.inProcess";
    private static final String AGENT_VERSION = "3.1.0-0";
    private static final String APP_NAME_NAMING_STANDARD = "^[a-zA-Z](([ a-zA-Z0-9_-])*[a-zA-Z0-9_-])*$";
    private static final String APP_INSTANCE_NAMING_STANDARD = "^[a-zA-Z0-9](([ a-zA-Z0-9_-])*[a-zA-Z0-9_-])*$";
    private static final String LOG_CONFIGURATION = "log4j.xml";
    private static final String ACCESS_FILE_PATH = null;
    private static final String PASSWORD_FILE_PATH = null;
    private static int agentPort;
    private static int rmiregistryMaxHeapSize;
    private static int exportedPort;
    private static boolean isInProcessRMI;
    private static String hostIP;
    private static String hostName;
    private static String javaVersion;
    private static String logConfiguration = LOG_CONFIGURATION;
    private static String accessFilePath = ACCESS_FILE_PATH;
    private static String passwordFilePath = PASSWORD_FILE_PATH;
    private static ComponentInfo componentInfo;
    private static boolean isMonitorEnabled;
    private static int statisticsCalculationWindow = 120;

    private AppProperties() {
    }

    static void loadProperties() {

        Configuration configuration = ConfigurationFactory.getConfiguration();
        agentPort = Integer.parseInt((String) configuration.getProperty(MX_PORT));
        exportedPort = Integer.parseInt((String) configuration.getProperty(EXPORTED_PORT));
        rmiregistryMaxHeapSize = configuration.getInt(RMIREGISTRY_MAXHEAPSIZE);
        javaVersion = System.getProperty("java.version");
        logConfiguration = System.getProperty(DOMAIN_NAME + ".log.configuration", LOG_CONFIGURATION);
        accessFilePath = System.getProperty(DOMAIN_NAME + ".access.file.path", ACCESS_FILE_PATH);
        passwordFilePath = System.getProperty(DOMAIN_NAME + ".password.file.path", PASSWORD_FILE_PATH);
        isInProcessRMI = Boolean.getBoolean((String) configuration.getProperty(IN_PROC_RMI));
        isMonitorEnabled = configuration.getBoolean(MONITOR_ENABLED, true);
        statisticsCalculationWindow = configuration.getInt(CALCULATIONS_WINDOW);
    }

    static void determineHostDetails() {
        try {
            hostIP = java.net.InetAddress.getLocalHost().getHostAddress();
            hostName = java.net.InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            MonitoringAgent.LOGGER.error("Failed to retrieve Host IP/Name", e);
        }
    }

    static void loadProperties(MXConfiguration conf) {
        loadProperties();
        agentPort = conf.getAgentPort();
        exportedPort = conf.getExportedPort();
    }

    static String getDomainName() {
        return DOMAIN_NAME;
    }

    static int getAgentPort() {
        return agentPort;
    }

    static String getAgentVersion() {
        return AGENT_VERSION;
    }

    static String getHostIP() {
        return hostIP;
    }

    static String getHostName() {
        return hostName;
    }

    static String getJavaVersion() {
        return javaVersion;
    }

    static String getLogConfiguration() {
        return logConfiguration;
    }

    static String getAppNameNamingStandard() {
        return APP_NAME_NAMING_STANDARD;
    }

    static String getAppInstanceNamingStandard() {
        return APP_INSTANCE_NAMING_STANDARD;
    }

    public static String getAccessFilePath() {
        return accessFilePath;
    }

    public static String getPasswordFilePath() {
        return passwordFilePath;
    }

    public static int getrmiregistryMaxHeapSize() {
        return rmiregistryMaxHeapSize;
    }

    public static int getExportedPort() {
        return exportedPort;
    }

    public static boolean isInProcessRMI() {
        return isInProcessRMI;
    }

    public static void redundancyModeChanged(RedundancyMode mode) {
        if (componentInfo != null) {
            componentInfo.setRedundancyMode(mode);
        }
    }

    public static void clearComponentInfo() {
        componentInfo = null;
    }

    public static boolean isMonitoringEnabled() {
        return isMonitorEnabled;
    }

    static void setMonitoringEnabled(boolean enable) {
        isMonitorEnabled = enable;
    }

    public static ComponentInfo getComponentInfo(MonitoringMXBean mxBean) {

        if (componentInfo != null)
            return componentInfo;
        componentInfo = new ComponentInfo();
        if (System.getenv("_RPM_SOFTWARE_NAME") != null) {
            componentInfo.setName(System.getenv("_RPM_SOFTWARE_NAME").toUpperCase());
        } else if (mxBean != null) {
            componentInfo.setName(mxBean.getName());
        }
        componentInfo.setFullName(getFullName(mxBean));
        componentInfo.setInstance(getInstanceName(mxBean));
        if (System.getenv("_ARTIFACT_VERSION") != null) {
            componentInfo.setVersion(System.getenv("_ARTIFACT_VERSION"));
        } else if (mxBean != null) {
            componentInfo.setVersion(mxBean.getVersion());
        }
        componentInfo.setRedundancyMode(RedundancyMode.StandAlone);
        return componentInfo;
    }

    private static String getFullName(MonitoringMXBean mxBean) {
        if (System.getenv("_FULL_SOFTWARE_NAME") != null) {
            return System.getenv("_FULL_SOFTWARE_NAME");
        } else if (System.getenv("_RPM_SOFTWARE_NAME") != null) {
            return System.getenv("_RPM_SOFTWARE_NAME").toUpperCase();
        } else if (mxBean != null) {
            return mxBean.getFullName();
        }
        return null;
    }

    private static String getInstanceName(MonitoringMXBean mxBean) {

        if (System.getProperty("app.instance.name") != null) {
            return System.getProperty("app.instance.name");
        } else if (System.getenv("_RPM_SOFTWARE_NAME") != null) {
            return System.getenv("_RPM_SOFTWARE_NAME").toUpperCase() + "Instance1";

        } else if (mxBean != null) {
            return mxBean.getInstance();
        }
        return null;

    }

    public static MonitorAndManagementSettings getMonitorAndManagementSettings() {
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

    public static int getStatisticsCalculationWindow() {
        return statisticsCalculationWindow;
    }
}
