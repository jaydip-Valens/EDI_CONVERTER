package org.example.csv.csv.services;

import org.example.csv.csv.domain.VendorInventory;
import org.springframework.web.multipart.MultipartFile;

public interface VendorInventoryServices {
    String addVendorInventory(MultipartFile ediFile, int id);
}
