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

package com.cisco.oss.foundation.monitoring.component.data;

import com.cisco.oss.foundation.monitoring.IComponentInfoMXBean;
import com.cisco.oss.foundation.monitoring.RedundancyMode;

public enum ComponentInfo implements IComponentInfoMXBean {

    INSTANCE;

    private ComponentInfo(){
        if (System.getenv("_RPM_SOFTWARE_NAME") != null) {
            name = System.getenv("_RPM_SOFTWARE_NAME").toUpperCase();
        }

        if (System.getenv("_FULL_SOFTWARE_NAME") != null) {
            fullName = System.getenv("_FULL_SOFTWARE_NAME");
        } else if (System.getenv("_RPM_SOFTWARE_NAME") != null) {
            fullName = System.getenv("_RPM_SOFTWARE_NAME").toUpperCase();
        }

        if (System.getProperty("app.instance.name") != null) {
            instance = System.getProperty("app.instance.name");
        } else if (System.getenv("_RPM_SOFTWARE_NAME") != null) {
            instance = System.getenv("_RPM_SOFTWARE_NAME").toUpperCase() + "Instance1";
        }

        if (System.getenv("_ARTIFACT_VERSION") != null) {
            version = System.getenv("_ARTIFACT_VERSION");
        }
    }

    private String fullName = "DUMMY";
    private String instance = "DUMMYInstance1";
    private String version = "1.0.0";
    private RedundancyMode redundancyMode = RedundancyMode.StandAlone;
    private String name = "Dummy";

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setRedundancyMode(RedundancyMode redundancyMode) {
        this.redundancyMode = redundancyMode;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getFullName() {
        return this.fullName;
    }


    public String getInstance() {
        return this.instance;
    }


    public String getName() {
        return this.name;
    }


    public RedundancyMode getRedundancyMode() {
        return this.redundancyMode;
    }


    public String getVersion() {
        return this.version;
    }


}
