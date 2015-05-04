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

package com.cisco.oss.foundation.monitoring;

import com.cisco.oss.foundation.monitoring.notification.NotificationSender;

/**
 * It is an interface when implemented by Component's MXBean class, it will get
 * a reference to NotificationSender object through which it can send
 * notifications by itself.
 *
 * @author manojc
 */
public interface INotifier {
    void setNotificationSender(NotificationSender nSender);
}
