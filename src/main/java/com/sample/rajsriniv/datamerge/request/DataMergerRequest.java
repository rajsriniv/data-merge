package com.sample.rajsriniv.datamerge.request;

import java.util.List;

public class DataMergerRequest {

    private List<DataMergerRequestDetails> filesToMerge;

    public List<DataMergerRequestDetails> getFilesToMerge() {
        return this.filesToMerge;
    }

    public void setFilesToMerge(List<DataMergerRequestDetails> filesToMerge) {
        this.filesToMerge = filesToMerge;
    }

    public boolean validate() {
        if (this.filesToMerge.size() != 2) {
            return false;
        }
        for (DataMergerRequestDetails details : filesToMerge) {
            if (!details.validate()) {
                return false;
            }
        }
        return true;
    }
    
}
