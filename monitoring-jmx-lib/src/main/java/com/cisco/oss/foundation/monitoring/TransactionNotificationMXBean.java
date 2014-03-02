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
