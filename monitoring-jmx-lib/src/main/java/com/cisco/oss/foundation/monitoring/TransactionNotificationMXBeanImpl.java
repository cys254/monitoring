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

import java.util.Date;
import java.util.List;

public class TransactionNotificationMXBeanImpl implements TransactionNotificationMXBean {

    private static final long serialVersionUID = -4254566615900973771L;

    private String componentName;
    private String id;
    private Date timeOfOccurence;
    private List<ParameterDetails> parameterList;

    @Override
    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Date getTimeOfOccurance() {
        return timeOfOccurence;
    }

    @Override
    public String toString() {

        return "NotificationInfo [compName=" + componentName + ", id=" + id
                + ", timeOfOccurence=" + timeOfOccurence + "]";
    }

    @Override
    public List<ParameterDetails> getParameterDetails() {
        return parameterList;
    }

    @Override
    public void setTimeOfOccurance(Date timeOfOccurence) {
        this.timeOfOccurence = timeOfOccurence;

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((componentName == null) ? 0 : componentName.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final TransactionNotificationMXBeanImpl other = (TransactionNotificationMXBeanImpl) obj;
        if (componentName == null) {
            if (other.componentName != null)
                return false;
        } else if (!componentName.equals(other.componentName))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;


        return true;
    }


}
