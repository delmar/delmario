package ca.delmar.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: jinw
 * Date: 12/03/13
 * Time: 2:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class Location implements Serializable {

    private String name;
    private String address1;
    private String address2;
    private String city;
    private String provState;
    private String zipCode;
    private String country;
    private String tel;
    private String fax;
    private String service;
    private String locationType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public String getFullAddress() {
        StringBuilder sb = new StringBuilder();
        sb.append(address1);
        if (!address2.isEmpty()) sb.append(",").append(address2);
        sb.append(",").append(city);
        if (!provState.isEmpty()) sb.append(",").append(provState);
        if (!zipCode.isEmpty()) sb.append(",").append(zipCode);
        sb.append(",").append(country);
        return sb.toString();
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvState() {
        return provState;
    }

    public void setProvState(String provState) {
        this.provState = provState;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }
}
