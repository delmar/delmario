package ca.delmar.api.controller;

import ca.delmar.api.domain.HSResult;
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

/**
 * Created with IntelliJ IDEA.
 * User: jinw
 * Date: 20/08/13
 * Time: 10:31 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/hstest")
public class HSTestController {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @RequestMapping(value = "/hs")
    @ResponseBody
    public HSResult test1(@RequestParam("h") String h, @RequestParam("c") String c) {
        String hsCode = h.substring(0, 8);
        String countryCode = c.toUpperCase();
        String query =
                "      select hsrates.hstart, hsrates.hsdtyp, hsrates.hsdcod," +
                "             hsrates.hsdrat, country.country_code, country.country_name, " +
                "             tarrif.tariff_treatmen, tarrif.name " +
                "        from hsrates, cnttrf, country, tarrif " +
                "        WHERE  hsrates.hscod = ? AND " +
                "              hsrates.hstart = cnttrf.tariff_treatmen AND " +
                "              hsrates.hsexdt = to_date('0001-01-01', 'YYYY-MM-DD') AND " +
                "              country.country_code = cnttrf.country_code AND " +
                "              tarrif.tariff_treatmen = cnttrf.tariff_treatmen AND " +
                "              country.country_code = ? " +
                "               ORDER BY tarrif.priority ";
        List<HSResult> list = jdbcTemplate.query(query, new Object[]{hsCode, countryCode}, new HSResultMapper());
        return list.size() > 0 ? list.get(0) : null;
    }

    private static final class HSResultMapper implements RowMapper<HSResult> {
        public HSResult mapRow(ResultSet rs, int rowNum) throws SQLException {
            HSResult result = new HSResult();
            result.hstart = rs.getString("hstart");
            result.hsdtyp = rs.getString("hsdtyp");
            result.hsdcod = rs.getString("hsdcod");
            result.hsdrat = rs.getString("hsdrat");
            result.country_code = rs.getString("country_code");
            result.country_name = rs.getString("country_name");
            result.tariff_treatment = rs.getString("tariff_treatmen");
            result.tariff_name = rs.getString("name");
            return result;
        }
    }

}
