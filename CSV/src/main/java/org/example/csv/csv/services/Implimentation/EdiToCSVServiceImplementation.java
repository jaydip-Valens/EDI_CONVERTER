package org.example.csv.csv.services.Implimentation;

import org.example.csv.csv.exceptionHandler.InvalidDataException;
import org.example.csv.csv.exceptionHandler.InvalidFileException;
import org.example.csv.csv.services.EdiToCSVServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static org.example.csv.csv.utils.Util.validateFile;

@Service
public class EdiToCSVServiceImplementation implements EdiToCSVServices {

    private static final Logger logger = LoggerFactory.getLogger(EdiToCSVServiceImplementation.class);
    @Override
    public Map<String, String> ediToCSVConvertor(MultipartFile ediFile) {
        try {
            logger.info("Starting EDI to CSV conversion");
            validateFile(ediFile);
            String content = new String(ediFile.getBytes());
            String[] contentList = content.contains("~GS") ? content.split("~") : content.contains("~\nGS") ? content.replace("~", "").split("\n") : content.split("\n");
            StringBuilder stringBuilder = new StringBuilder();
            Set<String> headers = new LinkedHashSet<>(Arrays.asList("Vendor", "Product Name", "Cost", "Quantity"));
            String[] tempData = null;
            String receiverId = "";
            String vendorName = "";
            stringBuilder.append(headers);
            for (String segment : contentList) {
                String[] segmentData = segment.split("\\*");
                String segmentId = segmentData[0];
                switch (segmentId) {
                    case "ISA":
                        receiverId = segmentData[8].trim();
                        break;
                    case "N1":
                        vendorName = segmentData[segmentData.length - 1].trim().replaceAll("[^a-zA-Z0-9_]", "");
                        break;
                    case "LIN":
                        if (tempData != null) {
                            tempData = Arrays.stream(tempData).map(x -> x == null ? "" : x).toArray(String[]::new);
                            stringBuilder.append(String.join(",", tempData));
                            stringBuilder.append("\n");
                        }
                        tempData = new String[4];
                        tempData[0] = segmentData[segmentData.length - 1];
                        break;
                    case "PID":
                        if (tempData == null) throw new InvalidDataException();
                        if (segment.contains("*08")) {
                            tempData[1] = segmentData[segmentData.length - 1].trim();
                        }
                        break;
                    case "CTP":
                        if (tempData == null) throw new InvalidDataException();
                        tempData[2] = segmentData.length > 3 ? segmentData[3].trim() : "";
                        break;
                    case "QTY":
                        if (tempData == null) throw new InvalidDataException();
                        tempData[3] = segmentData[2].trim();
                        break;
                    default:
                        break;
                }
            }
            stringBuilder.append(String.join(",", tempData));
            String fileName = getFileName(receiverId, vendorName);
            logger.info("EDI to CSV conversion completed successfully.");
            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("result", stringBuilder.toString());
            resultMap.put("fileName", fileName);
            return resultMap;
        } catch (InvalidFileException e) {
            logger.error("Invalid file encountered during EDI to CSV conversion.", e);
            throw new InvalidFileException();
        } catch (Exception e) {
            logger.error("Error occurred during EDI to CSV conversion.", e);
            throw new RuntimeException(e);
        }
    }

    private static String getFileName(String receiverId, String vendorName) {
        String fileName = null;
        if (!receiverId.isBlank()) {
            if (!vendorName.isBlank()) {
                fileName = receiverId + "_" + vendorName + ".csv";
            } else {
                fileName = receiverId + ".csv";
            }
        }
        return fileName;
    }
}
