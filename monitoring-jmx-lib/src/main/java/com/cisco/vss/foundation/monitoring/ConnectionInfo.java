package com.cisco.vss.foundation.monitoring;

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
