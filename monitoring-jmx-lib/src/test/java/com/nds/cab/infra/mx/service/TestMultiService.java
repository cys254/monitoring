package com.cisco.vss.foundation.monitoring.service;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Assert;
import org.junit.Test;

import com.cisco.vss.foundation.monitoring.CommunicationInfo;
import com.cisco.vss.foundation.monitoring.MonitoringAgent;
import com.cisco.vss.foundation.monitoring.ServiceDetails;

public class TestMultiService {

    @Test
    public void testManyServices() {

        MonitoringAgent.getInstance().register();

        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        int numOfServices = 500;

        final CountDownLatch latch = new CountDownLatch(numOfServices);

        final ServiceDetails serviceDetails = new ServiceDetails("default desc", "test", "junit", 12345);
        for (int i = 0; i < numOfServices; i++) {

            threadPool.execute(new Runnable() {

                @Override
                public void run() {
                    long nanoTime = System.nanoTime();
                    String apiName = nanoTime + "";
                    CommunicationInfo.getCommunicationInfo().transactionStarted(serviceDetails, apiName, 125);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    CommunicationInfo.getCommunicationInfo().transactionFinished(serviceDetails, apiName, (nanoTime % 2 == 0) ? false : true, "kuku");
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

    }

}
