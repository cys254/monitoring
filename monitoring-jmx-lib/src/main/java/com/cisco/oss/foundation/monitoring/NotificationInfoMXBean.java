package com.cisco.oss.foundation.monitoring;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlType;

/**
 * Defines the base api for all types of Notifications.
 */
@XmlType
public interface NotificationInfoMXBean extends Serializable {

    final String CI_INGEST_COMPLETE = "IngestComplete";
    final String OASM_OFFER_INGEST_COMPLETE = "IngestComplete";
    final String OASM_OFFER_INGEST_EXCEPTION = "IngestException";

    String getComponentName();

    String getId();

    java.util.Date getTimeOfOccurance();

    void setComponentName(String compName);

    void setId(String id);

    void setTimeOfOccurance(java.util.Date timeOfOccurence);
}

