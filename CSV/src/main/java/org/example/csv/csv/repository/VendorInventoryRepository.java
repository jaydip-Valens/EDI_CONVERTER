package org.example.csv.csv.repository;

import org.example.csv.csv.domain.VendorInventory;
import org.example.csv.csv.domain.VendorInventoryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorInventoryRepository extends JpaRepository<VendorInventory, VendorInventoryKey> {
}
