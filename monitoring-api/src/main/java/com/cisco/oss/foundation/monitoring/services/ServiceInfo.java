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

package com.cisco.oss.foundation.monitoring.services;

import com.cisco.oss.foundation.monitoring.CommunicationInfo;
import com.google.common.collect.Lists;
import fi.jumi.actors.ActorRef;
import fi.jumi.actors.ActorThread;
import fi.jumi.actors.Actors;
import fi.jumi.actors.MultiThreadedActors;
import fi.jumi.actors.eventizers.dynamic.DynamicEventizerProvider;
import fi.jumi.actors.listeners.CrashEarlyFailureHandler;
import fi.jumi.actors.listeners.NullMessageListener;

import java.util.List;

public enum ServiceInfo implements ServiceInfoMXBean {

    INSTANCE;
    public ServiceActorImpl serviceActorImpl =  new ServiceActorImpl();
    public ActorThread serviceActorThread = null;
    public final ActorRef<ServiceActor> serviceActor = createServiceActor();


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

        return (List)Lists.newArrayList( serviceActorImpl.services.values());


    }


    private ActorRef<ServiceActor> createServiceActor() {
        Actors actors = new MultiThreadedActors(
                CommunicationInfo.INSTANCE.actorsThreadPool,
                new DynamicEventizerProvider(),
                new CrashEarlyFailureHandler(),
                new NullMessageListener()
        );

        // Start up a thread where messages to actors will be executed
        serviceActorThread = actors.startActorThread();
        ActorRef<ServiceActor> srvActor = serviceActorThread.bindActor(ServiceActor.class, serviceActorImpl);
        return srvActor;
    }
}
