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
import com.cisco.oss.foundation.monitoring.component.config.MonitorAndManagementSettings;

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
