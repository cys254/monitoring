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

package com.cisco.vss.foundation.monitoring;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.nds.cab.component.NDSComponentSystem;

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
