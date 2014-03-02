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
 * Created on Aug 24, 2010
 */

package com.cisco.oss.foundation.monitoring;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.cisco.oss.foundation.monitoring.component.NotificationComponentSystem;
import com.cisco.oss.foundation.monitoring.component.SampleSystemWithSetters;

/**
 * @author manojc
 */
public class ServerInfoTest {

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
    public void testSendAttributeChangeNotification() {
        NotificationComponentSystem myComponent = null;
        MonitoringAgent mxAgent = null;
        try {
            myComponent = new NotificationComponentSystem();
            mxAgent = MonitoringAgent.getInstance();
            mxAgent.register(myComponent);
            assertNotNull("NotificationSender is not injected in the MXBean", myComponent.getNotificationSender());
            myComponent.getNotificationSender().sendAttributeChangeNotification("Value Changed", "applicationMode",
                    "String", "Master", "Slave");
        } catch (Exception ex) {
            fail(ex.getMessage());
        } finally {
            unregisterAgent(mxAgent);
            myComponent = null;
            mxAgent = null;
        }
    }

    @Test
    public void testSetAttributeValueStringString() {
        SampleSystemWithSetters myComponent = null;
        MonitoringAgent mxAgent = null;
        try {
            myComponent = new SampleSystemWithSetters();
            mxAgent = MonitoringAgent.getInstance();
            mxAgent.register(myComponent);
            mxAgent.getAgentDetails().setAttributeValue("MyMessage", "Bye");
            assertEquals("Bye", myComponent.getMyMessage());
        } catch (Exception ex) {
            fail(ex.getMessage());
        } finally {
            unregisterAgent(mxAgent);
            myComponent = null;
            mxAgent = null;
        }
    }

    @Test
    public void testSetAttributeValueStringInt() {
        SampleSystemWithSetters myComponent = null;
        MonitoringAgent mxAgent = null;
        try {
            myComponent = new SampleSystemWithSetters();
            mxAgent = MonitoringAgent.getInstance();
            mxAgent.register(myComponent);
            mxAgent.getAgentDetails().setAttributeValue("MyInt", 555);
            assertEquals(555, myComponent.getMyInt());
        } catch (Exception ex) {
            fail(ex.getMessage());
        } finally {
            unregisterAgent(mxAgent);
            myComponent = null;
            mxAgent = null;
        }
    }

    @Test
    public void testSetAttributeValueStringLong() {
        SampleSystemWithSetters myComponent = null;
        MonitoringAgent mxAgent = null;
        try {
            myComponent = new SampleSystemWithSetters();
            mxAgent = MonitoringAgent.getInstance();
            mxAgent.register(myComponent);
            mxAgent.getAgentDetails().setAttributeValue("MyLong", 5555555L);
            assertEquals(5555555L, myComponent.getMyLong());
        } catch (Exception ex) {
            fail(ex.getMessage());
        } finally {
            unregisterAgent(mxAgent);
            myComponent = null;
            mxAgent = null;
        }
    }

    @Test
    public void testSetAttributeValueStringIntForMultilevelAttribute() {
        SampleSystemWithSetters myComponent = null;
        MonitoringAgent mxAgent = null;
        try {
            myComponent = new SampleSystemWithSetters();
            mxAgent = MonitoringAgent.getInstance();
            mxAgent.register(myComponent);
            mxAgent.getAgentDetails().setAttributeValue("LatestRequest/FailedRequestCount", 12345);
            assertEquals(12345, myComponent.getLatestRequest().getFailedRequestCount());
        } catch (Exception ex) {
            fail(ex.getMessage());
        } finally {
            unregisterAgent(mxAgent);
            myComponent = null;
            mxAgent = null;
        }
    }

    @Test
    public void testSetAttributeValueMaps() {
        SampleSystemWithSetters myComponent = null;
        MonitoringAgent mxAgent = null;
        try {
            myComponent = new SampleSystemWithSetters();
            mxAgent = MonitoringAgent.getInstance();
            mxAgent.register(myComponent);
            mxAgent.getAgentDetails().setAttributeValue("LatestRequest/Errors[error1]/ErrorCount", 12345);
            assertEquals(12345, myComponent.getLatestRequest().getErrors().get("error1").getErrorCount());
        } catch (Exception ex) {
            fail(ex.getMessage());
        } finally {
            unregisterAgent(mxAgent);
            myComponent = null;
            mxAgent = null;
        }
    }

    @Test
    public void testSetAttributeValueArray() {
        SampleSystemWithSetters myComponent = null;
        MonitoringAgent mxAgent = null;
        try {
            myComponent = new SampleSystemWithSetters();
            mxAgent = MonitoringAgent.getInstance();
            mxAgent.register(myComponent);
            mxAgent.getAgentDetails().setAttributeValue("AllRequest[1]/FailedRequestCount", 12345);
            assertEquals(12345, myComponent.getAllRequest()[1].getFailedRequestCount());
        } catch (Exception ex) {
            fail(ex.getMessage());
        } finally {
            unregisterAgent(mxAgent);
            myComponent = null;
            mxAgent = null;
        }
    }

    @Test
    public void testSetAttributeValueStringArray() {
        SampleSystemWithSetters myComponent = null;
        MonitoringAgent mxAgent = null;
        try {
            myComponent = new SampleSystemWithSetters();
            mxAgent = MonitoringAgent.getInstance();
            mxAgent.register(myComponent);
            mxAgent.getAgentDetails().setAttributeValue("ConnectedSystems[1]", "my system");
            assertEquals("my system", myComponent.getConnectedSystems()[1]);
        } catch (Exception ex) {
            fail(ex.getMessage());
        } finally {
            unregisterAgent(mxAgent);
            myComponent = null;
            mxAgent = null;
        }
    }

    @Test
    public void testSetAttributeValueList() {
        SampleSystemWithSetters myComponent = null;
        MonitoringAgent mxAgent = null;
        try {
            myComponent = new SampleSystemWithSetters();
            mxAgent = MonitoringAgent.getInstance();
            mxAgent.register(myComponent);
            mxAgent.getAgentDetails().setAttributeValue("Errors[1]/ErrorCount", 12345);
            assertEquals(12345, myComponent.getErrors().get(1).getErrorCount());
        } catch (Exception ex) {
            fail(ex.getMessage());
        } finally {
            unregisterAgent(mxAgent);
            myComponent = null;
            mxAgent = null;
        }
    }
}
