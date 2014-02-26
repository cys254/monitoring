/*
 * Copyright (c) NDS Limited 2010.
 * All rights reserved.
 * No part of this program may be reproduced, translated or transmitted,
 * in any form or by any means, electronic, mechanical, photocopying,
 * recording or otherwise, or stored in any retrieval system of any nature,
 * without written permission of the copyright holder.
 */

/* 
 * Created on Aug 25, 2010
 */

package com.cisco.oss.foundation.monitoring;

import static org.junit.Assert.fail;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.junit.Before;
import org.junit.Test;

import com.nds.cab.component.NDSComponentSystem;

public class ServerRecoveryDaemonTest {
    @Before
    public void init() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void unregisterAgent(MonitoringAgent agent) {
        try {
            if (agent != null && agent.isRegistered()) {
                agent.unregister();
            }
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testServerRecoveryDaemon() {
        System.setProperty("nds.mx.recoverydaemon.polling", "5000");
        NDSComponentSystem myComponent = null;
        MonitoringAgent mxAgent = null;
        MonitoringClient myClient = null;
        try {
            myComponent = new NDSComponentSystem();
            mxAgent = MonitoringAgent.getInstance();
            mxAgent.register(myComponent);
            myClient = new MonitoringClient(myComponent.getName(), myComponent.getInstance(), null, mxAgent
                    .getAgentDetails().getAgentPort(), "ndsmonitoradmin", "admin");
            String result = myClient.getManagementInformationXml();

            if (result == null || !result.startsWith("<MBeans>") || !result.endsWith("</MBeans>")
                    || !result.contains("</MBean>"))
                fail("ManagementInformationXml couldn't be retrieved.");

            Registry registry = LocateRegistry.getRegistry(3000);
            registry.unbind("jmxrmi/ABCBackup");
            Thread.sleep(10000);

            myClient = new MonitoringClient(myComponent.getName(), myComponent.getInstance(), null, mxAgent
                    .getAgentDetails().getAgentPort(), "ndsmonitoradmin", "admin");
            result = myClient.getManagementInformationXml();

            if (result == null || !result.startsWith("<MBeans>") || !result.endsWith("</MBeans>")
                    || !result.contains("</MBean>"))
                fail("ManagementInformationXml couldn't be retrieved after unbinding rmiregistry.");
        } catch (Exception ex) {
            fail(ex.getMessage());
        } finally {
            myClient.disconnect();
            unregisterAgent(mxAgent);
            myComponent = null;
            mxAgent = null;
        }
    }

}
