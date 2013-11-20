package ca.delmar.api.controller;

import ca.delmar.api.domain.ShipmentStatus;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/shipment/status")
public class ShipmentStatusController {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @RequestMapping()
    @ResponseBody
    public ShipmentStatus search(@RequestParam(value = "carrier", required = false) String carrierCode,
                       @RequestParam(value = "ccn", required = false) String ccn) {
        if (carrierCode.isEmpty() || ccn.isEmpty()) return null;
        return findShipmentStatusBy(carrierCode, ccn);
    }

    // --------------------- service methods ---------------
    public ShipmentStatus findShipmentStatusBy(String carrierCode, String cargoControl) {
        List<ShipmentStatus> result = jdbcTemplate.query(FIND_PARS_BY_CARRIER_BILL, new Object[]{carrierCode.trim().toUpperCase(), cargoControl.trim().toUpperCase()}, new ShipmentStatusMapper());
        return result.size() > 0 ? result.get(0) : null;
    }

    private static final class ShipmentStatusMapper implements RowMapper<ShipmentStatus> {
        public ShipmentStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
            ShipmentStatus shipmentStatus = new ShipmentStatus();
            shipmentStatus.setFileNumber(rs.getString("file#"));
            shipmentStatus.setCarrierCode(rs.getString("cargo1"));
            shipmentStatus.setCargoControlNumber(rs.getString("cargo2"));
            shipmentStatus.setPortName(rs.getString("port_name"));
            shipmentStatus.setPortNumber(rs.getString("port#"));
            shipmentStatus.setQuantity(rs.getString("qty"));
            shipmentStatus.setUom(rs.getString("pkg"));
            shipmentStatus.setShipperReference(rs.getString("po#"));
            if (rs.getDate("ACCEPTED_DATE") != null) {
                Calendar eventDate = Calendar.getInstance();
                Date date = rs.getDate("ACCEPTED_DATE");
                int hour = rs.getInt("ACCEPTED_HOUR");
                int minute = rs.getInt("ACCEPTED_MIN");
                eventDate.setTime(date);
                eventDate.set(Calendar.HOUR_OF_DAY, hour);
                eventDate.set(Calendar.MINUTE, minute);
                shipmentStatus.setCustomsAcceptedDate(eventDate.getTime());
            }
            if (rs.getDate("RELEASED_DATE") != null) {
                Calendar eventDate1 = Calendar.getInstance();
                Date date1 = rs.getDate("RELEASED_DATE");
                int hour1 = rs.getInt("RELEASED_HOUR");
                int minute1 = rs.getInt("RELEASED_MIN");
                eventDate1.setTime(date1);
                eventDate1.set(Calendar.HOUR_OF_DAY, hour1);
                eventDate1.set(Calendar.MINUTE, minute1);
                shipmentStatus.setCustomsReleasedDate(eventDate1.getTime());
            }
            return shipmentStatus;
        }
    }

    public static final String FIND_PARS_BY_CARRIER_BILL =
            "SELECT b.file#, b.cargo1, b.cargo2, b.po#, b.qty, b.pkg, b.port#, p.port_name, " +
                    "T1.EVENT_DT AS ACCEPTED_DATE, T1.EVENT_HR AS ACCEPTED_HOUR, T1.EVENT_MN AS ACCEPTED_MIN, " +
                    "T2.EVENT_DT AS RELEASED_DATE, T2.EVENT_HR AS RELEASED_HOUR, T2.EVENT_MN AS RELEASED_MIN " +
                    "from b3trace b " +
                    "left outer join port p on (b.port# = p.port_code) " +
                    "left outer join tracstat t1 on (b.file# =  t1.file# and t1.event_cd = 'C04') " +
                    "left outer join tracstat t2 on (b.file# =  t2.file# and t2.event_cd = 'C07') " +
                    "where b.cargo1 = ? and b.cargo2 = ?";

}
