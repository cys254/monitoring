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
import com.cisco.oss.foundation.ip.utils.IpUtils;
import com.cisco.oss.foundation.monitoring.component.data.ComponentInfo;
import com.cisco.oss.foundation.monitoring.exception.AgentRegistrationException;
import org.apache.commons.configuration.Configuration;
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

    static String getServiceURL(Configuration configuration, MonitoringMXBean exposedObject) {
        if (exposedObject == null) {
            return null;
        }

        String serviceURL = "service:jmx:rmi://" + IpUtils.getIpAddress() + ":" + configuration.getInt(FoundationMonitoringConstants.EXPORTED_PORT)
                + "/jndi/rmi://" + IpUtils.getIpAddress() + ":" + configuration.getInt(FoundationMonitoringConstants.MX_PORT) + "/jmxrmi/"
                + ComponentInfo.INSTANCE.getName();

        if ((ComponentInfo.INSTANCE.getInstance() != null) && (!ComponentInfo.INSTANCE.getInstance().trim().equals(""))) {
            serviceURL = serviceURL + ComponentInfo.INSTANCE.getInstance();
        }

        return serviceURL;
    }

    static String getObjectName(String contentSource, MonitoringMXBean exposedObject) {
        if (exposedObject == null) {
            return null;
        }

        String objName = FoundationMonitoringConstants.DOMAIN_NAME + ":name=" + ComponentInfo.INSTANCE.getName();

        if ((ComponentInfo.INSTANCE.getInstance() != null) && (!ComponentInfo.INSTANCE.getInstance().trim().equals(""))) {
            objName = objName + ",instance=" + ComponentInfo.INSTANCE.getInstance();
        }

        if (!contentSource.equals("")) {
            objName = objName + ",contentSource=" + contentSource;
        }

        return objName;
    }

    static void validateGenericParams(MonitoringMXBean exposedObject) throws AgentRegistrationException {
        if ((ComponentInfo.INSTANCE.getName() == null)
                || !ComponentInfo.INSTANCE.getName().matches(FoundationMonitoringConstants.APP_INSTANCE_NAMING_STANDARD)) {
            throw new AgentRegistrationException("Name attributes does not follow the naming standard, which is "
                    + FoundationMonitoringConstants.APP_NAME_NAMING_STANDARD);
        }

        if ((ComponentInfo.INSTANCE.getInstance() != null) && (!ComponentInfo.INSTANCE.getInstance().trim().equals(""))
                && !ComponentInfo.INSTANCE.getInstance().matches(FoundationMonitoringConstants.APP_INSTANCE_NAMING_STANDARD)) {
            throw new AgentRegistrationException("Instance attributes does not follow the naming standard, which is "
                    + FoundationMonitoringConstants.APP_NAME_NAMING_STANDARD);
        }
    }

    static void validateJavaVersion() throws AgentRegistrationException {
        String javaVersion = System.getProperty("java.version").substring(0, 3);

        if (javaVersion.equals("1.0") || javaVersion.equals("1.1") || javaVersion.equals("1.2")
                || javaVersion.equals("1.3") || javaVersion.equals("1.4") || javaVersion.equals("1.5")) {
            LOGGER.error("Attempt to use RMIMonitoringAgent in an unsupported version of Java");
            throw new AgentRegistrationException("Unsupported java version");
        }
    }

    static Map<String, String> prepareJmxEnvironmentMap() {
        Map<String, String> env = new HashMap<String, String>();
        env.put(RMIConnectorServer.JNDI_REBIND_ATTRIBUTE, Boolean.TRUE.toString());
        return env;
    }
}
