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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.*;


public class NotificationMXBean extends StandardEmitterMBean implements NotificationInterfaceMXBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationMXBean.class);

    public NotificationMXBean() {
        super(NotificationInterfaceMXBean.class, true, new NotificationBroadcasterSupport());

    }

    public void sendNotification(NotificationInfoMXBean data) {
        sendNotificationToClient(data);
    }

    void sendNotificationToClient(NotificationInfoMXBean data) {

        Notification notification = new Notification("mx.notification", this,
                0, System.currentTimeMillis());
        try {
            notification.setUserData(data);
            sendNotification(notification);
            LOGGER.info("Notification sent");
        } catch (Exception e) {

            LOGGER.error("Exception from sendNotificationtoClient method :" + e.getMessage());
        }
    }

    public void addNotificationListener(
            NotificationListener listener,
            NotificationFilter filter,
            Object handback) {
        super.addNotificationListener(
                listener, filter, handback);
    }

    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {

        String[] types = new String[]{"mx.notification"};
        String name = Notification.class.getName();
        MBeanNotificationInfo info =
                new MBeanNotificationInfo(types, name, "A notification is sent");
        return new MBeanNotificationInfo[]{info};
    }

}
