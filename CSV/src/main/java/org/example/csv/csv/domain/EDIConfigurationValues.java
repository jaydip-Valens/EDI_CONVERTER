package org.example.csv.csv.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "edi")
public class EDIConfigurationValues {
    private String senderId;

    private String receiverId;

    private String isaInterchangeControlVersionNumber;

    private String isaInterchangeControlNumber;

    private String isaAcknowledgmentRequested;

    private String isaUsageIndicator;

    private String gsGroupControlNumber;

    private String gsResponsibleAgencyCode;

    private String gsVersionIdentifierCode;

    private String stTransactionSetControlNumber;

    private String biaReferenceIdentification;

    private String refReferenceIdentification;

    private String n1EntityIdentifierCode;

    private String ctpPriceIdentifierCode;

    private String qtyQuantityQualifier;

    private String qtyMeasurementCode;

    private String dtmDateTimeQualifier;

    private String isaAuthorizationInformation;

    private String isaSecurityInformation;

    private String isaAuthorizationInformationQualifier;

    private String isaSecurityInformationQualifier;

    private String isaInterchangeIDQualifier;

    private String refReferenceIdentificationQualifier;

    private String linProductServiceIDQualifier;

    private String linProductServiceIDQualifierSecond;

    private String biaTransactionSetPurposeCode;

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getIsaInterchangeControlVersionNumber() {
        return isaInterchangeControlVersionNumber;
    }

    public void setIsaInterchangeControlVersionNumber(String isaInterchangeControlVersionNumber) {
        this.isaInterchangeControlVersionNumber = isaInterchangeControlVersionNumber;
    }

    public String getIsaInterchangeControlNumber() {
        return isaInterchangeControlNumber;
    }

    public void setIsaInterchangeControlNumber(String isaInterchangeControlNumber) {
        this.isaInterchangeControlNumber = isaInterchangeControlNumber;
    }

    public String getIsaAcknowledgmentRequested() {
        return isaAcknowledgmentRequested;
    }

    public void setIsaAcknowledgmentRequested(String isaAcknowledgmentRequested) {
        this.isaAcknowledgmentRequested = isaAcknowledgmentRequested;
    }

    public String getIsaUsageIndicator() {
        return isaUsageIndicator;
    }

    public void setIsaUsageIndicator(String isaUsageIndicator) {
        this.isaUsageIndicator = isaUsageIndicator;
    }

    public String getGsGroupControlNumber() {
        return gsGroupControlNumber;
    }

    public void setGsGroupControlNumber(String gsGroupControlNumber) {
        this.gsGroupControlNumber = gsGroupControlNumber;
    }

    public String getGsResponsibleAgencyCode() {
        return gsResponsibleAgencyCode;
    }

    public void setGsResponsibleAgencyCode(String gsResponsibleAgencyCode) {
        this.gsResponsibleAgencyCode = gsResponsibleAgencyCode;
    }

    public String getGsVersionIdentifierCode() {
        return gsVersionIdentifierCode;
    }

    public void setGsVersionIdentifierCode(String gsVersionIdentifierCode) {
        this.gsVersionIdentifierCode = gsVersionIdentifierCode;
    }

    public String getStTransactionSetControlNumber() {
        return stTransactionSetControlNumber;
    }

    public void setStTransactionSetControlNumber(String stTransactionSetControlNumber) {
        this.stTransactionSetControlNumber = stTransactionSetControlNumber;
    }

    public String getBiaReferenceIdentification() {
        return biaReferenceIdentification;
    }

    public void setBiaReferenceIdentification(String biaReferenceIdentification) {
        this.biaReferenceIdentification = biaReferenceIdentification;
    }

    public String getRefReferenceIdentification() {
        return refReferenceIdentification;
    }

    public void setRefReferenceIdentification(String refReferenceIdentification) {
        this.refReferenceIdentification = refReferenceIdentification;
    }

    public String getN1EntityIdentifierCode() {
        return n1EntityIdentifierCode;
    }

    public void setN1EntityIdentifierCode(String n1EntityIdentifierCode) {
        this.n1EntityIdentifierCode = n1EntityIdentifierCode;
    }

    public String getCtpPriceIdentifierCode() {
        return ctpPriceIdentifierCode;
    }

    public void setCtpPriceIdentifierCode(String ctpPriceIdentifierCode) {
        this.ctpPriceIdentifierCode = ctpPriceIdentifierCode;
    }

    public String getQtyQuantityQualifier() {
        return qtyQuantityQualifier;
    }

    public void setQtyQuantityQualifier(String qtyQuantityQualifier) {
        this.qtyQuantityQualifier = qtyQuantityQualifier;
    }

    public String getQtyMeasurementCode() {
        return qtyMeasurementCode;
    }

    public void setQtyMeasurementCode(String qtyMeasurementCode) {
        this.qtyMeasurementCode = qtyMeasurementCode;
    }

    public String getDtmDateTimeQualifier() {
        return dtmDateTimeQualifier;
    }

    public void setDtmDateTimeQualifier(String dtmDateTimeQualifier) {
        this.dtmDateTimeQualifier = dtmDateTimeQualifier;
    }

    public String getIsaAuthorizationInformation() {
        return isaAuthorizationInformation;
    }

    public void setIsaAuthorizationInformation(String isaAuthorizationInformation) {
        this.isaAuthorizationInformation = isaAuthorizationInformation;
    }

    public String getIsaSecurityInformation() {
        return isaSecurityInformation;
    }

    public void setIsaSecurityInformation(String isaSecurityInformation) {
        this.isaSecurityInformation = isaSecurityInformation;
    }

    public String getIsaAuthorizationInformationQualifier() {
        return isaAuthorizationInformationQualifier;
    }

    public void setIsaAuthorizationInformationQualifier(String isaAuthorizationInformationQualifier) {
        this.isaAuthorizationInformationQualifier = isaAuthorizationInformationQualifier;
    }

    public String getIsaSecurityInformationQualifier() {
        return isaSecurityInformationQualifier;
    }

    public void setIsaSecurityInformationQualifier(String isaSecurityInformationQualifier) {
        this.isaSecurityInformationQualifier = isaSecurityInformationQualifier;
    }

    public String getIsaInterchangeIDQualifier() {
        return isaInterchangeIDQualifier;
    }

    public void setIsaInterchangeIDQualifier(String isaInterchangeIDQualifier) {
        this.isaInterchangeIDQualifier = isaInterchangeIDQualifier;
    }

    public String getRefReferenceIdentificationQualifier() {
        return refReferenceIdentificationQualifier;
    }

    public void setRefReferenceIdentificationQualifier(String refReferenceIdentificationQualifier) {
        this.refReferenceIdentificationQualifier = refReferenceIdentificationQualifier;
    }

    public String getBiaTransactionSetPurposeCode() {
        return biaTransactionSetPurposeCode;
    }

    public void setBiaTransactionSetPurposeCode(String biaTransactionSetPurposeCode) {
        this.biaTransactionSetPurposeCode = biaTransactionSetPurposeCode;
    }

    public String getLinProductServiceIDQualifier() {
        return linProductServiceIDQualifier;
    }

    public void setLinProductServiceIDQualifier(String linProductServiceIDQualifier) {
        this.linProductServiceIDQualifier = linProductServiceIDQualifier;
    }

    public String getLinProductServiceIDQualifierSecond() {
        return linProductServiceIDQualifierSecond;
    }

    public void setLinProductServiceIDQualifierSecond(String linProductServiceIDQualifierSecond) {
        this.linProductServiceIDQualifierSecond = linProductServiceIDQualifierSecond;
    }
}
