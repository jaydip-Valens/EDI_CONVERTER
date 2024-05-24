package org.example.csv.csv.services.Implimentation;

import org.example.csv.csv.domain.VendorDetail;
import org.example.csv.csv.dto.VendorDetailDto;
import org.example.csv.csv.repository.VendorDetailRepository;
import org.example.csv.csv.services.VendorDetailServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VendorDetailServicesImplementation implements VendorDetailServices {

    @Autowired
    private VendorDetailRepository vendorDetailRepository;

    @Override
    public void addVendorDetail(VendorDetailDto vendorDetailDto) {
        try {
            vendorDetailRepository.save(DtoToEntity(vendorDetailDto));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<VendorDetailDto> getAllVendorDetails() {
        try {
            return vendorDetailRepository.findAll().stream().map(this::EntityToDto).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public VendorDetailDto getVendorDetailById(int id) {
        try {
            Optional<VendorDetail> optionalVendorDetail = vendorDetailRepository.findById(id);
            return optionalVendorDetail.map(this::EntityToDto).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String deleteVendorDetailById(int id) {
        try {
            if (!vendorDetailRepository.existsById(id)) {
                return null;
            }
            vendorDetailRepository.deleteById(id);
            return "deleted";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private VendorDetail DtoToEntity(VendorDetailDto vendorDetailDto) {
        return new VendorDetail(vendorDetailDto.getId(), vendorDetailDto.getName(), vendorDetailDto.getDataSetting(), vendorDetailDto.getDataMapping());
    }

    private VendorDetailDto EntityToDto(VendorDetail vendorDetail) {
        return new VendorDetailDto(vendorDetail.getId(), vendorDetail.getName(), vendorDetail.getDataSetting(),vendorDetail.getDataMapping());
    }
}
