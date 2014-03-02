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

import org.junit.Test;

public class ConfigurationTest {

    @Test
    public void testConfiguration() {
        Configuration configuration = new Configuration();
        assertEquals(3000, configuration.getAgentPort());
        assertEquals(4000, configuration.getExportedPort());
    }

    @Test
    public void testSetAgentPort() {
        Configuration configuration = new Configuration();
        configuration.setAgentPort(3005);
        assertEquals(3005, configuration.getAgentPort());
    }

    @Test
    public void testSetExportedPort() {
        Configuration configuration = new Configuration();
        configuration.setExportedPort(4005);
        assertEquals(4005, configuration.getExportedPort());
    }

}
