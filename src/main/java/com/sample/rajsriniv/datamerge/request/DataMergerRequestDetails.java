package com.sample.rajsriniv.datamerge.request;

import org.apache.commons.lang3.StringUtils;

import java.io.File;

public class DataMergerRequestDetails {
    private String fileName;
    private String fileType;
    private String filePath;

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return this.fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Validate the request that is sent to the service.
     * @return true if the request is valid and false otherwise.
     */
    public boolean validate() {
        if (StringUtils.isEmpty(this.fileName) || StringUtils.isEmpty(this.fileType) || StringUtils.isEmpty(this.filePath)) {
            return false;
        }
        File file = new File(this.filePath, this.fileName);
        if (!file.exists() || !file.isFile()) {
            return false;
        }
        return true;
    }
}
