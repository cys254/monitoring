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

package com.cisco.oss.foundation.monitoring.services;

public class ServiceDetails implements Cloneable {

    private String serviceDescription;
    private String interfaceName;
    private String protocol;
    private int port;
    private String apiName;


    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final ServiceDetails other = (ServiceDetails) obj;
        if (interfaceName == null) {
            if (other.interfaceName != null)
                return false;
        } else if (!interfaceName.equals(other.interfaceName))
            return false;
        if (port != other.port)
            return false;
        if (protocol == null) {
            if (other.protocol != null)
                return false;
        } else if (!protocol.equals(other.protocol))
            return false;
        if (apiName == null) {
            if (other.apiName != null)
                return false;
        } else if (!apiName.equals(other.apiName))
            return false;
        if (serviceDescription == null) {
            if (other.serviceDescription != null)
                return false;
        } else if (!serviceDescription.equals(other.serviceDescription))
            return false;
        return true;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public String getProtocol() {
        return protocol;
    }

    public int getPort() {
        return port;
    }

    /**
     * @param serviceDescription
     * @param interfaceName
     * @param protocol
     * @param port
     * @param apiName
     */
    public ServiceDetails(String serviceDescription, String interfaceName,
                          String protocol, int port) {
        super();
        this.serviceDescription = serviceDescription;
        this.interfaceName = interfaceName;
        this.protocol = protocol;
        this.port = port;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }


}
