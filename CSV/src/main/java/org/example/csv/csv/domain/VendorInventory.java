package org.example.csv.csv.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "vendor_inventory")
@IdClass(VendorInventoryKey.class)
public class VendorInventory {

    @Id
    @Column(name = "sku")
    private String sku;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    private VendorDetail vendor;

    private Integer quantity;

    private Integer unitCost;

    private String productTitle;

    private String upc;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public VendorDetail getVendor() {
        return vendor;
    }

    public void setVendor(VendorDetail vendor) {
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

}