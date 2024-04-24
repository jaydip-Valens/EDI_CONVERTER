package com.edi.converor.service.ServiceImpl;

import com.edi.converor.service.EdiToCsvService;
import com.opencsv.CSVWriter;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.channels.FileChannel;

@Service
public class EdiToCsvServiceImpl implements EdiToCsvService {

    private static final Logger logger = LoggerFactory.getLogger(EdiToCsvServiceImpl.class);

    @Override
    public File ediToCsv(MultipartFile ediFile, String fileType) throws IOException {
        String name = "";
        String receiverNumber = "";
        File csvFile;
        String[] csvRow = new String[4];
        int count = 0;
        char separator;
        logger.info("Start reading data from EDI file.");
        String content = new String(ediFile.getBytes());
        String[] lines = content.contains("~GS") ? content.split("~") : content.split("\n");
        int i = 0;

        if (fileType.equalsIgnoreCase("csv")) {
            csvFile = new File("temp.csv");
            separator = ',';
        } else {
            csvFile = new File("temp.tsv");
            separator = '\t';
        }
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(csvFile),separator,CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)) {
            while (i < lines.length) {
                if (count == 0) {
                    if (lines[i].startsWith("ISA")) {
                        String[] singleData = lines[i].split("\\*");
                        receiverNumber = singleData[8].trim();
                        count++;
                        try {
                            String[] header = {"ProductCode", "ProductName", "Price", "Quantity"};
                            logger.info("Start writing data into " + fileType + " file.");
                            csvWriter.writeNext(header);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        return null;
                    }
                } else if (lines[i].startsWith("N1")) {
                    String[] singleData = lines[i].split("\\*");
                    name = singleData[2].replaceAll("[^a-zA-Z0-9.]", "_");
                    name = name.replace(".", "");
                }
                try {
                    if (lines[i].startsWith("PID*F*08")) {
                        String[] singleData = lines[i].split("\\*");
                        csvRow[1] = singleData[singleData.length - 1].trim();
                    } else if (lines[i].startsWith("LIN**") && lines[i].contains("VP")) {
                        String[] singleData = lines[i].split("\\*");
                        csvRow[0] = (singleData[singleData.length - 1].trim());
                    } else if (lines[i].startsWith("CTP**")) {
                        String[] singleData = lines[i].split("\\*");
                        csvRow[2] = singleData[singleData.length - 1].trim();
                    } else if (lines[i].startsWith("QTY*")) {
                        String[] singleData = lines[i].split("\\*");
                        csvRow[3] = (singleData[singleData.length - 2].trim());
                        csvWriter.writeNext(csvRow);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                i++;
            }
            logger.info("EDI file successfully converted into " + fileType + " file.");
        } catch (Exception e) {
            logger.error("Runtime error: {}",e.getMessage());
            throw new RuntimeException(e);
        }
        String fileName = GetName(name, receiverNumber,fileType);
        new File(String.valueOf(csvFile)).renameTo(new File(fileName));
        return new File(fileName);
    }

    private static String GetName(String name, String receiverNumber, String fileType) {
        String fileName;
        if(!name.equalsIgnoreCase("")){
            fileName = receiverNumber + "_" + name + "." + fileType;
        } else {
            fileName = receiverNumber + "." + fileType;
        }
        return fileName;
    }
}