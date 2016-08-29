package com.cardmanager.kdml.cardmanager.DTO;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kdml on 2016-08-23.
 */
public class Cost {
    private Map<String,HashMap> maptest;

    public Cost(){}
    public Cost(Map<String, HashMap> maptest) {
        this.maptest = maptest;
    }

    public Map<String, HashMap> getMaptest() {
        return maptest;
    }

    public void setMaptest(Map<String, HashMap> maptest) {
        this.maptest = maptest;
    }
}
