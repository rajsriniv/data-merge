package com.sample.rajsriniv.datamerge.io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class HtmlReader implements DataFileReader{
    
    private static final String TABLE = "table";
    private static final String ROW = "tr";
    private static final String COLUMN = "td";
    private static final String HEADER_COLUMN = "th";
    private final Map<Integer, Map<String, Object>> recordMap;

    public HtmlReader(File htmlFileLocation) throws NumberFormatException, IOException {
        this.recordMap = new HashMap<>();
        readHtmlFile(htmlFileLocation);
    }

    public Map<Integer, Map<String, Object>> getRecordMap() {
        return recordMap;
    }

    private void readHtmlFile(File htmlFileLocation) throws IOException, NumberFormatException {
        Document doc = Jsoup.parse(htmlFileLocation, StandardCharsets.UTF_8.toString());
        Element table = doc.select(TABLE).get(0);
        Elements rows = table.select(ROW);
        Element columnNames = rows.get(0);
        Elements columnName = columnNames.select(HEADER_COLUMN);
        String[] header = new String[columnName.size()];
        for (int i = 0;i < columnName.size();i++) {
            header[i] = columnName.get(i).text();
        }
        for(int i = 1;i < rows.size();i++) {
            Element row = rows.get(i);
            Elements columns = row.select(COLUMN);
            Map<String, Object> rowMap = new HashMap<>();
            for (int j = 0;j < columns.size();j++) {
                rowMap.put(header[j],
                        Parser.unescapeEntities(columns.get(j).text(), true)
                                .replace("\u00a0", ""));
            }
            this.recordMap.put(Integer.parseInt((String) rowMap.get("ID")), rowMap);
        }
    }
}
