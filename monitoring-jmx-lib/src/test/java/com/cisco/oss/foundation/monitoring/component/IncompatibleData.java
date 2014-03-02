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

import java.util.ArrayList;

public class IncompatibleData {
    // Objects with no getters are not allowed
    void doNothing() {

    }

    // Array or Maps of primitive types are not allowed
    public int[] getIntArray() {
        return new int[]{1, 2};
    }

    // Array or Maps of enum constants are not allowed
    public Severity[] getEnumArray() {
        return new Severity[]{Severity.Critical, Severity.Minor};
    }

    // Array or Maps containing null values are not allowed
    public ArrayList<String> getArrayList() {
        ArrayList<String> myArray = new ArrayList<String>();
        myArray.add("abc");
        myArray.add("xyz");
        myArray.add(null);
        return myArray;
    }

}
