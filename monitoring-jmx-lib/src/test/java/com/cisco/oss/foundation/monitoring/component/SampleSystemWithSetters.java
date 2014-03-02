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

/* 
 * Created on Aug 24, 2010
 */

package com.cisco.oss.foundation.monitoring.component;

import java.lang.*;
import java.util.ArrayList;
import java.util.List;

public class SampleSystemWithSetters implements SampleSystemWithSettersMXBean {
    private String name = "ABC";
    private String instance = "Backup";
    private String version = "1.2.3.R4";
    private Request latestRequest = new Request("LatestRequestType");
    private String myMessage = "Hello World";
    private int myInt = 999;
    private long myLong = 9999999L;
    private Request[] allRequest = {new Request("req1"), new Request("req2")};
    private String[] connectedSystems = {"System1", "System2", "System3"};
    private List<Error> errors = new ArrayList<Error>();

    public SampleSystemWithSetters() {
        errors.add(new Error("Err1", Severity.Critical));
        errors.add(new Error("Err2", Severity.Major));
    }

    @Override
    public String getInstance() {
        return this.instance;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getVersion() {
        return this.version;
    }


    @Override
    public Request getLatestRequest() {
        return this.latestRequest;
    }

    @Override
    public String getMyMessage() {
        return this.myMessage;
    }

    @Override
    public void setLatestRequest(Request latestRequest) {
        this.latestRequest = latestRequest;

    }

    @Override
    public void setMyMessage(String myMessage) {
        this.myMessage = myMessage;
    }

    @Override
    public int getMyInt() {
        return this.myInt;
    }

    @Override
    public void setMyInt(int myInt) {
        this.myInt = myInt;
    }

    @Override
    public long getMyLong() {
        return this.myLong;
    }

    @Override
    public void setMyLong(long myLong) {
        this.myLong = myLong;
    }

    public Request[] getAllRequest() {
        return this.allRequest;
    }

    public void setAllRequest(Request[] allRequest) {
        this.allRequest = allRequest;
    }

    public String[] getConnectedSystems() {
        return this.connectedSystems;
    }

    public void setConnectedSystems(String[] connectedSystems) {
        this.connectedSystems = connectedSystems;
    }

    @Override
    public List<Error> getErrors() {
        return this.errors;
    }

    @Override
    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    @Override
    public String getFullName() {
        // TODO Auto-generated method stub
        return null;
    }

}
