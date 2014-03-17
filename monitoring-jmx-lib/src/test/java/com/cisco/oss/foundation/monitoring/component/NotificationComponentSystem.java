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
package com.cisco.oss.foundation.monitoring.component;

import javax.xml.bind.annotation.XmlElement;

import com.cisco.oss.foundation.monitoring.RedundancyMode;
import com.cisco.oss.foundation.monitoring.INotifier;
import com.cisco.oss.foundation.monitoring.notification.NotificationSender;

public class NotificationComponentSystem implements FoundationComponentSystemMXBean, INotifier {
    private String name = "ABC";
    private String commandLine = "java ABC arg1 arg2";
    private String instance = "Backup";
    private String version = "1.2.3.R4";
    NotificationSender nSender;

    public NotificationComponentSystem() {
    }

    public NotificationComponentSystem(String name, String version, String instance, String commandLine) {
        this.name = name;
        this.version = version;
        this.instance = instance;
        this.commandLine = commandLine;
    }

    @XmlElement
    public String getCommandLine() {
        return commandLine;
    }

    @XmlElement
    public String getInstance() {
        return instance;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    @XmlElement
    public String getVersion() {
        return version;
    }

    public void setNotificationSender(NotificationSender sender) {
        nSender = sender;
    }

    public NotificationSender getNotificationSender() {
        return nSender;
    }

    @XmlElement
    public RedundancyMode getApplicationMode() {
        return RedundancyMode.StandAlone;
    }

    @Override
    public String getFullName() {
        // TODO Auto-generated method stub
        return null;
    }


}
