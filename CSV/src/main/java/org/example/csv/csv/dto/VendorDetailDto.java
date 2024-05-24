package org.example.csv.csv.dto;

import java.util.Map;

public class VendorDetailDto  {
    private Integer id;

    private String name;

    private Map<String, String> dataSetting;
    private Map<String, String> dataMapping;

    public VendorDetailDto() {
    }

    public VendorDetailDto(Integer id, String name, Map<String, String> dataSetting, Map<String, String> dataMapping) {
        this.id = id;
        this.name = name;
        this.dataSetting = dataSetting;
        this.dataMapping = dataMapping;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getDataSetting() {
        return dataSetting;
    }

    public void setDataSetting(Map<String, String> dataSetting) {
        this.dataSetting = dataSetting;
    }

    public Map<String, String> getDataMapping() {
        return dataMapping;
    }

    public void setDataMapping(Map<String, String> dataMapping) {
        this.dataMapping = dataMapping;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "dataSetting = " + dataSetting + ", " +
                "dataMapping = " + dataMapping + ")";
    }

    public String getName() {
        return name;
    }
}