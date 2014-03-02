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

package com.cisco.oss.foundation.monitoring.component.config;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.bind.JAXBException;

import org.junit.Test;

public class MonitorAndManagementSettingsTest {

    @Test
    public void testNewInstanceWithURL() throws MalformedURLException, IOException, JAXBException {
        MonitorAndManagementSettings settings = MonitorAndManagementSettings.newInstance(new URL(
                "file:src/test/resources/SampleMonitorAndManagementSettings.xml"));
        assertNotNull(settings.getControlSettings());
        assertNotNull(settings.getControlSettings().getStartCommandSettings());
        assertEquals(settings.getControlSettings().getStartCommandSettings().getCommand(),
                "/etc/init.d/nds_sample start");
    }

    @Test
    public void testNewInstanceWithClasspath() throws JAXBException {
        MonitorAndManagementSettings settings = MonitorAndManagementSettings
                .newInstance("/SampleMonitorAndManagementSettings.xml");
        assertNotNull(settings.getControlSettings());
        assertNotNull(settings.getControlSettings().getStartCommandSettings());
        assertEquals(settings.getControlSettings().getStartCommandSettings().getCommand(),
                "/etc/init.d/nds_sample start");
    }

}
