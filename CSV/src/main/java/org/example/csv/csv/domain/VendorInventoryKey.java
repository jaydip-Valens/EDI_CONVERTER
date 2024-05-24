package org.example.csv.csv.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

public class VendorInventoryKey implements Serializable {

    @Column(name = "sku", length = Integer.MAX_VALUE)
    private String sku;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vendor_id")
    private VendorDetail vendor;

    public VendorInventoryKey() {
    }

    public VendorInventoryKey(String sku, VendorDetail vendor) {
        this.sku = sku;
        this.vendor = vendor;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VendorInventoryKey that = (VendorInventoryKey) o;
        return Objects.equals(sku, that.sku) && Objects.equals(vendor, that.vendor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sku, vendor);
    }
}
