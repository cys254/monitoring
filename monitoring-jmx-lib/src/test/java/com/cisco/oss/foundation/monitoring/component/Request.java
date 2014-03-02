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
package com.cisco.oss.foundation.monitoring.component;

import java.beans.ConstructorProperties;
import java.lang.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Request {
    String requestType;
    int successRequestCount = 0;
    int failedRequestCount = 0;
    Date lastRequestTime;
    Map<String, Error> errors = new HashMap<String, Error>();

    public Request() {
        errors.put("error1", new Error("error1", Severity.Major));
        errors.put("error2", new Error("error2", Severity.Major));
    }

    @ConstructorProperties({"requestType"})
    public Request(String requestType) {
        this.requestType = requestType;
        errors.put("error1", new Error("error1", Severity.Major));
        errors.put("error2", new Error("error2", Severity.Major));
    }

    public String getRequestType() {
        return requestType;
    }

    public int getSuccessRequestCount() {
        return successRequestCount;
    }

    public int getFailedRequestCount() {
        return failedRequestCount;
    }

    public Date getLastRequestTime() {
        return lastRequestTime;
    }

    public Map<String, Error> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, Error> errors) {
        this.errors = errors;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public void setSuccessRequestCount(int successRequestCount) {
        this.successRequestCount = successRequestCount;
    }

    public void setFailedRequestCount(int failedRequestCount) {
        this.failedRequestCount = failedRequestCount;
    }

    public void setLastRequestTime(Date lastRequestTime) {
        this.lastRequestTime = lastRequestTime;
    }
}
