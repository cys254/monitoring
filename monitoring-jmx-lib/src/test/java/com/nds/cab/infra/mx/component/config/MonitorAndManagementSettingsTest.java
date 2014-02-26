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
