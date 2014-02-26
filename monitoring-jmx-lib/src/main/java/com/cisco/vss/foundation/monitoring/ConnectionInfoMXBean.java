package com.cisco.vss.foundation.monitoring;

import java.util.List;

public interface ConnectionInfoMXBean {

    List<ServerConnection> getServerConnections();
}
