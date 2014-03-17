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
 * Created on Aug 23, 2010
 */
package com.cisco.oss.foundation.monitoring;

import com.cisco.oss.foundation.monitoring.component.FoundationComponentSystem;

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
        FoundationComponentSystem myComponent = new FoundationComponentSystem("ABC", "1.2", null, "/bin/abc");
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

        FoundationComponentSystem myComponent = new FoundationComponentSystem("ABC", "1.2", null, "/bin/abc");
        assertEquals("foundation.mx:name=ABC,contentSource=Application", Utility.getObjectName("Application", myComponent));
    }

    @Test
    public void testGetObjectNameForNoContentSource() {
        AppProperties.clearComponentInfo();
        FoundationComponentSystem myComponent = new FoundationComponentSystem("ABC", "1.2", "Backup", "/bin/abc");
        assertEquals("foundation.mx:name=ABC,instance=Backup", Utility.getObjectName("", myComponent));
    }
}
