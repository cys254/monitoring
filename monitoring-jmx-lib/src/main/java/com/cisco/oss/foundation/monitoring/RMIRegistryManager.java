/*
 * Copyright (c) NDS Limited 2010.
 * All rights reserved.
 * No part of this program may be reproduced, translated or transmitted,
 * in any form or by any means, electronic, mechanical, photocopying,
 * recording or otherwise, or stored in any retrieval system of any nature,
 * without written permission of the copyright holder.
 */

package com.cisco.oss.foundation.monitoring;

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

    /**
     * Checks if rmiregistry is running on the specified port.
     *
     * @param port
     * @return true if rmiregistry is running on the specified port, false
     * otherwise
     */
    public static boolean isRMIRegistryRunning(int port) {
        try {
            final Registry registry = RegistryFinder.getInstance().getRegistry(port);
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
    public static boolean isServiceExported(int port, String serviceName) {
        if (serviceName == null)
            return false;
        try {
            final Registry registry = RegistryFinder.getInstance().getRegistry(port);
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
    public static boolean startRMIRegistry(int port) {
        if (isRMIRegistryRunning(port))
            return true;

        if (AppProperties.isInProcessRMI()) {
            return startInProcRMIRegistry(port);
        } else {
            return startOutProcRMIRegistry(port);
        }
    }

    /**
     * Starts in-process rmiregistry on the specified port.
     *
     * @param port on which the rmiregistry needs to be started
     * @return true if successful, false otherwise
     */
    public static boolean startInProcRMIRegistry(final int port) {
        MonitoringAgent.AUDITOR.info("Starting In-Process rmiregistry on port " + port);
        boolean result = true;
        try {
            LocateRegistry.createRegistry(port);
            MonitoringAgent.AUDITOR.info("In-Process rmiregistry started on port " + port);
        } catch (RemoteException e) {
            MonitoringAgent.AUDITOR.error("Failed to start In-Process rmiregistry on port " + port);
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
    public static boolean startOutProcRMIRegistry(final int port) {
        MonitoringAgent.AUDITOR.info("Starting rmiregistry on port " + port);
        try {

            Registry registryStarted = RegistryFinder.getInstance().getRegistry(port);
            if (registryStarted != null) {
                MonitoringAgent.AUDITOR.info("rmiregistry started on " + port);
            } else {
                MonitoringAgent.AUDITOR.error("Failed to start rmiregistry on " + port);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
        //return startProcess(port, JAVA_HOME_RMI_REGISTRY) || startProcess(port, RMI_REGISTRY);
    }

	/*private static boolean startProcess(final int port, final String command) {
        ProcessBuilder processBuilder = new ProcessBuilder(command, String.valueOf(port));
		int noOfRetries= 1;
		while(!isRMIRegistryRunning(port) && noOfRetries<30){
		try {
			processBuilder.start();
		}
		catch (IOException e) {
			MonitoringAgent.AUDITOR.error("Failed to start rmiregistry using command " + command + " " + port);
			return false;
		}
		noOfRetries++;
		try {
			// wait for the process to start properly
			Thread.sleep(1000);
		}
		catch (InterruptedException e) {
		}
	}

		if (isRMIRegistryRunning(port)) {
			MonitoringAgent.AUDITOR.info("rmiregistry started using command " + command + " " + port);
			return true;
		}
		else {			
			MonitoringAgent.AUDITOR.error("Failed to start rmiregistry using command " + command + " " + port);	
			return false;
		}
	}
	*/
}
