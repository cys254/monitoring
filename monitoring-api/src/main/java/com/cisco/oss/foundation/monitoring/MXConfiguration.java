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

/**
 * Objects of this type can be passed to MonitoringAgent in order to override
 * default configuration attributes.
 *
 * @author manojc
 */
public interface MXConfiguration {

    /**
     * Gets the port number of RMI Registry on which the agent is registered.
     *
     * @return The port number of RMI Registry on which the agent is registered
     */
    int getAgentPort();

    /**
     * Gets the port number on which the Monitoring information is exported.
     *
     * @return The port number on which the Monitoring information is exported
     */
    int getExportedPort();

    /**
     * Gets the MonitorAndManagementSettings object.
     *
     * @return MonitorAndManagementSettings
     */
    @Deprecated
    MonitorAndManagementSettings getMonitorAndManagementSettings();
}
