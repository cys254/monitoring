package com.cisco.vss.foundation.monitoring;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public enum ServiceInfo implements ServiceInfoMXBean {

    INSTANCE;

    //	private static ServiceInfo serviceInfo= null;
    private List<Service> services = new CopyOnWriteArrayList<Service>();

    private ServiceInfo() {

    }

    public static ServiceInfo getServiceInfo() {
//		if(serviceInfo == null)
//			serviceInfo = new ServiceInfo();
//		return serviceInfo;
        return INSTANCE;
    }

    @Override
    public List<Service> getServices() {
        return services;
    }

    public void addService(Service service) {
        services.add(service);
    }
}
