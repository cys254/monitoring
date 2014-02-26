/*
 * Copyright (c) NDS Limited 2010.
 * All rights reserved.
 * No part of this program may be reproduced, translated or transmitted,
 * in any form or by any means, electronic, mechanical, photocopying,
 * recording or otherwise, or stored in any retrieval system of any nature,
 * without written permission of the copyright holder.
 */

package com.cisco.vss.foundation.monitoring.component.config;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

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
