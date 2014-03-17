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

import com.cisco.oss.foundation.monitoring.ConnectionStatus;
import com.cisco.oss.foundation.monitoring.TransactionOpen;
import com.cisco.oss.foundation.monitoring.services.Service;
import com.cisco.oss.foundation.monitoring.services.ServiceActor;
import com.cisco.oss.foundation.monitoring.services.ServiceImp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yair Ogen on 3/5/14.
 */
public class ServerConnectionActorImpl implements ServerConnectionActor{
    protected Map<ServerConnection, ServerConnectionImp> serverConnections = new HashMap<>();

    public void startTransaction(ServerConnection serverConnection){
        ServerConnectionImp serverConnectionImp = null;
        if (serverConnections.containsKey(serverConnection)) {
            serverConnectionImp = serverConnections.get(serverConnection);
        } else {
            serverConnectionImp = (ServerConnectionImp) serverConnection;
            serverConnections.put(serverConnection, serverConnectionImp);
        }

        serverConnectionImp.lastTransactionStartTime = new Date();
        serverConnectionImp.totalRequestCount++;
        serverConnectionImp.openSince = 0;
        serverConnectionImp.transactionOpen = TransactionOpen.Open;
    }

    public void endTransaction(ServerConnection serverConnection, boolean isFailed, String description){
        ServerConnectionImp serverConnectionImp = serverConnections.get(serverConnection);

        serverConnectionImp.lastTransactionEndTime = new Date();
        if (isFailed == true) {
            serverConnectionImp.lastFailedRequestTime = new Date();
            serverConnectionImp.failedRequestCount++;
            serverConnectionImp.lastFailedRequestDescription = description;
        } else {
            serverConnectionImp.lastSuccessfulRequestTime = new Date();
        }
        if (serverConnectionImp.lastFailedRequestTime == null || (serverConnectionImp.lastSuccessfulRequestTime.getTime() > serverConnectionImp.lastFailedRequestTime.getTime())) {
            serverConnectionImp.connectionStatus = ConnectionStatus.Healthy;
        } else {
            serverConnectionImp.connectionStatus = ConnectionStatus.Broken;
        }
        serverConnectionImp.transactionOpen = TransactionOpen.Closed;
        serverConnectionImp.lastTransactionProcessingTime = (serverConnectionImp.lastTransactionEndTime.getTime() - serverConnectionImp.lastTransactionStartTime.getTime());
        serverConnectionImp.openSince = 0;
    }

    @Override
    public void updateOpenSince(ServerConnection serverConnection, long openSince) {
        ServerConnectionImp serverConnectionImp = serverConnections.get(serverConnection);
        serverConnectionImp.openSince = openSince;
    }
}
