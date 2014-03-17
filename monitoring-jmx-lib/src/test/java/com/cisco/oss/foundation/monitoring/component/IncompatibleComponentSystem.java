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

public class IncompatibleComponentSystem implements IncompatibleComponentMXBean {
    IncompatibleData data = new IncompatibleData();

    public IncompatibleData getIncompatibleData() {
        return data;
    }

    public String getInstance() {
        return "Primary";
    }

    public String getName() {
        return "IncompatibleComponent";
    }

    public String getVersion() {
        return "1.2.3.R4";
    }

    public String getHost() {
        return "monitor-ltp2";
    }


    @Override
    public String getFullName() {
        // TODO Auto-generated method stub
        return null;
    }


}
