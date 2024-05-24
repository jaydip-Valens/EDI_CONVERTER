package org.example.csv.csv.controller;


import org.example.csv.csv.services.CSVToEdiServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
public class CSVToEdiController {

    @Autowired
    private CSVToEdiServices csvToEdiServices;

    @GetMapping(value = "/csv-to-edi846", produces = "text/edi")
    public ResponseEntity<String> csvToEdi(@RequestParam(value = "csvFile") MultipartFile csvFile) {
        try {
            Map<String, String> resultMap = csvToEdiServices.csvToEdiConverter(csvFile);
            return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resultMap.get("fileName") + "\"").body(resultMap.get("data"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
