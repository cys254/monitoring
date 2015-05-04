/*
 * Copyright 2015 Cisco Systems, Inc.
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

package com.cisco.oss.foundation.monitoring.notification;

import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

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

