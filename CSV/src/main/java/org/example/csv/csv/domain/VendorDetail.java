package org.example.csv.csv.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Entity
@Table(name = "vendor_details")
public class VendorDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "data_setting", nullable = false, columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> dataSetting;

    @Column(name = "data_mapping", nullable = false, columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> dataMapping;

    public VendorDetail() {
    }

    public VendorDetail(Integer id, String name, Map<String, String> dataSetting, Map<String, String> dataMapping) {
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

    public String getName() {
        return name;
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
        return "VendorDetail{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dataSetting=" + dataSetting +
                ", dataMapping=" + dataMapping +
                '}';
    }
}