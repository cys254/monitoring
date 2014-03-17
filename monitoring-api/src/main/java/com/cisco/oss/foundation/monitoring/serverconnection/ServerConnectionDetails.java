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

package com.cisco.oss.foundation.monitoring.serverconnection;

public class ServerConnectionDetails {

    private String serverName;
    private String interfaceName;
    private String hostName;
    private long sourcePort;
    private long destinationPort;
    private String apiName;

    public String getServerName() {
        return serverName;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public String getHostName() {
        return hostName;
    }

    public long getSourcePort() {
        return sourcePort;
    }

    public long getDestinationPort() {
        return destinationPort;
    }


    /**
     * @param serverName
     * @param interfaceName
     * @param hostName
     * @param sourcePort
     * @param destinationPort
     * @param apiName
     */
    public ServerConnectionDetails(String serverName, String interfaceName,
                                   String hostName, long sourcePort, long destinationPort) {
        super();
        this.serverName = serverName;
        this.interfaceName = interfaceName;
        this.hostName = hostName;
        this.sourcePort = sourcePort;
        this.destinationPort = destinationPort;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final ServerConnectionDetails other = (ServerConnectionDetails) obj;
        if (destinationPort != other.destinationPort)
            return false;
        if (hostName == null) {
            if (other.hostName != null)
                return false;
        } else if (!hostName.equals(other.hostName))
            return false;
        if (interfaceName == null) {
            if (other.interfaceName != null)
                return false;
        } else if (!interfaceName.equals(other.interfaceName))
            return false;
        if (apiName == null) {
            if (other.apiName != null)
                return false;
        } else if (!apiName.equals(other.apiName))
            return false;
        if (serverName == null) {
            if (other.serverName != null)
                return false;
        } else if (!serverName.equals(other.serverName))
            return false;
        if (sourcePort != other.sourcePort)
            return false;
        return true;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }
}
