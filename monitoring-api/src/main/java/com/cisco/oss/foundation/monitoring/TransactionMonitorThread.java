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

import com.cisco.oss.foundation.monitoring.serverconnection.ConnectionInfo;
import com.cisco.oss.foundation.monitoring.serverconnection.ServerConnectionImp;

import java.util.Date;

public class TransactionMonitorThread extends Thread {

    private static TransactionMonitorThread transactionMonitorThread;
    private boolean isThreadRunning = false;

    private TransactionMonitorThread() {

    }

    public static TransactionMonitorThread getInstance() {
        if (transactionMonitorThread == null) {
            transactionMonitorThread = new TransactionMonitorThread();
            transactionMonitorThread.setDaemon(true);
        }
        return transactionMonitorThread;
    }

    public void startTread() {
        if (this.isThreadRunning == false) {
            this.start();
        }
        this.isThreadRunning = true;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(60000);
                updateServerConnections();
            } catch (InterruptedException e) {

            } catch (Exception e) {
            }
        }
    }

    private void updateServerConnections() {
        for (int index = 0; index < ConnectionInfo.INSTANCE.getServerConnections().size(); index++) {
            ServerConnectionImp serverConnection = (ServerConnectionImp) ConnectionInfo.INSTANCE.getServerConnections().get(index);
            if (serverConnection.getTransactionOpen() == TransactionOpen.Open) {
                ConnectionInfo.INSTANCE.serverConnectorActor.tell().updateOpenSince(serverConnection,calculateOpenTransactionTime(serverConnection.getLastTransactionStartTime()));
//                connection.setOpenSince(calculateOpenTransactionTime(connection.getLastTransactionStartTime()));
            }
        }
    }

    private long calculateOpenTransactionTime(Date startDate) {
        long transactionTime = (new Date()).getTime() - startDate.getTime();
        transactionTime = transactionTime / 1000;
        transactionTime = transactionTime / 60;
        return transactionTime;
    }

}
