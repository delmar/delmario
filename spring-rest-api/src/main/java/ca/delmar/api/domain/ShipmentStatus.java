package ca.delmar.api.domain;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: jinw
 * Date: Mar 9, 2013
 * Time: 4:38:27 PM
 * To change this template use File | Settings | File Templates.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ShipmentStatus", namespace = "http://delmarcargo.com/api/shipment/status")
@XmlType(
        name = "ShipmentStatus",
        namespace = "http://delmarcargo.com/api/shipment/status"
)
public class ShipmentStatus implements Serializable {
    @XmlElement(name = "DelmarFileNumber")
    private String fileNumber;
    @XmlElement(name = "CarrierSCACCode")
    private String carrierCode;
    @XmlElement(name = "CarrierBillNumber")
    private String cargoControlNumber;
    @XmlElement(name = "ShipperReference")
    private String shipperReference;
    @XmlElement(name = "PortofEntryNumber")
    private String portNumber;
    @XmlElement(name = "PortofEntryName")
    private String portName;
    @XmlElement(name = "CustomsAcceptedDate")
    private Date customsAcceptedDate;
    @XmlElement(name = "CustomsReleasedDate")
    private Date customsReleasedDate;
    @XmlElement(name = "ManifestQuantity")
    private String quantity;
    @XmlElement(name = "ManifestUOM")
    private String uom;

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
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

    public Date getCustomsAcceptedDate() {
        return customsAcceptedDate;
    }

    public void setCustomsAcceptedDate(Date customsAcceptedDate) {
        this.customsAcceptedDate = customsAcceptedDate;
    }

    public Date getCustomsReleasedDate() {
        return customsReleasedDate;
    }

    public void setCustomsReleasedDate(Date customsReleasedDate) {
        this.customsReleasedDate = customsReleasedDate;
    }

    public String getShipperReference() {
        return shipperReference;
    }

    public void setShipperReference(String shipperReference) {
        this.shipperReference = shipperReference;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }
}
