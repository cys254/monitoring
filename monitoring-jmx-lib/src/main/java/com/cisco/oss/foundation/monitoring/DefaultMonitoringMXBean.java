package com.cisco.oss.foundation.monitoring;

public class DefaultMonitoringMXBean implements MonitoringMXBean {

    private static String name;
    private static String version;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getFullName() {
        return name;
    }

    @Override
    public String getInstance() {
        return name;
    }

    @Override
    public String getVersion() {

        return version;
    }


    public static void setEnvironment(String name, String version) {
        DefaultMonitoringMXBean.name = name;
        DefaultMonitoringMXBean.version = version;
    }

}
