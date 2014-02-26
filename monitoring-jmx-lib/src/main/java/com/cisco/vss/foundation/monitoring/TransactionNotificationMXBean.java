package com.cisco.vss.foundation.monitoring;

import java.util.List;

/**
 * This interface defines a MXBean, used to send notification when a certain type of Transaction is complete.
 *
 * @author Avinasht
 */
public interface TransactionNotificationMXBean extends NotificationInfoMXBean {

    /**
     * Returns a list of ParameterDetails instances.
     * ParameterDetails represents a key and value pair object, key being the parameter name and value being the parameter value.
     *
     * @param
     * @return List<ParameterDetails>
     */
    List<ParameterDetails> getParameterDetails();

}
