package org.example.csv.csv.services.Implimentation;

import org.apache.commons.lang3.ArrayUtils;
import org.example.csv.csv.domain.EDIConfigurationValues;
import org.example.csv.csv.exceptionHandler.InvalidFileException;
import org.example.csv.csv.services.CSVToEdiServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static org.example.csv.csv.utils.Util.createStringForSegment;
import static org.example.csv.csv.utils.Util.validateFile;

@Service
public class CSVToEdiServiceImplementation implements CSVToEdiServices {

    private static final Logger logger = LoggerFactory.getLogger(CSVToEdiServiceImplementation.class);
    private static final Random random = new Random();

    @Autowired
    private EDIConfigurationValues ediConfigurationValues;

    public Map<String, String> csvToEdiConverter(MultipartFile csvFile) {
        try {
            validateFile(csvFile);
            logger.info("Starting CSV to EDI conversion");
            LocalDateTime DateTime = LocalDateTime.now();
            int linCount = 0;
            int segmentCount = 0;
            List<String> content = Arrays.stream(new String(csvFile.getBytes()).split("\n")).map(x -> x.replace("\"","")).collect(Collectors.toList());
            StringBuilder stringBuilder = new StringBuilder();
            String[] nameArray = ((csvFile.getOriginalFilename().split("[._]")));
            String vendorName = String.join(" ", ArrayUtils.removeAll(nameArray, 0, nameArray.length - 1));
            String fileName = getFileName(ediConfigurationValues.getReceiverId(), vendorName);
            try {
                List<String> segmentData = new ArrayList<>();
                isaSegmentWriter(segmentData, DateTime, stringBuilder);
                gsSegmentWriter(segmentData, DateTime, stringBuilder);
                stSegmentWriter(segmentData, stringBuilder);
                biaSegmentWriter(segmentData, DateTime, stringBuilder);
                refSegmentWriter(segmentData, stringBuilder);
                n1SegmentWriter(segmentData, vendorName, stringBuilder);
                List<String> segmentNames = List.of(content.get(0).split(","));
                content.remove(0);
                for (String contentLine : content) {
                    String[] data = contentLine.split(",");
                    if (segmentNames.contains("Vendor")) {
                        long randomValue = random.nextLong(1000000000000L);
                        linCount++;
                        segmentCount++;
                        linSegmentWriter(data, segmentData, randomValue, segmentNames, stringBuilder);
                    }
                    if (segmentNames.contains("Product Name")) {
                        if (!data[segmentNames.indexOf("Product Name")].isEmpty()) {
                            segmentCount = pidSegmentWriter(data, segmentData, segmentNames, segmentCount, stringBuilder);
                        }
                    }
                    if (segmentNames.contains("Cost")) {
                        if (!data[segmentNames.indexOf("Cost")].isEmpty()) {
                            segmentCount = ctpSegmentWriter(data, segmentData, segmentNames, segmentCount, stringBuilder);
                        }
                    }
                    if (segmentNames.contains("Quantity")) {
                        segmentCount = qtySegmentWriter(data, segmentData, segmentNames, segmentCount, stringBuilder);
                    }
                    segmentCount = dtmSegmentWriter(segmentData, DateTime, segmentCount, stringBuilder);
                }
                cttSegmentWriter(segmentData, linCount, stringBuilder);
                seSegmentWriter(segmentData, segmentCount, stringBuilder);
                geSegmentWriter(segmentData, stringBuilder);
                ieaSegmentWriter(segmentData, stringBuilder);
            } catch (Exception e) {
                logger.error("Error occurred during CSV to EDI conversion.", e);
                throw new RuntimeException(e);
            }
            logger.info("CSV to EDI conversion completed successfully.");
            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("data" , stringBuilder.toString());
            resultMap.put("fileName", fileName);
            return resultMap;
        } catch (InvalidFileException e) {
            logger.error("Invalid file encountered during CSV to EDI conversion.", e);
            throw new InvalidFileException();
        } catch (Exception e) {
            logger.error("Error occurred during CSV to EDI conversion.", e);
            throw new RuntimeException(e);
        }
    }

