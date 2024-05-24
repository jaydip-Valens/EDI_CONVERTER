package org.example.csv.csv.services.Implimentation;

import com.opencsv.CSVWriter;
import org.example.csv.csv.domain.VendorDetail;
import org.example.csv.csv.domain.VendorInventory;
import org.example.csv.csv.exceptionHandler.InvalidFileException;
import org.example.csv.csv.repository.VendorDetailRepository;
import org.example.csv.csv.repository.VendorInventoryRepository;
import org.example.csv.csv.services.VendorInventoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.csv.csv.utils.Util.validateFile;

@Service
public class VendorInventoryServicesImplementation implements VendorInventoryServices {

    @Autowired
    private VendorInventoryRepository vendorInventoryRepository;

    @Autowired
    private VendorDetailRepository vendorDetailRepository;
    @Override
    public String addVendorInventory(MultipartFile ediFile, int id) {
        try {
            validateFile(ediFile);
            Optional<VendorDetail> optionalVendorDetail = vendorDetailRepository.findById(id);
            if (optionalVendorDetail.isEmpty()) {
                return "Invalid ID";
            }
            String segmentDelimiter = optionalVendorDetail.get().getDataSetting().get("segment_delimiter").toString();
            String content = new String(ediFile.getBytes());
            String[] contentList = content.split(segmentDelimiter);
            List<VendorInventory> vendorInventories = new ArrayList<>();
            try  {
                for (String data : contentList) {
                    final VendorInventory vendorInventory = getVendorInventory(data, optionalVendorDetail.get());
                    vendorInventories.add(vendorInventory);
                }
                vendorInventoryRepository.saveAll(vendorInventories);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (InvalidFileException e) {
            throw new InvalidFileException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "added";
    }

    private static VendorInventory getVendorInventory(String data, VendorDetail vendorDetail) {
        VendorInventory vendorInventory = new VendorInventory();
        if (data.startsWith("LIN*")) {
              String[] temp = data.split("\\*");
              vendorInventory.setSku(temp[temp.length - 1]);
              vendorInventory.setUpc(temp[3]);
          } else if (data.startsWith("PID*F*08")) {
              String[] temp = data.split("\\*");
              vendorInventory.setProductTitle(temp[temp.length - 1].trim());
          } else if (data.startsWith("CTP*")) {
              String[] temp = data.split("\\*");
              vendorInventory.setUnitCost(Integer.parseInt(temp[temp.length - 1]));
          } else if (data.startsWith("QTY*")) {
              String[] temp = data.split("\\*");
              vendorInventory.setQuantity(Integer.parseInt(temp[temp.length - 2]));
          }
        vendorInventory.setVendor(vendorDetail);
        return vendorInventory;
    }
}
