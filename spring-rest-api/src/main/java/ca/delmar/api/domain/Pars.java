package ca.delmar.api.domain;

import java.io.Serializable;
import java.util.Date;

public class Pars implements Serializable {
    private String fileNumber;
    private String accountSecurityNumber;
    private String transactionNumber;
    private String checkDigit;
    private String carrierCode;
    private String cargoControlNumber;
    private String portNumber;
    private int ediSequence;
    private String status;
    private Date releasedDate;

    public int getEdiSequence() {
        return ediSequence;
    }

    public void setEdiSequence(int ediSequence) {
        this.ediSequence = ediSequence;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }

    public String getAccountSecurityNumber() {
        return accountSecurityNumber;
    }

    public void setAccountSecurityNumber(String accountSecurityNumber) {
        this.accountSecurityNumber = accountSecurityNumber;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getCheckDigit() {
        return checkDigit;
    }

    public void setCheckDigit(String checkDigit) {
        this.checkDigit = checkDigit;
    }

    public String getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

    public String getCargoControlNumber() {
        return cargoControlNumber;
    }

    public void setCargoControlNumber(String cargoControlNumber) {
        this.cargoControlNumber = cargoControlNumber;
    }

    public String getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(String portNumber) {
        this.portNumber = portNumber;
    }

    public String getStatus() {
        if (getReleasedDate() != null) {
            status = "Shipment Released.";
        } else {
            switch (ediSequence) {
                case 0:
                    status = "Goods Released.";
                    break;
                case 2:
                    status = "Pars submitted.";
                    break;
                case 4:
                    status = "Pars submitted.";
                    break;
                case 5:
                    status = "Referred for Exam.";
                    break;
                case -1:
                    status = "Pars Accepted.";
                    break;
                default:
                    status = "Pars submission pending - contact delmar.";
            }
        }
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getReleasedDate() {
        return releasedDate;
    }

    public void setReleasedDate(Date releasedDate) {
        this.releasedDate = releasedDate;
    }
}
