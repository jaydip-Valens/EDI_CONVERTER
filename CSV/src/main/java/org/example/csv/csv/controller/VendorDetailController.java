package org.example.csv.csv.controller;

import org.example.csv.csv.dto.VendorDetailDto;
import org.example.csv.csv.services.VendorDetailServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VendorDetailController {

    @Autowired
    private VendorDetailServices vendorDetailServices;

    @PostMapping("/vendor")
    public void addVendor(@RequestBody VendorDetailDto vendorDetailDto) {
        vendorDetailServices.addVendorDetail(vendorDetailDto);
    }
}
