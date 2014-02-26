/*
 * Copyright (c) NDS Limited 2010.
 * All rights reserved.
 * No part of this program may be reproduced, translated or transmitted,
 * in any form or by any means, electronic, mechanical, photocopying,
 * recording or otherwise, or stored in any retrieval system of any nature,
 * without written permission of the copyright holder.
 */

/* 
 * Created on Aug 26, 2010
 */

package com.cisco.oss.foundation.monitoring;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.nds.cab.component.NDSComponentSystem;

/**
 * @author manojc
 */
public class RMIRegistryManagerTest {

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
    public void testIsRMIRegistryRunningWithFalsePort() {
        assertFalse(RMIRegistryManager.isRMIRegistryRunning(1));
    }

    @Test
    public void testIsServiceExportedWithFalsePort() {
        assertFalse(RMIRegistryManager.isServiceExported(1, "ABCBackup"));
    }

    @Test
    public void testIsServiceExportedWithNullServiceName() {
        assertFalse(RMIRegistryManager.isServiceExported(3000, null));
    }

    @Test
    public void testIsServiceExported() {
        NDSComponentSystem myComponent = null;
        MonitoringAgent mxAgent = null;
        try {
            AppProperties.clearComponentInfo();
            myComponent = new NDSComponentSystem();
            mxAgent = MonitoringAgent.getInstance();
            mxAgent.register(myComponent);
            int agentPort = mxAgent.getAgentDetails().getAgentPort();
            String serviceName = "jmxrmi/" + myComponent.getName() + myComponent.getInstance();
            assertTrue(RMIRegistryManager.isServiceExported(agentPort, serviceName));
        } catch (Exception ex) {
            fail(ex.getMessage());
        } finally {
            unregisterAgent(mxAgent);
            myComponent = null;
            mxAgent = null;
        }
    }
}
