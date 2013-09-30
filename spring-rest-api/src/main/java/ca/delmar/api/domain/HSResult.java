package ca.delmar.api.domain;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: jinw
 * Date: 20/08/13
 * Time: 10:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class HSResult implements Serializable {
    public String hstart;
    public String hsdtyp;
    public String hsdcod;
    public String hsdrat;
    public String country_code;
    public String country_name;
    public String tariff_treatment;
    public String tariff_name;

    public String getHstart() {
        return hstart;
    }

    public void setHstart(String hstart) {
        this.hstart = hstart;
    }

    public String getHsdtyp() {
        return hsdtyp;
    }

    public void setHsdtyp(String hsdtyp) {
        this.hsdtyp = hsdtyp;
    }

    public String getHsdcod() {
        return hsdcod;
    }

    public void setHsdcod(String hsdcod) {
        this.hsdcod = hsdcod;
    }

    public String getHsdrat() {
        return hsdrat;
    }

    public void setHsdrat(String hsdrat) {
        this.hsdrat = hsdrat;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getTariff_treatment() {
        return tariff_treatment;
    }

    public void setTariff_treatment(String tariff_treatment) {
        this.tariff_treatment = tariff_treatment;
    }

    public String getTariff_name() {
        return tariff_name;
    }

    public void setTariff_name(String tariff_name) {
        this.tariff_name = tariff_name;
    }
}
