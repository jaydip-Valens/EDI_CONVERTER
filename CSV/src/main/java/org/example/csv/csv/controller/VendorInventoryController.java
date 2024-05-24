package org.example.csv.csv.controller;

import org.example.csv.csv.services.VendorDetailServices;
import org.example.csv.csv.services.VendorInventoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class VendorInventoryController {

    @Autowired
    private VendorInventoryServices vendorInventoryServices;

    @PostMapping("/inventory")
    public void addInventory(@RequestParam(name = "ediFile") MultipartFile ediFile, @RequestBody int id) {
        vendorInventoryServices.addVendorInventory(ediFile, id);
    }
}
