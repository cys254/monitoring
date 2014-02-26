package com.cisco.oss.foundation.monitoring;

import java.util.Date;

public class TransactionMonitorThread extends Thread {

    private static TransactionMonitorThread transactionMonitorThread;
    private boolean isThreadRunning = false;

    private TransactionMonitorThread() {

    }

    public static TransactionMonitorThread getInstance() {
        if (transactionMonitorThread == null) {
            transactionMonitorThread = new TransactionMonitorThread();
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
        for (int index = 0; index < MonitoringAgent.getConnectionInfo().getServerConnections().size(); index++) {
            ServerConnectionImp connection = (ServerConnectionImp) MonitoringAgent.getConnectionInfo().getServerConnections().get(index);
            if (connection.getTransactionOpen() == TransactionOpen.Open) {
                connection.setOpenSince(calculateOpenTransactionTime(connection.getLastTransactionStartTime()));
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
