/*
 * Copyright (c) NDS Limited 2010.
 * All rights reserved.
 * No part of this program may be reproduced, translated or transmitted,
 * in any form or by any means, electronic, mechanical, photocopying,
 * recording or otherwise, or stored in any retrieval system of any nature,
 * without written permission of the copyright holder.
 */
package com.nds.cab.component;

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
