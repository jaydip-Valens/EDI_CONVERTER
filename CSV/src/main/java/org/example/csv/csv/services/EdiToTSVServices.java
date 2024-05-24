package org.example.csv.csv.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface EdiToTSVServices {
    File ediToTSVConvertor(MultipartFile file) throws IOException;
}
