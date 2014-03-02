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

package com.cisco.oss.foundation.monitoring;


/**
 * This interface defines a MXBean, used to notify Exception during a flow.
 * It extends NotificationInfoMXBean, which defines the basic details of the Notification.
 */
public interface ExceptionNotificationMXBean extends NotificationInfoMXBean {

    /**
     * Returns the severity of the Exception.
     * It could be one of the values defined in <code>ExceptionNotificationMXBean</code>
     *
     * @param
     * @return String
     */
    Object getData();

    void setData(Object obj);

    ExceptionSeverity getSeverity();

    void setSeverity(ExceptionSeverity severity);
}
