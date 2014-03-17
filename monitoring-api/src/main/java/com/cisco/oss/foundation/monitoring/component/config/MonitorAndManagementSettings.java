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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * This class provides the default implementation for
 * MonitorAndManagementSettings which exposes several bits of information which
 * eases monitoring systems to easily discover and self configure how this
 * component can be monitored and managed.
 *
 * @author manojc
 */
@XmlRootElement
public class MonitorAndManagementSettings implements MonitorAndManagementSettingsMXBean {
    private ControlSettings controlSettings = new ControlSettings();
    private OtherSettings otherSettings = new OtherSettings();

    /**
     * Creates a new instance of MonitorAndManagementSettings from an URL to xml
     * settings file.
     *
     * @param settingsXml URL of the settings xml file
     * @return a new instance of MonitoringConfiguration
     * @throws java.io.IOException
     * @throws javax.xml.bind.JAXBException
     */
    public static MonitorAndManagementSettings newInstance(URL settingsXml) throws IOException, JAXBException {
        InputStream istream = settingsXml.openStream();
        JAXBContext ctx = JAXBContext.newInstance(MonitorAndManagementSettings.class);
        return (MonitorAndManagementSettings) ctx.createUnmarshaller().unmarshal(istream);
    }

    /**
     * Creates a new instance of MonitorAndManagementSettings from a classpath
     * resource xml settings file.
     *
     * @param settingsXml Path of a classpath resource xml settings file
     * @return a new instance of {@link #MonitorAndManagementSettings}
     * @throws java.io.IOException
     * @throws javax.xml.bind.JAXBException
     */
    public static MonitorAndManagementSettings newInstance(String settingsXml) throws JAXBException {
        InputStream istream = MonitorAndManagementSettings.class.getResourceAsStream(settingsXml);
        JAXBContext ctx = JAXBContext.newInstance(MonitorAndManagementSettings.class);
        return (MonitorAndManagementSettings) ctx.createUnmarshaller().unmarshal(istream);
    }

    @Override
    public ControlSettings getControlSettings() {
        return controlSettings;
    }

    /**
     * Sets the settings which tells how this component can be controlled
     * remotely.
     *
     * @param controlSettings
     */
    public void setControlSettings(ControlSettings controlSettings) {
        this.controlSettings = controlSettings;
    }

    @Override
    public OtherSettings getOtherSettings() {
        return otherSettings;
    }

    public void setOtherSettings(OtherSettings otherSettings) {
        this.otherSettings = otherSettings;
    }
}
