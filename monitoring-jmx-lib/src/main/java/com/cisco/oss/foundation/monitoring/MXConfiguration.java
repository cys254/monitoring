/*
 * Copyright (c) NDS Limited 2010.
 * All rights reserved.
 * No part of this program may be reproduced, translated or transmitted,
 * in any form or by any means, electronic, mechanical, photocopying,
 * recording or otherwise, or stored in any retrieval system of any nature,
 * without written permission of the copyright holder.
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
