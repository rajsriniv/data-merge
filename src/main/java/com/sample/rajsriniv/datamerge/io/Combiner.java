package com.sample.rajsriniv.datamerge.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Combiner {

    private static final String ID = "ID";
    private static final String SPLIT_BY = ",";

    private final List<Map<String, Object>> combinedRows;

    private final List<String> header;

    /**
     * Constructor for combining tables from different files into one table
     * @param dataFileReaders - List of data file readers
     * @param combinedCsvLocation - File location where output file should be written
     * @throws IOException - on failure
     */
    public Combiner(List<DataFileReader> dataFileReaders, File combinedCsvLocation) throws IOException {

        this.combinedRows = new ArrayList<>();
        this.header = new ArrayList<>();
        combineTables(dataFileReaders);
        writeHeader(combinedCsvLocation);
        writeCsvFile(combinedCsvLocation);
    }

    /**
     * Combine tables from different files
     * @param dataFileReaders - List of data file readers which should be combined
     */
    private void combineTables(List<DataFileReader> dataFileReaders) {
        Set<String> tempHeaders = new HashSet<>();
        Map<Integer, Map<String, Object>> table = new HashMap<>();
        for (DataFileReader dataFileReader : dataFileReaders) {
            Map<Integer, Map<String, Object>> records = dataFileReader.getRecordMap();
            for (Integer id : records.keySet()) {
                Map<String, Object> record = records.get(id);
                tempHeaders.addAll(record.keySet());
                if (table.containsKey(id)) {
                    Map<String, Object> existingRecord = table.get(id);
                    existingRecord.putAll(record);
                    table.put(id, existingRecord);
                } else {
                    table.put(id, record);
                }
            }
        }
        header.addAll(tempHeaders);
        header.remove(ID);
        header.add(0, ID);
        this.combinedRows.addAll(table.values());
        sortRowsById();
    }

    /**
     * Sort the combined table by id in ascending order
     */
    private void sortRowsById() {
        combinedRows.sort(Comparator.comparing(o -> o.get(ID).toString()));
    }

    /**
     * Write the header to the output file
     * @param csvFileLocation - Output file location
     * @throws IOException - on failure
     */
    private void writeHeader(File csvFileLocation) throws IOException {
        try (FileWriter fw = new FileWriter(csvFileLocation)) {
            StringBuilder header = new StringBuilder();
            for (String key : this.header) {
                header.append(key).append(SPLIT_BY);
            }
            header.deleteCharAt(header.length() - 1).append("\n");
            fw.append(header.toString());
        }
    }

    /**
     * Write data to output file
     * @param csvFileLocation - Output file location
     * @throws IOException - On failure
     */
    private void writeCsvFile(File csvFileLocation) throws IOException {
        try (FileWriter fw = new FileWriter(csvFileLocation, true)) {
            for (Map<String, Object> row : combinedRows) {
                StringBuilder sb = new StringBuilder();
                for (String key : this.header) {
                    String value = (String) row.get(key);
                    //If value is null, replace with empty string
                    if (value == null) {
                        value = "";
                    }
                    sb.append(value).append(SPLIT_BY);
                }
                sb.deleteCharAt(sb.length() - 1).append("\n");
                fw.append(sb.toString());
            }
        }
    }
}
