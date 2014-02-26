/*
 * Copyright (c) NDS Limited 2010.
 * All rights reserved.
 * No part of this program may be reproduced, translated or transmitted,
 * in any form or by any means, electronic, mechanical, photocopying,
 * recording or otherwise, or stored in any retrieval system of any nature,
 * without written permission of the copyright holder.
 */
package com.nds.cab.component;

import javax.xml.bind.annotation.XmlElement;

import com.cisco.vss.foundation.monitoring.RedundancyMode;
import com.cisco.vss.foundation.monitoring.INotifier;
import com.cisco.vss.foundation.monitoring.NotificationSender;

public class NotificationComponentSystem implements NDSComponentSystemMXBean, INotifier {
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
