package org.example.csv.csv.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.example.csv.csv.domain.VendorInventory;
import org.example.csv.csv.domain.VendorInventoryKey;
import org.example.csv.csv.dto.VendorDetailDto;

import java.io.Serializable;
/**
 * DTO for {@link VendorInventory}
 */
public class VendorInventoryDto {
    private VendorInventoryKey id;
    private String sku;
    private VendorDetailDto vendor;
    private Integer quantity;
    private Integer unitCost;
    private String productTitle;
    private String upc;

    public VendorInventoryDto(VendorInventoryKey id, String sku, VendorDetailDto vendor, Integer quantity, Integer unitCost, String productTitle, String upc) {
        this.id = id;
        this.sku = sku;
        this.vendor = vendor;
        this.quantity = quantity;
        this.unitCost = unitCost;
        this.productTitle = productTitle;
        this.upc = upc;
    }

    public VendorInventoryKey getId() {
        return id;
    }

    public void setId(VendorInventoryKey id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public VendorDetailDto getVendor() {
        return vendor;
    }

    public void setVendor(VendorDetailDto vendor) {
        this.vendor = vendor;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Integer unitCost) {
        this.unitCost = unitCost;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "sku = " + sku + ", " +
                "vendor = " + vendor + ", " +
                "quantity = " + quantity + ", " +
                "unitCost = " + unitCost + ", " +
                "productTitle = " + productTitle + ", " +
                "upc = " + upc + ")";
    }
}