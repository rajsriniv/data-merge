package com.sample.rajsriniv.datamerge.io;

import java.util.Map;

public interface DataFileReader {
    
    Map<Integer, Map<String, Object>> getRecordMap();

}
