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

/* 
 * Created on Aug 26, 2010
 */

package com.cisco.oss.foundation.monitoring;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.cisco.oss.foundation.monitoring.component.NDSComponentSystem;

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
