package com.sample.rajsriniv.datamerge.controller;

import com.sample.rajsriniv.datamerge.io.RecordMerger;
import com.sample.rajsriniv.datamerge.request.DataMergerRequest;
import com.sample.rajsriniv.datamerge.request.DataMergerRequestDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.File;
import java.util.List;

@Controller
public class DataMergerController {
    @Value("${spring.application.name}")
    String appName;

    @Autowired
    private RecordMerger recordMerger;

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("appName", appName);
        return "home";
    }

    @PostMapping(path="/files/combine", consumes="application/json")
    public String combineData(@RequestBody DataMergerRequest dataMergerRequest) throws Exception {

        boolean isValid = dataMergerRequest.validate();
        if (!isValid) {
            return "Request is not valid";
        }
        List<DataMergerRequestDetails> details = dataMergerRequest.getFilesToMerge();
        File firstFile = new File(details.get(0).getFilePath(), details.get(0).getFileName());
        File secondFile = new File(details.get(1).getFilePath(), details.get(1).getFileName());
        recordMerger.mergeFiles(new String[]{firstFile.getAbsolutePath(), secondFile.getAbsolutePath()});
        return "done";
    }
}
