package org.example.csv.csv.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface EdiToCSVServices {
    Map<String, String> ediToCSVConvertor(MultipartFile file);
}