    private void linSegmentWriter(String[] data, List<String> segmentData, long randomValue, List<String> segmentNames, StringBuilder stringBuilder) {
        segmentData.add("LIN*");
        segmentData.add(ediConfigurationValues.getLinProductServiceIDQualifier());
        segmentData.add(String.valueOf(randomValue));
        segmentData.add("*");
        segmentData.add(ediConfigurationValues.getLinProductServiceIDQualifierSecond());
        segmentData.add(data[segmentNames.indexOf("Vendor")]);
        String LIN = String.join("*", segmentData);
        stringBuilder.append(LIN).append("~\n");
        segmentData.clear();
    }

    private void ieaSegmentWriter(List<String> segmentData, StringBuilder stringBuilder) {
        segmentData.add("IEA");
        segmentData.add("1");
        segmentData.add(ediConfigurationValues.getIsaInterchangeControlNumber());
        String IEA = String.join("*", segmentData);
        stringBuilder.append(IEA).append("~\n");
        segmentData.clear();
    }

    private void geSegmentWriter(List<String> segmentData, StringBuilder stringBuilder) {
        segmentData.add("GE");
        segmentData.add("1");
        segmentData.add(ediConfigurationValues.getGsGroupControlNumber());
        String GE = String.join("*", segmentData);
        stringBuilder.append(GE).append("~\n");
        segmentData.clear();
    }

    private void seSegmentWriter(List<String> segmentData, int segmentCount, StringBuilder stringBuilder) {
        segmentData.add("SE");
        segmentData.add(String.valueOf(segmentCount + 6));
        segmentData.add(ediConfigurationValues.getStTransactionSetControlNumber());
        String SE = String.join("*", segmentData);
        stringBuilder.append(SE).append("~\n");
        segmentData.clear();
    }

