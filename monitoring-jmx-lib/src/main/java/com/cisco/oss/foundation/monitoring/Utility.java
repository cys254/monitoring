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

import com.cisco.oss.foundation.monitoring.exception.AgentRegistrationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import javax.management.remote.rmi.RMIConnectorServer;


/**
 * This class has all the static utility methods.
 *
 * @author manojc
 */
class Utility {

    private static final Logger LOGGER = LoggerFactory.getLogger(Utility.class);

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
            LOGGER.error("Attempt to use MonitoringAgent in an unsupported version of Java");
            throw new AgentRegistrationException("Unsupported java version");
        }
    }

    static Map<String, String> prepareJmxEnvironmentMap() {
        Map<String, String> env = new HashMap<String, String>();
        env.put(RMIConnectorServer.JNDI_REBIND_ATTRIBUTE, Boolean.TRUE.toString());
        return env;
    }
}
