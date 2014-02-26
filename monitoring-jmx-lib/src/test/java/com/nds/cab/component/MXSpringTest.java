/*
 * Copyright (c) NDS Limited 2010.
 * All rights reserved.
 * No part of this program may be reproduced, translated or transmitted,
 * in any form or by any means, electronic, mechanical, photocopying,
 * recording or otherwise, or stored in any retrieval system of any nature,
 * without written permission of the copyright holder.
 */
package com.nds.cab.component;

// package com.nds.cab.infra.testmx;
//
// import static org.junit.Assert.fail;
//
// import org.junit.Test;
// import org.springframework.context.ApplicationContext;
// import org.springframework.context.support.ClassPathXmlApplicationContext;
//
// import com.cisco.vss.foundation.monitoring.MonitoringAgent;
// import com.cisco.vss.foundation.monitoring.MonitoringClient;
// import com.cisco.vss.foundation.monitoring.exception.AgentAlreadyRegisteredException;
// import com.cisco.vss.foundation.monitoring.exception.AgentNotRegisteredException;
//
// public class MXSpringTest {
// static final String authKey = "Abcd1234";
// private static ApplicationContext ctx;
//
// private void unregisterAgent(MonitoringAgent agent) {
// try {
// agent.unregister();
// } catch (AgentNotRegisteredException anrEx) {
// } catch (Exception ex) {
// fail(ex.getMessage());
// }
// }
//
// @Test
// public void testRegisterUnregister() {
// NDSComponentSystem myComponent = null;
// MonitoringAgent myAgent = null;
// MonitoringClient myClient = null;
// try {
//
// ctx = new ClassPathXmlApplicationContext("context.xml");
// myComponent = (NDSComponentSystem) ctx
// .getBean("NDSComponentSystemBean");
// myAgent = (MonitoringAgent) ctx.getBean("mybean");
//
// myClient = new MonitoringClient(myComponent.getName(), myComponent
// .getInstance(), null, myAgent.getAgentDetails()
// .getAgentPort(),"ndsmonitoradmin","admin");
// String result = myClient.getManagementInformationXml();
//
// if (result == null || !result.startsWith("<MBeans>")
// || !result.endsWith("</MBeans>")
// || !result.contains("</MBean>"))
// fail("ManagementInformationXml couldn't be retrieved");
// } catch (Exception ex) {
// fail(ex.getMessage());
// } finally {
// myClient.disconnect();
// unregisterAgent(myAgent);
// myComponent = null;
// myAgent = null;
// }
// }
//
// @Test
// public void testAgentAlreadyRegisteredException() {
// NDSComponentSystem myComponent = null;
// MonitoringAgent myAgent = null;
// boolean isTestSuccess = false;
//
// try {
// ctx = new ClassPathXmlApplicationContext("context.xml");
// myComponent = (NDSComponentSystem) ctx
// .getBean("NDSComponentSystemBean");
// myAgent = (MonitoringAgent) ctx.getBean("mybean");
// } catch (Exception ex) {
// fail(ex.getMessage());
// }
//
// try {
// myAgent.register(myComponent, authKey);
// } catch (AgentAlreadyRegisteredException aarException) {
// isTestSuccess = true;
// } catch (Exception ex) {
// fail(ex.getMessage());
// } finally {
// unregisterAgent(myAgent);
// myComponent = null;
// myAgent = null;
// }
//
// if (!isTestSuccess)
// fail("Didn't get AgentAlreadyRegisteredException");
// }
//
// @Test
// public void testMultiApplicationsSupport() {
// NDSComponentSystem myComponent1 = null, myComponent2 = null;
// MonitoringAgent myAgent1 = null, myAgent2 = null;
// MonitoringClient myClient1 = null, myClient2 = null;
// String result = null;
//
// try {
// ctx = new ClassPathXmlApplicationContext("context1.xml");
//
// myComponent1 = (NDSComponentSystem) ctx
// .getBean("NDSComponentSystemBean1");
// myAgent1 = (MonitoringAgent) ctx.getBean("mybean");
//
// myComponent2 = (NDSComponentSystem) ctx
// .getBean("NDSComponentSystemBean2");
// myAgent2 = (MonitoringAgent) ctx.getBean("mybean1");
//
// myClient1 = new MonitoringClient(myComponent1.getName(),
// myComponent1.getInstance(), null, myAgent1
// .getAgentDetails().getAgentPort(),"ndsmonitoradmin","admin");
// result = myClient1.getManagementInformationXml();
//
// if (result == null || !result.startsWith("<MBeans>")
// || !result.endsWith("</MBeans>")
// || !result.contains("</MBean>"))
// fail("ManagementInformationXml couldn't be retrieved");
//
// myClient2 = new MonitoringClient(myComponent2.getName(),
// myComponent2.getInstance(), null, myAgent2
// .getAgentDetails().getAgentPort(),"ndsmonitoradmin","admin");
// result = myClient2.getManagementInformationXml();
//
// if (result == null || !result.startsWith("<MBeans>")
// || !result.endsWith("</MBeans>")
// || !result.contains("</MBean>"))
// fail("ManagementInformationXml couldn't be retrieved");
// } catch (Exception ex) {
// fail(ex.getMessage());
// } finally {
// unregisterAgent(myAgent1);
// unregisterAgent(myAgent2);
// myClient1.disconnect();
// myClient2.disconnect();
// myComponent1 = null;
// myComponent2 = null;
// myAgent1 = null;
// myAgent2 = null;
// myClient1 = null;
// myClient2 = null;
// }
// }
// }
