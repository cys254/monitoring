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
import java.util.concurrent.CopyOnWriteArrayList;

public enum ConnectionInfo implements ConnectionInfoMXBean {

    INSTANCE;

    //	private static ConnectionInfo connectionInfo;
    private List<ServerConnection> connetctions = new CopyOnWriteArrayList<ServerConnection>();

    public static ConnectionInfo getConnectionInfo() {
//		if(connectionInfo == null)
//			connectionInfo = new ConnectionInfo();
//		return connectionInfo;
        return INSTANCE;
    }

    @Override
    public List<ServerConnection> getServerConnections() {
        return this.connetctions;
    }

    public void addConnetion(ServerConnection connetion) {
        connetctions.add(connetion);
    }

}
