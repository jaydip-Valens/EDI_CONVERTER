package org.example.csv.csv.services;

import com.opencsv.exceptions.CsvException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface CSVToEdiServices {
    Map<String, String> csvToEdiConverter(MultipartFile file) throws IOException, CsvException;
}
