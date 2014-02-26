/*
 * Copyright (c) NDS Limited 2010.
 * All rights reserved.
 * No part of this program may be reproduced, translated or transmitted,
 * in any form or by any means, electronic, mechanical, photocopying,
 * recording or otherwise, or stored in any retrieval system of any nature,
 * without written permission of the copyright holder.
 */

package com.cisco.vss.foundation.monitoring;

import com.cisco.vss.foundation.configuration.ConfigurationFactory;
import com.cisco.vss.foundation.monitoring.component.config.MonitorAndManagementSettings;

/**
 * This class provides the default implementation for MXConfiguration. In this
 * implementation the configuration attributes are passed as constructor
 * parameters.
 *
 * @author manojc
 */
public class Configuration implements MXConfiguration {

    private static final String MX_PORT = "service.mxagentRegistry.port";
    private static final String EXPORTED_PORT = "service.mxagentRegistry.innerPort";

    private int agentPort;
    private int exportedPort;
    private MonitorAndManagementSettings monitorAndManagementSettings;

    /**
     * Creates a new Configuration object with agentPort=3000 and
     * exportedPort=4000. <br>
     * It is recommended to use the overloaded constructor
     * {@link #Configuration(int exportedPort)} since agent port is standardized
     * to 3000, and exportedPort can vary. <br>
     */
    public Configuration() {
        org.apache.commons.configuration.Configuration configuration = ConfigurationFactory.getConfiguration();

        this.agentPort = Integer.parseInt((String) configuration.getProperty(MX_PORT));
        this.exportedPort = Integer.parseInt((String) configuration.getProperty(EXPORTED_PORT));
        this.monitorAndManagementSettings = null;
    }

    /**
     * Creates a new Configuration object with agentPort=3000 and the supplied
     * values for exportedPort. <br>
     *
     * @param exportedPort
     */
    public Configuration(int exportedPort) {
        org.apache.commons.configuration.Configuration configuration = ConfigurationFactory.getConfiguration();
        this.agentPort = Integer.parseInt((String) configuration.getProperty(MX_PORT));
        this.exportedPort = exportedPort;
        this.monitorAndManagementSettings = null;
    }

    /**
     * Creates a new Configuration object with the supplied values for agentPort
     * and exportedPort. <br>
     * It is recommended to use the overloaded constructor
     * {@link #Configuration(int exportedPort)} since agent port is standardized
     * to 3000, and exportedPort can vary. <br>
     *
     * @param agentPort
     * @param exportedPort
     */
    public Configuration(int agentPort, int exportedPort) {
        this.agentPort = agentPort;
        this.exportedPort = exportedPort;
        this.monitorAndManagementSettings = null;
    }

    @Override
    public int getAgentPort() {
        return this.agentPort;
    }

    @Override
    public int getExportedPort() {
        return this.exportedPort;
    }

    @Override
    @Deprecated
    public MonitorAndManagementSettings getMonitorAndManagementSettings() {
        return this.monitorAndManagementSettings;
    }

    /**
     * @param agentPort the agentPort to set
     */
    public void setAgentPort(int agentPort) {
        this.agentPort = agentPort;
    }

    /**
     * @param exportedPort the exportedPort to set
     */
    public void setExportedPort(int exportedPort) {
        this.exportedPort = exportedPort;
    }

    /**
     * @param monitorAndManagementSettings the monitorAndManagementSettings to set
     */
    @Deprecated
    public void setMonitorAndManagementSettings(MonitorAndManagementSettings monitorAndManagementSettings) {
        this.monitorAndManagementSettings = monitorAndManagementSettings;
    }
}
