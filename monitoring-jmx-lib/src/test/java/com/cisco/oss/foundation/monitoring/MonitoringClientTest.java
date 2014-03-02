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

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.cisco.oss.foundation.monitoring.component.NDSComponentSystem;

public class MonitoringClientTest {

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
    public void testMonitoringClientStringString() {
        NDSComponentSystem myComponent = null;
        MonitoringAgent mxAgent = null;
        MonitoringClient myClient = null;
        try {
            myComponent = new NDSComponentSystem();
            mxAgent = MonitoringAgent.getInstance();
            mxAgent.register(myComponent);

            myClient = new MonitoringClient(mxAgent.getExposedServiceURL(), mxAgent.getExposedObjectName());
            String result = myClient.getManagementInformationXml();
            assertNotNull(result);
            assertTrue(result.startsWith("<MBeans>"));
            assertTrue(result.endsWith("</MBeans>"));
            assertTrue(result.contains("</MBean>"));
        } catch (Exception ex) {
            fail(ex.getMessage());
        } finally {
            myClient.disconnect();
            unregisterAgent(mxAgent);
            myComponent = null;
            mxAgent = null;
        }
    }

    @Test
    public void testMonitoringClientStringStringStringString() {
        NDSComponentSystem myComponent = null;
        MonitoringAgent mxAgent = null;
        MonitoringClient myClient = null;
        try {
            myComponent = new NDSComponentSystem();
            mxAgent = MonitoringAgent.getInstance();
            mxAgent.register(myComponent);

            myClient = new MonitoringClient(mxAgent.getExposedServiceURL(), mxAgent.getExposedObjectName(),
                    "ndsmonitoradmin", "admin");
            String result = myClient.getManagementInformationXml();
            assertNotNull(result);
            assertTrue(result.startsWith("<MBeans>"));
            assertTrue(result.endsWith("</MBeans>"));
            assertTrue(result.contains("</MBean>"));
        } catch (Exception ex) {
            fail(ex.getMessage());
        } finally {
            myClient.disconnect();
            unregisterAgent(mxAgent);
            myComponent = null;
            mxAgent = null;
        }
    }

    @Test
    public void testMonitoringClientStringStringStringInt() {
        NDSComponentSystem myComponent = null;
        MonitoringAgent mxAgent = null;
        MonitoringClient myClient = null;
        try {
            myComponent = new NDSComponentSystem();
            mxAgent = MonitoringAgent.getInstance();
            mxAgent.register(myComponent);

            myClient = new MonitoringClient(myComponent.getName(), myComponent.getInstance(), null, mxAgent
                    .getAgentDetails().getAgentPort());
            String result = myClient.getManagementInformationXml();
            assertNotNull(result);
            assertTrue(result.startsWith("<MBeans>"));
            assertTrue(result.endsWith("</MBeans>"));
            assertTrue(result.contains("</MBean>"));
        } catch (Exception ex) {
            fail(ex.getMessage());
        } finally {
            myClient.disconnect();
            unregisterAgent(mxAgent);
            myComponent = null;
            mxAgent = null;
        }
    }

    @Test
    public void testMonitoringClientStringStringStringIntStringString() {
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
            assertNotNull(result);
            assertTrue(result.startsWith("<MBeans>"));
            assertTrue(result.endsWith("</MBeans>"));
            assertTrue(result.contains("</MBean>"));
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
