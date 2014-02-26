package com.cisco.oss.foundation.monitoring;

import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * this class is a proxy for creating a registry in a way that starts an
 * external "rmiregistry" process if the requested registry is not up.
 *
 * @author Yair Ogen
 */
public final class RegistryFinder {

    /**
     *
     */
    private static final String JAVA_HOME = "java.home";
    private static final String JAVA_RMI_SERVER_CODEBASE = "java.rmi.server.codebase";
    private static final Logger AUDITOR = LoggerFactory.getLogger("audit." + RegistryFinder.class.getName());
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistryFinder.class);
    private Configuration configuration = null;
    private static RegistryFinder instance;
    private static final Object LOCK = new Object();

    private RegistryFinder() {
    }

    // primitive singleton implementation. if we have more than one instance,
    // never mind.
    public static RegistryFinder getInstance() {

        synchronized (LOCK) {
            if (instance == null) {
                instance = new RegistryFinder();
            }
            return instance;
        }
    }

    /**
     * @see org.springframework.beans.factory.FactoryBean#getObject()
     */
    public Registry getRegistry(int port) throws Exception {// NOPMD

        try {
            // try to get existing registry
            final Registry registry = LocateRegistry.getRegistry(port);

            // test the registry
            registry.list();

            LOGGER.trace("using existing registry");

            return registry;
        } catch (RemoteException ex) {
            // if registry does not exist, create a new one.
            return startRmiRegistryProcess(port);
        }

    }


    /**
     * start new rmiregistry using PrcessBuilder
     *
     * @param port
     * @return
     */
    private Registry startRmiRegistryProcess(final int port) {

        try {
            final String javaHome = System.getProperty(JAVA_HOME);
            String command = null;
            if (javaHome == null) {
                command = "rmiregistry";
            } else {
                if (javaHome.endsWith("bin")) {
                    command = javaHome + "/rmiregistry ";
                } else {
                    command = javaHome + "/bin/rmiregistry ";
                }
            }

            // try with new command with full path
            return internalProcessStart(port, command);// NOPMD

        } catch (Exception e1) {
            // if failed exit...
            LOGGER.error("The RMI Registry cannot be started.", e1);
            throw new UnsupportedOperationException("could not start rmiregistry!!! Reason: " + e1.toString(), e1);// NOPMD
        }

    }

    private Registry internalProcessStart(final int port, final String command) throws IOException, RemoteException, AccessException {

        String maxHeapArg = "-J-Xmx" + AppProperties.getrmiregistryMaxHeapSize() + "m";
        // start rmiregistry process
        if (System.getProperty("os.name").toLowerCase(Locale.getDefault()).contains("windows")) {
            String[] commandArgsArr = new String[]{command, maxHeapArg, String.valueOf(port)};
            List<String> commandArgs = Arrays.asList(commandArgsArr);
            LOGGER.info("running command: " + commandArgs);
            new ProcessBuilder(commandArgsArr).start();
        } else {
            // support background process to prevent zombies

            String[] commandArgsArr = new String[]{"/bin/sh", "-c", command + maxHeapArg + " " + port + "&"};
            List<String> commandArgs = Arrays.asList(commandArgsArr);
            LOGGER.info("running command: " + commandArgs);
            new ProcessBuilder(commandArgsArr).start();
        }

        try {
            // wait for the process to start properly
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            LOGGER.warn("The sleep for  rmi registry to end has been interrupted: " + e.toString());
        }

        // get registry
        final Registry registry = LocateRegistry.getRegistry(port);

        // test registry
        registry.list();

        LOGGER.info("New RMI  Registry created using port: " + port);

        return registry;
    }

}

