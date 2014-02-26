/*
 * Copyright (c) NDS Limited 2010.
 * All rights reserved.
 * No part of this program may be reproduced, translated or transmitted,
 * in any form or by any means, electronic, mechanical, photocopying,
 * recording or otherwise, or stored in any retrieval system of any nature,
 * without written permission of the copyright holder.
 */
package com.nds.cab.component;

import com.cisco.oss.foundation.monitoring.RedundancyMode;

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
        return "ndsmonitor-ltp2";
    }


    @Override
    public String getFullName() {
        // TODO Auto-generated method stub
        return null;
    }


}
