package ca.delmar.api.controller;

import ca.delmar.api.domain.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jinw
 * Date: 08/03/13
 * Time: 9:42 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/locations")
public class LocationsController {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @RequestMapping()
    public List<Location> index() {
        return getAllList();
    }

    @RequestMapping("/list")
    @ResponseBody
    public List<Location> getAllList() {
        return findAllLocations();
    }

    @RequestMapping("/offices/list")
    @ResponseBody
    public List<Location> getOfficeList() {
        return findAllOfficeLocations();
    }

    @RequestMapping("/agents/list")
    @ResponseBody
    public List<Location> getAgentList() {
        return findAllAgentLocations();
    }

/*
    @RequestMapping("/agents/list")
    @ResponseBody
    public List<Location> getBothAgentList() {
        return findBothAgentLocations();
    }
*/

    @RequestMapping("/agents/air/list")
    @ResponseBody
    public List<Location> getAirAgentList() {
        return findAirAgentLocations();
    }

    @RequestMapping("/agents/ocean/list")
    @ResponseBody
    public List<Location> getOceanAgentList() {
        return findOceanAgentLocations();
    }

    @RequestMapping("/maps")
    public String maps() {
        return "locations/maps";
    }

    // The followings are not real controller related.
    public List<Location> findAllLocations() {
        return jdbcTemplate.query(SQL_FIND_ALL_LOCATIONS, new LocationMapper());
    }

    public List<Location> findAllOfficeLocations() {
        return jdbcTemplate.query(SQL_FIND_OFFICE_LOCATIONS, new LocationMapper());
    }

    public List<Location> findAllAgentLocations() {
        return jdbcTemplate.query(SQL_FIND_AGENT_LOCATIONS, new LocationMapper());
    }

    public List<Location> findAirAgentLocations() {
        return jdbcTemplate.query(SQL_FIND_AIR_AGENT_LOCATIONS, new LocationMapper());
    }

    public List<Location> findOceanAgentLocations() {
        return jdbcTemplate.query(SQL_FIND_OCEAN_AGENT_LOCATIONS, new LocationMapper());
    }

    private static class LocationMapper implements ParameterizedRowMapper<Location> {
        public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
            Location location = new Location();
            location.setName(rs.getString("name"));
            location.setAddress1(rs.getString("address1"));
            location.setAddress2(rs.getString("address2"));
            location.setCity(rs.getString("city"));
            location.setProvState(rs.getString("provstate"));
            location.setZipCode(rs.getString("zipcode"));
            location.setCountry(rs.getString("country"));
            location.setTel(rs.getString("tel"));
            location.setFax(rs.getString("fax"));
            location.setLocationType(rs.getString("locationType"));
            location.setService(rs.getString("service"));
            return location;
        }
    }

    public static final String SQL_FIND_ALL_LOCATIONS = "SELECT * FROM OFFICE_AGENT_LOCATION";
    public static final String SQL_FIND_OFFICE_LOCATIONS = "SELECT * FROM OFFICE_AGENT_LOCATION WHERE LOCATIONTYPE = 'OFFICE'";
    public static final String SQL_FIND_AGENT_LOCATIONS = "SELECT * FROM OFFICE_AGENT_LOCATION WHERE LOCATIONTYPE = 'AGENT'";
    public static final String SQL_FIND_AIR_AGENT_LOCATIONS = "SELECT * FROM OFFICE_AGENT_LOCATION WHERE LOCATIONTYPE = 'AGENT' AND SERVICE = 2";
    public static final String SQL_FIND_OCEAN_AGENT_LOCATIONS = "SELECT * FROM OFFICE_AGENT_LOCATION WHERE LOCATIONTYPE = 'AGENT' AND SERVICE = 3";

}
