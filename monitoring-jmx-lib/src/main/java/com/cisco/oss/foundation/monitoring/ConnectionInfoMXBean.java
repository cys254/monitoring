package com.cisco.oss.foundation.monitoring;

import java.util.List;

public interface ConnectionInfoMXBean {

    List<ServerConnection> getServerConnections();
}
