/*
 * Copyright (c) NDS Limited 2010.
 * All rights reserved.
 * No part of this program may be reproduced, translated or transmitted,
 * in any form or by any means, electronic, mechanical, photocopying,
 * recording or otherwise, or stored in any retrieval system of any nature,
 * without written permission of the copyright holder.
 */

/*
 * Created on Aug 23, 2010
 */
package com.cisco.oss.foundation.monitoring;

import com.nds.cab.component.NDSComponentSystem;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author manojc
 */
public class UtilityTest {
    @Before
    public void init() {
        AppProperties.determineHostDetails();
    }

    @Test
    public void testGetServiceURLForNullMXBean() {
        assertEquals(null, Utility.getServiceURL(null));
    }

    @Test
    public void testGetServiceURLForNullInstance() {
        AppProperties.clearComponentInfo();
        NDSComponentSystem myComponent = new NDSComponentSystem("ABC", "1.2", null, "/bin/abc");
        String hostIP = AppProperties.getHostIP();
        String expectedServiceURL = "service:jmx:rmi://" + hostIP + ":" + AppProperties.getExportedPort() + "/jndi/rmi://" + hostIP + ":" + AppProperties.getAgentPort() + "/jmxrmi/ABC";
        assertEquals(expectedServiceURL, Utility.getServiceURL(myComponent));
    }

    @Test
    public void testGetObjectNameForNullMXBean() {
        assertEquals(null, Utility.getObjectName(null, null));
    }

    @Test
    public void testGetObjectNameForNullInstance() {

        NDSComponentSystem myComponent = new NDSComponentSystem("ABC", "1.2", null, "/bin/abc");
        assertEquals("nds.mx:name=ABC,contentSource=Application", Utility.getObjectName("Application", myComponent));
    }

    @Test
    public void testGetObjectNameForNoContentSource() {
        AppProperties.clearComponentInfo();
        NDSComponentSystem myComponent = new NDSComponentSystem("ABC", "1.2", "Backup", "/bin/abc");
        assertEquals("nds.mx:name=ABC,instance=Backup", Utility.getObjectName("", myComponent));
    }
}