    private int dtmSegmentWriter(List<String> segmentData, LocalDateTime DateTime, int segmentCount, StringBuilder stringBuilder) {
        segmentData.add("DTM");
        segmentData.add(ediConfigurationValues.getDtmDateTimeQualifier());
        segmentData.add(DateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        segmentCount++;
        String DTM = String.join("*", segmentData);
        stringBuilder.append(DTM).append("~\n");
        segmentData.clear();
        return segmentCount;
    }

    private int qtySegmentWriter(String[] data, List<String> segmentData, List<String> segmentNames, int segmentCount, StringBuilder stringBuilder) {
        segmentData.add("QTY");
        segmentData.add(ediConfigurationValues.getQtyQuantityQualifier());
        segmentData.add(data[segmentNames.indexOf("Quantity")]);
        segmentData.add(ediConfigurationValues.getQtyMeasurementCode());
        segmentCount++;
        String QTY = String.join("*", segmentData);
        stringBuilder.append(QTY).append("~\n");
        segmentData.clear();
        return segmentCount;
    }

    private int ctpSegmentWriter(String[] data, List<String> segmentData, List<String> segmentNames, int segmentCount, StringBuilder stringBuilder) {
        segmentData.add("CTP*");
        segmentData.add(ediConfigurationValues.getCtpPriceIdentifierCode());
        segmentData.add(data[segmentNames.indexOf("Cost")]);
        segmentCount++;
        String CTP = String.join("*", segmentData);
        stringBuilder.append(CTP).append("~\n");
        segmentData.clear();
        return segmentCount;
    }

    private void n1SegmentWriter(List<String> segmentData, String vendorName, StringBuilder stringBuilder) {
        segmentData.add("N1");
        segmentData.add(ediConfigurationValues.getN1EntityIdentifierCode());
        segmentData.add(vendorName.isBlank() ? ediConfigurationValues.getReceiverId() : vendorName);
        String N1 = String.join("*", segmentData);
        stringBuilder.append(N1).append("~\n");
        segmentData.clear();
    }

    private void refSegmentWriter(List<String> segmentData, StringBuilder stringBuilder) {
        segmentData.add("REF");
        segmentData.add(ediConfigurationValues.getRefReferenceIdentificationQualifier());
        segmentData.add(ediConfigurationValues.getRefReferenceIdentification());
        String REF = String.join("*", segmentData);
        stringBuilder.append(REF).append("~\n");
        segmentData.clear();
    }

    private void biaSegmentWriter(List<String> segmentData, LocalDateTime DateTime, StringBuilder stringBuilder) {
        segmentData.add("BIA");
        segmentData.add(ediConfigurationValues.getBiaTransactionSetPurposeCode());
        segmentData.add("SI");
        segmentData.add(ediConfigurationValues.getBiaReferenceIdentification());
        segmentData.add(DateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        String BIA = String.join("*", segmentData);
        stringBuilder.append(BIA).append("~\n");
        segmentData.clear();
    }

    private void stSegmentWriter(List<String> segmentData, StringBuilder stringBuilder) {
        segmentData.add("ST*846");
        segmentData.add(ediConfigurationValues.getStTransactionSetControlNumber());
        String ST = String.join("*", segmentData);
        stringBuilder.append(ST).append("~\n");
        segmentData.clear();
    }

    private void gsSegmentWriter(List<String> segmentData, LocalDateTime DateTime, StringBuilder stringBuilder) {
        segmentData.add("GS*IB");
        segmentData.add(ediConfigurationValues.getReceiverId());
        segmentData.add(ediConfigurationValues.getSenderId());
        segmentData.add(DateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        segmentData.add(DateTime.format(DateTimeFormatter.ofPattern("HHmm")));
        segmentData.add(ediConfigurationValues.getGsGroupControlNumber());
        segmentData.add(ediConfigurationValues.getGsResponsibleAgencyCode());
        segmentData.add(ediConfigurationValues.getGsVersionIdentifierCode());
        String GS = String.join("*", segmentData);
        stringBuilder.append(GS).append("~\n");
        segmentData.clear();
    }

    private void isaSegmentWriter(List<String> segmentData, LocalDateTime DateTime, StringBuilder stringBuilder) {
        segmentData.add("ISA");
        segmentData.add(ediConfigurationValues.getIsaAuthorizationInformationQualifier());
        segmentData.add(createStringForSegment(ediConfigurationValues.getIsaAuthorizationInformation(), 10));
        segmentData.add(ediConfigurationValues.getIsaSecurityInformationQualifier());
        segmentData.add(createStringForSegment(ediConfigurationValues.getIsaSecurityInformation(), 10));
        segmentData.add(ediConfigurationValues.getIsaInterchangeIDQualifier());
        segmentData.add(createStringForSegment(ediConfigurationValues.getSenderId(), 15));
        segmentData.add("12");
        segmentData.add(createStringForSegment(ediConfigurationValues.getReceiverId(), 15));
        segmentData.add(DateTime.format(DateTimeFormatter.ofPattern("yyMMdd")));
        segmentData.add(DateTime.format(DateTimeFormatter.ofPattern("HHmm")));
        segmentData.add("U");
        segmentData.add(ediConfigurationValues.getIsaInterchangeControlVersionNumber());
        segmentData.add(ediConfigurationValues.getIsaInterchangeControlNumber());
        segmentData.add(ediConfigurationValues.getIsaAcknowledgmentRequested());
        segmentData.add(ediConfigurationValues.getIsaUsageIndicator());
        String ISA = String.join("*", segmentData);
        stringBuilder.append(ISA).append("*~\n");
        segmentData.clear();
    }

    private static void cttSegmentWriter(List<String> segmentData, int linCount, StringBuilder stringBuilder) {
        segmentData.add("CTT");
        segmentData.add(String.valueOf(linCount));
        String CTT = String.join("*", segmentData);
        stringBuilder.append(CTT).append("~\n");
        segmentData.clear();
    }

    private static int pidSegmentWriter(String[] data, List<String> segmentData, List<String> segmentNames, int segmentCount, StringBuilder stringBuilder) {
        segmentData.add("PID*F");
        segmentData.add("08");
        segmentData.add("*");
        segmentData.add(data[segmentNames.indexOf("Product Name")]);
        segmentCount++;
        String PID = String.join("*", segmentData);
        stringBuilder.append(PID).append("~\n");
        segmentData.clear();
        return segmentCount;
    }

    private static String getFileName(String receiverId, String vendorName) {
        String fileName = null;
        if (!receiverId.isBlank()) {
            if (!vendorName.isBlank()) {
                fileName = receiverId + "_" + vendorName.trim().replace(" ","_") + ".edi";
            } else {
                fileName = receiverId + ".edi";
            }
        }
        return fileName;
    }

}
