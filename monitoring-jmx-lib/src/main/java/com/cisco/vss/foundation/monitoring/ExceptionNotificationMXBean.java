package com.cisco.vss.foundation.monitoring;


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
