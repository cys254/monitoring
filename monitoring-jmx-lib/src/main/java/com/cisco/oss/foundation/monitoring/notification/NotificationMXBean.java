package com.cisco.oss.foundation.monitoring.notification;

import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.StandardEmitterMBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cisco.oss.foundation.monitoring.NotificationInfoMXBean;


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
