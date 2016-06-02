package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;

@Component
public class CustomerService2 {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource2(DataSource dataSource2) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource2);
    }

    public List<Customer> findAll() {
        return jdbcTemplate.query("SELECT id, first_name, last_name FROM customers",
                (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name")));
    }

    public void update(Customer customer) {
        jdbcTemplate.update(
                "UPDATE customers SET first_name=:first_name, last_name=:last_name WHERE id=:id",
                new HashMap<String, Object>() {{
                    put("first_name", customer.getFirstName());
                    put("last_name", customer.getLastName());
                    put("id", customer.getId());
                }}
        );
    }

}
