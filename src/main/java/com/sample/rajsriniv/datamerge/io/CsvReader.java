package com.sample.rajsriniv.datamerge.io;

import au.com.bytecode.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvReader implements DataFileReader {

    private static final Character SPLIT_BY = ',';
    private final Map<Integer, Map<String, Object>> recordMap;

    public CsvReader(File csvFileLocation) throws IOException {
        this.recordMap = new HashMap<>();
        readCsvFile(csvFileLocation);
    }

    public Map<Integer, Map<String, Object>> getRecordMap() {
        return recordMap;
    }

    private void readCsvFile(File csvFileLocation) throws IOException {
        try (FileReader fileReader = new FileReader(csvFileLocation);
             CSVReader csvReader = new CSVReader(fileReader, SPLIT_BY)) {
            List<String[]> rows = csvReader.readAll();
            String[] header = rows.get(0);
            for (int i = 1; i < rows.size(); i++) {
                String[] row = rows.get(i);
                Map<String, Object> rowMap = new HashMap<>();
                for (int j = 0;j < row.length;j++) {
                    rowMap.put(header[j], row[j]);
                }
                recordMap.put(Integer.parseInt((String) rowMap.get("ID")), rowMap);
            }
        }
    }
}