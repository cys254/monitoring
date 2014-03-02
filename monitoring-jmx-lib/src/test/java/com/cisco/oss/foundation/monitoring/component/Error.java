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
import java.util.Date;

public class Error {
    private String errorType;
    private Severity errorSeverity;
    private int errorCount;
    private Date lastErrorTime;
    private String lastErrorDescription;

    @ConstructorProperties({"errorType", "errorSeverity"})
    public Error(String errorType, Severity errorSeverity) {
        this.errorType = errorType;
        this.errorSeverity = errorSeverity;
        this.errorCount = 0;
        this.lastErrorTime = null;
        this.lastErrorDescription = null;
    }

    public String getErrorType() {
        return errorType;
    }

    public Severity getErrorSeverity() {
        return errorSeverity;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    public Date getLastErrorTime() {
        return lastErrorTime;
    }

    public String getLastErrorDescription() {
        return lastErrorDescription;
    }
}
