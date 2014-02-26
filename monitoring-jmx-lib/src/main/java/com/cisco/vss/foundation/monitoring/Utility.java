/*
 * Copyright (c) NDS Limited 2010.
 * All rights reserved.
 * No part of this program may be reproduced, translated or transmitted,
 * in any form or by any means, electronic, mechanical, photocopying,
 * recording or otherwise, or stored in any retrieval system of any nature,
 * without written permission of the copyright holder.
 */

package com.cisco.vss.foundation.monitoring;

import java.util.HashMap;
import java.util.Map;

import javax.management.remote.rmi.RMIConnectorServer;

import com.cisco.vss.foundation.monitoring.exception.AgentRegistrationException;

/**
 * This class has all the static utility methods.
 *
 * @author manojc
 */
class Utility {
    private Utility() {
    }

    static String getServiceURL(MonitoringMXBean exposedObject) {
        if (exposedObject == null) {
            return null;
        }

        String serviceURL = "service:jmx:rmi://" + AppProperties.getHostIP() + ":" + AppProperties.getExportedPort()
                + "/jndi/rmi://" + AppProperties.getHostIP() + ":" + AppProperties.getAgentPort() + "/jmxrmi/"
                + AppProperties.getComponentInfo(exposedObject).getName();

        if ((AppProperties.getComponentInfo(exposedObject).getInstance() != null) && (!AppProperties.getComponentInfo(exposedObject).getInstance().trim().equals(""))) {
            serviceURL = serviceURL + AppProperties.getComponentInfo(exposedObject).getInstance();
        }

        return serviceURL;
    }

    static String getObjectName(String contentSource, MonitoringMXBean exposedObject) {
        if (exposedObject == null) {
            return null;
        }

        String objName = AppProperties.getDomainName() + ":name=" + AppProperties.getComponentInfo(exposedObject).getName();

        if ((AppProperties.getComponentInfo(exposedObject).getInstance() != null) && (!AppProperties.getComponentInfo(exposedObject).getInstance().trim().equals(""))) {
            objName = objName + ",instance=" + AppProperties.getComponentInfo(exposedObject).getInstance();
        }

        if (!contentSource.equals("")) {
            objName = objName + ",contentSource=" + contentSource;
        }

        return objName;
    }

    static void validateGenericParams(MonitoringMXBean exposedObject) throws AgentRegistrationException {
        if ((AppProperties.getComponentInfo(exposedObject).getName() == null)
                || !AppProperties.getComponentInfo(exposedObject).getName().matches(AppProperties.getAppNameNamingStandard())) {
            throw new AgentRegistrationException("Name attributes does not follow the naming standard, which is "
                    + AppProperties.getAppNameNamingStandard());
        }

        if ((AppProperties.getComponentInfo(exposedObject).getInstance() != null) && (!AppProperties.getComponentInfo(exposedObject).getInstance().trim().equals(""))
                && !AppProperties.getComponentInfo(exposedObject).getInstance().matches(AppProperties.getAppInstanceNamingStandard())) {
            throw new AgentRegistrationException("Instance attributes does not follow the naming standard, which is "
                    + AppProperties.getAppInstanceNamingStandard());
        }
    }

    static void validateJavaVersion() throws AgentRegistrationException {
        String javaVersion = AppProperties.getJavaVersion().substring(0, 3);

        if (javaVersion.equals("1.0") || javaVersion.equals("1.1") || javaVersion.equals("1.2")
                || javaVersion.equals("1.3") || javaVersion.equals("1.4") || javaVersion.equals("1.5")) {
            MonitoringAgent.AUDITOR.error("Attempt to use MonitoringAgent in an unsupported version of Java");
            throw new AgentRegistrationException("Unsupported java version");
        }
    }

    static Map<String, String> prepareJmxEnvironmentMap() {
        Map<String, String> env = new HashMap<String, String>();
        env.put(RMIConnectorServer.JNDI_REBIND_ATTRIBUTE, Boolean.TRUE.toString());
        return env;
    }
}
