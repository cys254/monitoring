/*
 * Copyright (c) NDS Limited 2010.
 * All rights reserved.
 * No part of this program may be reproduced, translated or transmitted,
 * in any form or by any means, electronic, mechanical, photocopying,
 * recording or otherwise, or stored in any retrieval system of any nature,
 * without written permission of the copyright holder.
 */
package com.cisco.vss.foundation.monitoring;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;

import org.junit.Before;
import org.junit.Test;

import com.nds.cab.component.AllValidData;
import com.nds.cab.component.IncompatibleComponentSystem;
import com.nds.cab.component.NDSComponentSystem;
import com.nds.cab.component.NotificationComponentSystem;
import com.cisco.vss.foundation.monitoring.component.config.MonitorAndManagementSettings;
import com.cisco.vss.foundation.monitoring.exception.AgentAlreadyRegisteredException;
import com.cisco.vss.foundation.monitoring.exception.AgentRegistrationException;
import com.cisco.vss.foundation.monitoring.exception.IncompatibleClassException;

public class MXAgentTest {
    static final String authKey = "Abcd1234";

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
    public void testRegisterUnregister() {
        NDSComponentSystem myComponent = null;
        MonitoringAgent mxAgent = null;
        MonitoringClient myClient = null;
        try {
            AppProperties.setMonitoringEnabled(true);
            myComponent = new NDSComponentSystem();
            mxAgent = MonitoringAgent.getInstance();
            mxAgent.register(myComponent);

            myClient = new MonitoringClient(myComponent.getName(), myComponent.getInstance(), null, mxAgent
                    .getAgentDetails().getAgentPort(), "ndsmonitoradmin", "admin");
            String result = myClient.getManagementInformationXml();

            if (result == null || !result.startsWith("<MBeans>") || !result.endsWith("</MBeans>")
                    || !result.contains("</MBean>"))
                fail("ManagementInformationXml couldn't be retrieved");
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
    public void testRegisterUnregisterWithMXBeanFromConstructor() {
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
                fail("ManagementInformationXml couldn't be retrieved");
        } catch (Exception ex) {
            fail(ex.getMessage());
        } finally {
            myClient.disconnect();
            unregisterAgent(mxAgent);
            myComponent = null;
            mxAgent = null;
        }
    }

	/*@Test
    public void testRegisterUnregisterWithMXBeanAndMXConfigurationFromConstructor() {
		NDSComponentSystem myComponent = null;
		MonitoringAgent mxAgent = null;
		MXConfiguration mxConfig = new Configuration(3010, 4010);
		MonitoringClient myClient = null;
		try {
			myComponent = new NDSComponentSystem();
			mxAgent = new MonitoringAgent(myComponent, mxConfig);

			myClient = new MonitoringClient(myComponent.getName(), myComponent.getInstance(), null, 3010,
					"ndsmonitoradmin", "admin");
			String result = myClient.getManagementInformationXml();

			if (result == null || !result.startsWith("<MBeans>") || !result.endsWith("</MBeans>")
					|| !result.contains("</MBean>"))
				fail("ManagementInformationXml couldn't be retrieved");
		}
		catch (Exception ex) {
			fail(ex.getMessage());
		}
		finally {
			myClient.disconnect();
			unregisterAgent(mxAgent);
			myComponent = null;
			mxAgent = null;
		}
	}*/

    @Test
    public void testGetExposedServiceURL() {
        NDSComponentSystem myComponent = null;
        MonitoringAgent mxAgent = null;
        try {
            myComponent = new NDSComponentSystem();
            mxAgent = MonitoringAgent.getInstance();
            mxAgent.register(myComponent);
            String hostIP = AppProperties.getHostIP();
            String expectedServiceURL = "service:jmx:rmi://" + hostIP + ":4000/jndi/rmi://" + hostIP
                    + ":3000/jmxrmi/ABCBackup";
            assertEquals(expectedServiceURL, mxAgent.getExposedServiceURL());
        } catch (Exception ex) {
            fail(ex.getMessage());
        } finally {
            unregisterAgent(mxAgent);
            myComponent = null;
            mxAgent = null;
        }
    }

    @Test
    public void testGetExposedObjectName() {
        NDSComponentSystem myComponent = null;
        MonitoringAgent mxAgent = null;
        try {
            myComponent = new NDSComponentSystem();
            mxAgent = MonitoringAgent.getInstance();
            mxAgent.register(myComponent);
            assertEquals("nds.mx:name=ABC,instance=Backup,contentSource=Application", mxAgent.getExposedObjectName());
        } catch (Exception ex) {
            fail(ex.getMessage());
        } finally {
            unregisterAgent(mxAgent);
            myComponent = null;
            mxAgent = null;
        }
    }

    @Test
    public void testRegisterWithoutAuthKey() {
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
                fail("ManagementInformationXml couldn't be retrieved");
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
    public void testAgentDetailsWithDefaultConfiguration() {
        NDSComponentSystem myComponent = null;
        MonitoringAgent mxAgent = null;
        try {
            myComponent = new NDSComponentSystem();
            mxAgent = MonitoringAgent.getInstance();
            mxAgent.register(myComponent);

            assertNotNull(mxAgent.getAgentDetails());
            assertEquals(3000, mxAgent.getAgentDetails().getAgentPort());
            assertEquals(4000, mxAgent.getAgentDetails().getExportedPort());
        } catch (Exception ex) {
            fail(ex.getMessage());
        } finally {
            unregisterAgent(mxAgent);
            myComponent = null;
            mxAgent = null;
        }
    }

	/*@SuppressWarnings("deprecation")
	@Test
	public void testAgentDetailsWithCustomConfiguration() {
		NDSComponentSystem myComponent = null;
		MonitoringAgent mxAgent = null;
		try {
			myComponent = new NDSComponentSystem();
			mxAgent = new MonitoringAgent();

			MonitorAndManagementSettings monitorAndManagementSettings = MonitorAndManagementSettings
					.newInstance("/SampleMonitorAndManagementSettings.xml");
			Configuration mxConfiguration = new Configuration(3003, 4020);
			mxConfiguration.setMonitorAndManagementSettings(monitorAndManagementSettings);
			mxAgent.setConfiguration(mxConfiguration);
			mxAgent.register(myComponent);

			assertNotNull(mxAgent.getAgentDetails());
			assertEquals(3003, mxAgent.getAgentDetails().getAgentPort());
			assertEquals(4020, mxAgent.getAgentDetails().getExportedPort());
		}
		catch (Exception ex) {
			fail(ex.getMessage());
		}
		finally {
			unregisterAgent(mxAgent);
			myComponent = null;
			mxAgent = null;
		}
	}
*/
	/*@SuppressWarnings("deprecation")
	@Test
	public void testAgentDetailsWithCustomConfigurationWithoutSuccssFlags() {
		NDSComponentSystem myComponent = null;
		MonitoringAgent mxAgent = null;
		try {
			myComponent = new NDSComponentSystem();
			mxAgent = new MonitoringAgent();

			MonitorAndManagementSettings monitorAndManagementSettings = MonitorAndManagementSettings
					.newInstance("/SampleMonitorAndManagementSettingsWithoutSuccssFlags.xml");
			Configuration mxConfiguration = new Configuration(3003, 4020);
			mxConfiguration.setMonitorAndManagementSettings(monitorAndManagementSettings);
			mxAgent.setConfiguration(mxConfiguration);
			mxAgent.register(myComponent);

			assertNotNull(mxAgent.getAgentDetails());
			assertEquals(3003, mxAgent.getAgentDetails().getAgentPort());
			assertEquals(4020, mxAgent.getAgentDetails().getExportedPort());
		}
		catch (Exception ex) {
			fail(ex.getMessage());
		}
		finally {
			unregisterAgent(mxAgent);
			myComponent = null;
			mxAgent = null;
		}
	}
*/
	/*@Test
	public void testWithCustomConfiguration() {
		NDSComponentSystem myComponent = null;
		MonitoringAgent mxAgent = null;
		MonitoringClient myClient = null;
		try {
			myComponent = new NDSComponentSystem();
			mxAgent = new MonitoringAgent();

			Configuration mxConfiguration = new Configuration(3003, 4020);
			mxAgent.setConfiguration(mxConfiguration);
			mxAgent.register(myComponent);

			myClient = new MonitoringClient(myComponent.getName(), myComponent.getInstance(), null, 3003,
					"ndsmonitoradmin", "admin");
			String result = myClient.getManagementInformationXml();

			if (result == null || !result.startsWith("<MBeans>") || !result.endsWith("</MBeans>")
					|| !result.contains("</MBean>"))
				fail("ManagementInformationXml couldn't be retrieved");
		}
		catch (Exception ex) {
			fail(ex.getMessage());
		}
		finally {
			myClient.disconnect();
			unregisterAgent(mxAgent);
			myComponent = null;
			mxAgent = null;
		}
	}*/

    /*@SuppressWarnings("deprecation")
    @Test
    public void testMonitorAndManagementSettings() {
        NDSComponentSystem myComponent = null;
        MonitoringAgent mxAgent = null;
        MonitoringClient myClient = null;
        try {
            myComponent = new NDSComponentSystem();
            mxAgent = new MonitoringAgent();

            MonitorAndManagementSettings monitorAndManagementSettings = MonitorAndManagementSettings
                    .newInstance("/SampleMonitorAndManagementSettings.xml");
            Configuration mxConfiguration = new Configuration(3003, 4020);
            mxConfiguration.setMonitorAndManagementSettings(monitorAndManagementSettings);
            mxAgent.setConfiguration(mxConfiguration);
            mxAgent.register(myComponent);

            myClient = new MonitoringClient(myComponent.getName(), myComponent.getInstance(), null, 3003,
                    "ndsmonitoradmin", "admin");
            String result = myClient.getManagementInformationXml();

            if (result == null || !result.startsWith("<MBeans>") || !result.endsWith("</MBeans>")
                    || !result.contains("</MBean>"))
                fail("ManagementInformationXml couldn't be retrieved");
        }
        catch (Exception ex) {
            fail(ex.getMessage());
        }
        finally {
            myClient.disconnect();
            unregisterAgent(mxAgent);
            myComponent = null;
            mxAgent = null;
        }
    }
*/
    @Test
    public void testRegisterWithSpaceInApplicationName() {
        NDSComponentSystem myComponent = null;
        MonitoringAgent mxAgent = null;
        MonitoringClient myClient = null;
        try {
            AppProperties.clearComponentInfo();
            myComponent = new NDSComponentSystem("My Test", "1.2", "Primary", "java Test");
            mxAgent = MonitoringAgent.getInstance();
            mxAgent.register(myComponent);

            myClient = new MonitoringClient(myComponent.getName(), myComponent.getInstance(), null, mxAgent
                    .getAgentDetails().getAgentPort(), "ndsmonitoradmin", "admin");
            String result = myClient.getManagementInformationXml();

            if (result == null || !result.startsWith("<MBeans>") || !result.endsWith("</MBeans>")
                    || !result.contains("</MBean>"))
                fail("ManagementInformationXml couldn't be retrieved");
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
    public void testRegisterWithSpaceInInstanceName() {
        NDSComponentSystem myComponent = null;
        MonitoringAgent mxAgent = null;
        MonitoringClient myClient = null;
        try {
            AppProperties.clearComponentInfo();
            myComponent = new NDSComponentSystem("MyTest", "1.2", "Instance 1", "java Test");
            mxAgent = MonitoringAgent.getInstance();
            mxAgent.register(myComponent);

            myClient = new MonitoringClient(myComponent.getName(), myComponent.getInstance(), null, mxAgent
                    .getAgentDetails().getAgentPort(), "ndsmonitoradmin", "admin");
            String result = myClient.getManagementInformationXml();

            if (result == null || !result.startsWith("<MBeans>") || !result.endsWith("</MBeans>")
                    || !result.contains("</MBean>"))
                fail("ManagementInformationXml couldn't be retrieved");
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
    public void testAgentAlreadyRegisteredException() {
        NDSComponentSystem myComponent = null;
        MonitoringAgent mxAgent = null;
        boolean isTestSuccess = false;

        try {
            myComponent = new NDSComponentSystem();
            mxAgent = MonitoringAgent.getInstance();
            mxAgent.register(myComponent);
        } catch (Exception ex) {
            fail(ex.getMessage());
        }

        try {
            mxAgent.register(myComponent);
        } catch (AgentAlreadyRegisteredException aarException) {
            isTestSuccess = true;
        } catch (Exception ex) {
            fail(ex.getMessage());
        } finally {
            unregisterAgent(mxAgent);
            myComponent = null;
            mxAgent = null;
        }

        if (!isTestSuccess)
            fail("Didn't get AgentAlreadyRegisteredException");
    }

    @Test
    public void testIncompatibleClassException() {
        IncompatibleComponentSystem myComponent = new IncompatibleComponentSystem();
        MonitoringAgent mxAgent = null;
        boolean isTestSuccess = false;

        try {
            mxAgent = MonitoringAgent.getInstance();
            mxAgent.register(myComponent);
        } catch (IncompatibleClassException icEx) {
            isTestSuccess = true;
        } catch (Exception ex) {
            fail(ex.getMessage());
        } finally {
            unregisterAgent(mxAgent);
            myComponent = null;
            mxAgent = null;
        }

        if (!isTestSuccess)
            fail("Didn't get IncompatibleClassException");
    }

    @Test
    public void testAgentRegistrationExceptionWithInvalidApplicationName() {
        NDSComponentSystem myComponent = null;
        MonitoringAgent mxAgent = null;
        boolean isTestSuccess = false;

        try {
            AppProperties.clearComponentInfo();
            myComponent = new NDSComponentSystem("Test,", "1.2", "Primary", "java Test");
            mxAgent = MonitoringAgent.getInstance();
            mxAgent.register(myComponent);
        } catch (AgentRegistrationException arEx) {
            isTestSuccess = true;
        } catch (Exception ex) {
            fail(ex.getMessage());
        } finally {
            unregisterAgent(mxAgent);
            myComponent = null;
            mxAgent = null;
        }

        if (!isTestSuccess)
            fail("Didn't get AgentRegistrationException");
    }

    @Test
    public void testAgentRegistrationExceptionWithApplicationNameEndingWithSpace() {
        NDSComponentSystem myComponent = null;
        MonitoringAgent mxAgent = null;
        boolean isTestSuccess = false;

        try {
            AppProperties.clearComponentInfo();
            myComponent = new NDSComponentSystem("Test ", "1.2", "Primary", "java Test");
            mxAgent = MonitoringAgent.getInstance();
            mxAgent.register(myComponent);
        } catch (AgentRegistrationException arEx) {
            isTestSuccess = true;
        } catch (Exception ex) {
            fail(ex.getMessage());
        } finally {
            unregisterAgent(mxAgent);
            myComponent = null;
            mxAgent = null;
        }

        if (!isTestSuccess)
            fail("Didn't get AgentRegistrationException");
    }

    @Test
    public void testAgentRegistrationExceptionWithInvalidInstance() {
        NDSComponentSystem myComponent = null;
        MonitoringAgent mxAgent = null;
        boolean isTestSuccess = false;

        try {
            myComponent = new NDSComponentSystem("Test", "1.2", "Primary,", "java Test");
            mxAgent = MonitoringAgent.getInstance();
            mxAgent.register(myComponent);
        } catch (AgentRegistrationException arEx) {
            isTestSuccess = true;
        } catch (Exception ex) {
            fail(ex.getMessage());
        } finally {
            unregisterAgent(mxAgent);
            myComponent = null;
            mxAgent = null;
        }

        if (!isTestSuccess)
            fail("Didn't get AgentRegistrationException");
    }

    @Test
    public void testAgentRegistrationExceptionWithInstanceEndingWithSpace() {
        NDSComponentSystem myComponent = null;
        MonitoringAgent mxAgent = null;
        boolean isTestSuccess = false;

        try {
            myComponent = new NDSComponentSystem("Test", "1.2", "Primary ", "java Test");
            mxAgent = MonitoringAgent.getInstance();
            mxAgent.register(myComponent);
        } catch (AgentRegistrationException arEx) {
            isTestSuccess = true;
        } catch (Exception ex) {
            fail(ex.getMessage());
        } finally {
            unregisterAgent(mxAgent);
            myComponent = null;
            mxAgent = null;
        }

        if (!isTestSuccess)
            fail("Didn't get AgentRegistrationException");
    }

    @Test
    public void testAllValidDataTypes() {
        AllValidData myComponent = null;
        MonitoringAgent mxAgent = null;
        try {
            AppProperties.clearComponentInfo();
            myComponent = new AllValidData();
            mxAgent = MonitoringAgent.getInstance();
            mxAgent.register(myComponent);
        } catch (Exception ex) {
            fail(ex.getMessage());
        } finally {
            unregisterAgent(mxAgent);
            myComponent = null;
            mxAgent = null;
        }
    }

    @Test
    public void testWrongAuthenticationValues() {
        NDSComponentSystem myComponent = null;
        MonitoringAgent mxAgent = null;

        try {
            String appName = "TestApp";
            String version = "1.0";
            String instance = "Primary";
            String commandLine = "java TestApp abc";
            AppProperties.clearComponentInfo();
            myComponent = new NDSComponentSystem(appName, version, instance, commandLine);
            mxAgent = MonitoringAgent.getInstance();
            mxAgent.register(myComponent);

            MonitoringAgent secondAgent = MonitoringAgent.getRegistedAgent(appName, instance, authKey + "suffix");
            if (secondAgent != null) {
                fail("Insufficient authentication with authKey parameter");
            }
            secondAgent = MonitoringAgent.getRegistedAgent(appName, instance + "suffix", authKey);
            if (secondAgent != null) {
                fail("Insufficient authentication with instance parameter");
            }
            secondAgent = MonitoringAgent.getRegistedAgent(appName + "suffix", instance, authKey);
            if (secondAgent != null) {
                fail("Insufficient authentication with name parameter");
            }
        } catch (Exception ex) {
            fail(ex.getMessage());
        } finally {
            unregisterAgent(mxAgent);
            myComponent = null;
            mxAgent = null;
        }
    }

    @Test
    public void testOneAppMultiServletSupport() {
        NDSComponentSystem myComponent = null;
        MonitoringAgent mxAgent = null;

        try {
            String appName = "TestApp";
            String version = "1.0";
            String instance = "Primary";
            String commandLine = "java TestApp abc";
            AppProperties.clearComponentInfo();
            myComponent = new NDSComponentSystem(appName, version, instance, commandLine);
            mxAgent = MonitoringAgent.getInstance();
            mxAgent.register(myComponent, authKey);

            MonitoringAgent secondAgent = MonitoringAgent.getRegistedAgent(appName, instance, authKey);
            NDSComponentSystem secondComponent = (NDSComponentSystem) secondAgent.getExposedObject();
            if (!secondComponent.getName().equals(appName) || !secondComponent.getInstance().equals(instance)
                    || !secondComponent.getVersion().equals(version)
                    || !secondComponent.getCommandLine().equals(commandLine)) {
                fail("Second servlet didn't get the same copy");
            }
        } catch (Exception ex) {
            fail(ex.getMessage());
        } finally {
            unregisterAgent(mxAgent);
            myComponent = null;
            mxAgent = null;
        }
    }

/*	@Test
	public void testMultiApplicationsSupport() {
		NDSComponentSystem myComponent1 = null, myComponent2 = null;
		MonitoringAgent myAgent1 = null, myAgent2 = null;
		MonitoringClient myClient1 = null, myClient2 = null;
		String result = null;

		try {
			AppProperties.clearComponentInfo();
			myComponent1 = new NDSComponentSystem("TestApp", "1.0", "Primary", "java TestApp abc");
			myAgent1 = MonitoringAgent.getInstance();
			myAgent1.setConfiguration(new Configuration(4010));
			myAgent1.register(myComponent1);
			AppProperties.clearComponentInfo();
			myComponent2 = new NDSComponentSystem("TestApp", "1.0", "Primary1", "java TestApp abc");
			myAgent2 = MonitoringAgent.getInstance();
			myAgent2.setConfiguration(new Configuration(4020));
			myAgent2.register(myComponent2);

			myClient1 = new MonitoringClient(myComponent1.getName(), myComponent1.getInstance(), null, myAgent1
					.getAgentDetails().getAgentPort(), "ndsmonitoradmin", "admin");
			result = myClient1.getManagementInformationXml();

			if (result == null || !result.startsWith("<MBeans>") || !result.endsWith("</MBeans>")
					|| !result.contains("</MBean>"))
				fail("ManagementInformationXml couldn't be retrieved");

			myClient2 = new MonitoringClient(myComponent2.getName(), myComponent2.getInstance(), null, myAgent2
					.getAgentDetails().getAgentPort(), "ndsmonitoradmin", "admin");
			result = myClient2.getManagementInformationXml();

			if (result == null || !result.startsWith("<MBeans>") || !result.endsWith("</MBeans>")
					|| !result.contains("</MBean>"))
				fail("ManagementInformationXml couldn't be retrieved");
		}
		catch (Exception ex) {
			fail(ex.getMessage());
		}
		finally {
			unregisterAgent(myAgent1);
			unregisterAgent(myAgent2);
			if (myClient1 != null) {
				myClient1.disconnect();
				myClient1 = null;
			}
			if (myClient2 != null) {
				myClient2.disconnect();
				myClient2 = null;
			}
			myComponent1 = null;
			myComponent2 = null;
			myAgent1 = null;
			myAgent2 = null;
		}
	}*/

    @Test
    public void testGetNotificationSender() {
        NotificationComponentSystem myComponent = null;
        MonitoringAgent mxAgent = null;
        try {
            myComponent = new NotificationComponentSystem();
            mxAgent = MonitoringAgent.getInstance();
            mxAgent.register(myComponent, authKey);
            assertNotNull(mxAgent.getNotificationSender());
            assertNotNull("NotificationSender is not injected in the MXBean", myComponent.getNotificationSender());
        } catch (Exception ex) {
            fail(ex.getMessage());
        } finally {
            unregisterAgent(mxAgent);
            myComponent = null;
            mxAgent = null;
        }
    }

    @Test
    public void testSetConfigurationAfterMonitoringAgentIsRegistered() {
        NDSComponentSystem myComponent = null;
        MonitoringAgent mxAgent = null;
        boolean isTestSuccess = false;
        try {
            myComponent = new NDSComponentSystem();
            mxAgent = MonitoringAgent.getInstance();
            mxAgent.register(myComponent);
            mxAgent.setConfiguration(new Configuration(4001));
        } catch (AgentAlreadyRegisteredException ex) {
            isTestSuccess = true;
        } catch (Exception ex) {
            fail(ex.getMessage());
        } finally {
            unregisterAgent(mxAgent);
            myComponent = null;
            mxAgent = null;
        }
        assertEquals("Didn't receive AgentAlreadyRegisteredException", true, isTestSuccess);
    }

    public class MyRemoteObject implements Remote {
    }
	
	/*@Test
	public void testRegisterWithStaleRegistryEntry() {
		NDSComponentSystem myComponent = null;
		MonitoringAgent mxAgent = null;
		//MXConfiguration mxConfig = new Configuration(3010, 4010);
		MonitoringClient myClient = null;
		try {
			AppProperties.clearComponentInfo();
			RMIRegistryManager.startInProcRMIRegistry(3000);
			Remote stub = UnicastRemoteObject.exportObject(new MyRemoteObject(), 4000);

			java.rmi.registry.Registry registry = java.rmi.registry.LocateRegistry.getRegistry(3000);
			registry.bind("jmxrmi/ABCBackup", stub);
			
			stub = null;
			
			myComponent = new NDSComponentSystem();
			mxAgent = MonitoringAgent.getMonitoringAgent();
			mxAgent.register(myComponent);
			//mxAgent = new MonitoringAgent(myComponent, mxConfig);

			myClient = new MonitoringClient(myComponent.getName(), myComponent.getInstance(), null, 3010,
					"ndsmonitoradmin", "admin");
			String result = myClient.getManagementInformationXml();

			if (result == null || !result.startsWith("<MBeans>") || !result.endsWith("</MBeans>")
					|| !result.contains("</MBean>"))
				fail("ManagementInformationXml couldn't be retrieved");
		}
		catch (Exception ex) {
			fail(ex.getMessage());
		}
		finally {
			myClient.disconnect();
			unregisterAgent(mxAgent);
			myComponent = null;
			mxAgent = null;
		}
	}*/
}
