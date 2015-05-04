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

package com.cisco.oss.foundation.monitoring.service;

import java.util.Collection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.cisco.oss.foundation.monitoring.MonitoringAgentFactory;
import com.cisco.oss.foundation.monitoring.RMIMonitoringAgent;
import com.cisco.oss.foundation.monitoring.services.*;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.SlidingTimeWindowReservoir;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.cisco.oss.foundation.monitoring.CommunicationInfo;

public class TestMultiService {

    @Before
    public void init(){
//        RMIMonitoringAgent.getInstance();
    }


    @Test
    public void testManyServices() throws Exception {

        MonitoringAgentFactory.getInstance().register(new PropertiesConfiguration(TestMultiService.class.getResource("/config.properties").getPath()));

        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        int numOfServices = 1234;

        final CountDownLatch latch = new CountDownLatch(numOfServices);

        final ServiceDetails serviceDetails = new ServiceDetails("default desc", "test", "junit", 12345);
        for (int i = 0; i < numOfServices; i++) {

            final int index = i;

            threadPool.execute(new Runnable() {

                @Override
                public void run() {
                   String apiName = (index%3)+"";
                    CommunicationInfo.getCommunicationInfo().transactionStarted(serviceDetails, apiName, 125);
                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    CommunicationInfo.getCommunicationInfo().transactionFinished(serviceDetails, apiName, (index % 2 == 0) ? false : true, "kuku");
                    latch.countDown();
                }
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int numberOfThreads = Thread.getAllStackTraces().keySet().size();
        Assert.assertTrue(numberOfThreads <= 30);

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Collection<Service> services = ServiceInfo.INSTANCE.getServices();
        System.out.println("services: " + services.size());

        for (Service service : services) {
            System.out.println("service: " + service + ". total: " + service.getTotalRequestCount());
        }

        Assert.assertEquals(3, services.size());

//        try {
//            Thread.sleep(60000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }

    @Ignore
    @Test
    public void testHistogram(){
        final int tpsTime = 10;
        final Histogram tpsHistogram = new Histogram(new SlidingTimeWindowReservoir(tpsTime, TimeUnit.SECONDS));

        ExecutorService threadPool = Executors.newFixedThreadPool(35);

        int numOfServices = 600000;

        final CountDownLatch latch = new CountDownLatch(numOfServices);

        for (int i = 0; i < numOfServices; i++) {

            final int index = i;
            threadPool.execute(new Runnable() {

                @Override
                public void run() {
                    try {
                        if (index >= 10000 & index < 15000) {
                            Thread.sleep(50);
                        }else if (index % 100 == 0){
                            Thread.sleep(1000);
                            tpsHistogram.update(index);
                        }else if (index % 1000 == 0){
                            Thread.sleep(15000);
                            tpsHistogram.update(index);
                        } else if (index % 2 == 0){
                            Thread.sleep(10);
                            tpsHistogram.update(index);
                        }else {
                            Thread.sleep(50);
                            tpsHistogram.update(index);
                        }
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    if(index%1000 == 0){
                        int total = tpsHistogram.getSnapshot().getValues().length;
                        System.out.println("index: " + index +", count: " + tpsHistogram.getCount() + ", snapshot count: " + total);
                        int tps = total/tpsTime;
                        System.out.println("TPS: " + tps);
                    }

                    latch.countDown();
                }
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }



}
