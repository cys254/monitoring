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
import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Manages rmiregistry.
 *
 * @author manojc
 */
public class RMIRegistryManager {
    private static final String JAVA_HOME_RMI_REGISTRY = System.getProperty("java.home") + "/bin/rmiregistry";
    private static final String RMI_REGISTRY = "rmiregistry";
    private static final Logger LOGGER = LoggerFactory.getLogger(RMIRegistryManager.class);

    /**
     * Checks if rmiregistry is running on the specified port.
     *
     * @param port
     * @return true if rmiregistry is running on the specified port, false
     * otherwise
     */
    public static boolean isRMIRegistryRunning(Configuration configuration, int port) {
        try {
            final Registry registry = RegistryFinder.getInstance().getRegistry(configuration, port);
            registry.list();
            return true;
        } catch (RemoteException ex) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if a service is exported on the rmi registry with specified port.
     *
     * @param port
     * @return true if rmiregistry is running on the specified port and service
     * is exported on the rmi registry, false otherwise
     */
    public static boolean isServiceExported(Configuration configuration, int port, String serviceName) {
        if (serviceName == null)
            return false;
        try {
            final Registry registry = RegistryFinder.getInstance().getRegistry(configuration, port);
            String[] list = registry.list();

            if (list != null) {
                for (int i = 0; i < list.length; i++) {
                    if (serviceName.equals(list[i]))
                        return true;
                }
            }
        } catch (RemoteException ex) {
            return false;
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * Starts rmiregistry on the specified port. If property
     * "service.monitor.inProcess" is set to true in configSchema.xml,
     * then an in-process rmiregistry is started.
     * Otherwise rmiregistry will be started in a seperate process.
     *
     * @param port on which the rmiregistry needs to be started
     * @return true if successful or it is already started, false otherwise
     */
    public static boolean startRMIRegistry(Configuration configuration, int port) {
        if (isRMIRegistryRunning(configuration, port))
            return true;

        if (configuration.getBoolean(FoundationMonitoringConstants.IN_PROC_RMI)) {
            return startInProcRMIRegistry(port);
        } else {
            return startOutProcRMIRegistry(configuration, port);
        }
    }

    /**
     * Starts in-process rmiregistry on the specified port.
     *
     * @param port on which the rmiregistry needs to be started
     * @return true if successful, false otherwise
     */
    public static boolean startInProcRMIRegistry(final int port) {
        LOGGER.info("Starting In-Process rmiregistry on port " + port);
        boolean result = true;
        try {
            LocateRegistry.createRegistry(port);
            LOGGER.info("In-Process rmiregistry started on port " + port);
        } catch (RemoteException e) {
            LOGGER.error("Failed to start In-Process rmiregistry on port " + port);
            result = false;
        }
        return result;
    }

    /**
     * Starts rmiregistry in a separate process on the specified port.
     *
     * @param port on which the rmiregistry needs to be started
     * @return true if successful, false otherwise
     */
    public static boolean startOutProcRMIRegistry(Configuration configuration, final int port) {
        LOGGER.info("Starting rmiregistry on port " + port);
        try {

            Registry registryStarted = RegistryFinder.getInstance().getRegistry(configuration, port);
            if (registryStarted != null) {
                LOGGER.info("rmiregistry started on " + port);
            } else {
                LOGGER.error("Failed to start rmiregistry on " + port);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
        //return startProcess(port, JAVA_HOME_RMI_REGISTRY) || startProcess(port, RMI_REGISTRY);
    }

}
