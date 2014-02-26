package com.cisco.vss.foundation.monitoring;

public interface DataEntity {

    /**
     * M2 fragmentBase or Data Entity type.
     */
    String getEntityType();

    /**
     * count of the number of entities.
     */
    long getCount();
}
