package ca.delmar.api.controller;

import ca.delmar.api.domain.Pars;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/pars")
public class ParsQueryController {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @RequestMapping()
    @ResponseBody
    public Pars search(@RequestParam(value = "carrier", required = false) String carrierCode,
                       @RequestParam(value = "ccn", required = false) String ccn) {
        if (carrierCode.isEmpty() || ccn.isEmpty()) return null;
        return findByCarrierAndCargoControlNumber(carrierCode, ccn);
    }

    // --------------------- service methods ---------------
    private Pars findByCarrierAndCargoControlNumber(String carrierCode, String cargoControlNumber) {
        if (carrierCode.isEmpty() || cargoControlNumber.isEmpty()) return null;
        List<Pars> result = jdbcTemplate.query(FIND_PARS_BY_CARRIER_CCN, new Object[]{carrierCode.trim().toUpperCase(), cargoControlNumber.trim().toUpperCase()}, new ParsMapper());
        return result.size() > 0 ? result.get(0) : null;
    }

    private static final class ParsMapper implements RowMapper<Pars> {
        public Pars mapRow(ResultSet rs, int rowNum) throws SQLException {
            Pars pars = new Pars();
            pars.setEdiSequence(rs.getInt("edi_sequence"));
            pars.setFileNumber(rs.getString("file#"));
            pars.setAccountSecurityNumber(rs.getString("Accsec#"));
            pars.setTransactionNumber(rs.getString("trans#"));
            pars.setCheckDigit(rs.getString("check_digit"));
            pars.setPortNumber(rs.getString("port#"));
            pars.setCarrierCode(rs.getString("carriercode"));
            pars.setCargoControlNumber(rs.getString("cargocontrol"));
            pars.setReleasedDate(rs.getDate("reldt"));
            return pars;
        }
    }

    public static final String FIND_PARS_BY_CARRIER_CCN = "SELECT file#, Accsec#, trans#, check_digit, nvl(acr.edi_sequence, '-999') as edi_sequence, port#, cargo1 as carriercode, cargo2 as cargocontrol, reldt " +
            "from b3trace left outer join acr on (file#=file_no) where cargo1 = ? and cargo2 = ?";

}
