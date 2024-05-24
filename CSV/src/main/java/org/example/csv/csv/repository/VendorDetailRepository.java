package org.example.csv.csv.repository;

import org.example.csv.csv.domain.VendorDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorDetailRepository extends JpaRepository<VendorDetail, Integer> {
}
