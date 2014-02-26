/*
 * Copyright (c) NDS Limited 2010.
 * All rights reserved.
 * No part of this program may be reproduced, translated or transmitted,
 * in any form or by any means, electronic, mechanical, photocopying,
 * recording or otherwise, or stored in any retrieval system of any nature,
 * without written permission of the copyright holder.
 */

/* 
 * Created on Aug 24, 2010
 */

package com.cisco.vss.foundation.monitoring;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.nds.cab.component.NotificationComponentSystem;
import com.nds.cab.component.SampleSystemWithSetters;

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
