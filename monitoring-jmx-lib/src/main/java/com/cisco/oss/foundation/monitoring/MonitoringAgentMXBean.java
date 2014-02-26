/*
 * Copyright (c) NDS Limited 2010.
 * All rights reserved.
 * No part of this program may be reproduced, translated or transmitted,
 * in any form or by any means, electronic, mechanical, photocopying,
 * recording or otherwise, or stored in any retrieval system of any nature,
 * without written permission of the copyright holder.
 */

package com.cisco.oss.foundation.monitoring;

import java.util.Date;

/**
 * It is the MXBean interface which will be implemented internally by the
 * NDSMXAgent.
 *
 * @author manojc
 * @see com.cisco.oss.foundation.monitoring.MonitoringAgent
 */
public interface MonitoringAgentMXBean {
    /**
     * Gets the Date when MonitoringAgent was registered.
     *
     * @return Date when MonitoringAgent was registered.
     */
    Date getAgentStartTime();

    /**
     * Gets Version of the MonitoringAgent.
     *
     * @return Version of the MonitoringAgent.
     */
    String getAgentVersion();

    /**
     * Gets the RMI port on which MonitoringAgent is registered.
     *
     * @return RMI port on which MonitoringAgent is registered.
     */
    int getAgentPort();

    /**
     * Gets the port number on which the Monitoring information is exported.
     *
     * @return The port number on which the Monitoring information is exported.
     */
    int getExportedPort();

    /**
     * Gets the IP of the host machine where MonitoringAgent runs.
     *
     * @return IP of the host machine where MonitoringAgent runs.
     */
    String getHostIP();

    /**
     * Gets the network name of the host machine where MonitoringAgent runs.
     *
     * @return Network name of the host machine where MonitoringAgent runs.
     */
    String getHostName();

    /**
     * Allows the management information attributes to be written remotely.
     *
     * @param path  Path of the attribute to be set.
     * @param value Value to be set with
     * @throws Exception
     */
    void setAttributeValue(String path, String value) throws Exception;

    /**
     * Allows the management information attributes to be written remotely.
     *
     * @param path  Path of the attribute to be set.
     * @param value Value to be set with
     * @throws Exception
     */
    void setAttributeValue(String path, int value) throws Exception;

    /**
     * Allows the management information attributes to be written remotely.
     *
     * @param path  Path of the attribute to be set.
     * @param value Value to be set with
     * @throws Exception
     */
    void setAttributeValue(String path, long value) throws Exception;
}
