/*
 * Copyright (c) NDS Limited 2010.
 * All rights reserved.
 * No part of this program may be reproduced, translated or transmitted,
 * in any form or by any means, electronic, mechanical, photocopying,
 * recording or otherwise, or stored in any retrieval system of any nature,
 * without written permission of the copyright holder.
 */

/* 
 * Created on Aug 26, 2010
 */

package com.nds.cab.component;

import java.util.Date;
import java.util.Map;

import com.cisco.vss.foundation.monitoring.MonitoringMXBean;

public interface AllValidDataMXBean extends MonitoringMXBean {
    Integer getIntWrapper();

    int getInt();

    float getFloat();

    Float getFloatWrapper();

    double getDouble();

    Double getDoubleWrapper();

    Date getDate();

    String getCommandLine();

    Severity getEnum();

    Request getCompositeObject();

    Request getNullObject();

    Request[] getCompositeObjectArray();

    Map<Integer, String> getStringMap();

    Map<Integer, Request> getObjectMap();
}
