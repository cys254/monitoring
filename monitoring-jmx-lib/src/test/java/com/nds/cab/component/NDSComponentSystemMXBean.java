/*
 * Copyright (c) NDS Limited 2010.
 * All rights reserved.
 * No part of this program may be reproduced, translated or transmitted,
 * in any form or by any means, electronic, mechanical, photocopying,
 * recording or otherwise, or stored in any retrieval system of any nature,
 * without written permission of the copyright holder.
 */
package com.nds.cab.component;

import com.cisco.oss.foundation.monitoring.MonitoringMXBean;

public interface NDSComponentSystemMXBean extends MonitoringMXBean {
    public String getCommandLine();
}
