package com.cisco.oss.foundation.monitoring;

import javax.xml.bind.annotation.XmlType;

@XmlType
public interface IComponentInfoMXBean {
    /**
     * Name of the component.Name can be Acronym.
     */
    String getName();

    /**
     * Full name of the component.
     */
    String getFullName();

    /**
     * Instance Name format must be
     */
    String getInstance();

    /**
     * Component version
     */
    String getVersion();

    public RedundancyMode getRedundancyMode();
}
