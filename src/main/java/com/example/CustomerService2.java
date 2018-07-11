package com.example;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class CustomerService2 implements CustomerService {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerService2(@Qualifier("jdbcTemplate2") NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("CALL find_all_customers()",
                (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name")));
    }

    public List<Customer> findAll(int limit, int offset) {
        return jdbcTemplate.query("SELECT id, first_name, last_name FROM customers LIMIT :limit OFFSET :offset",
                new HashMap<String, Object>() {{
                    put("limit", limit);
                    put("offset", offset);
                }},
                (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name")));
    }

    @Override
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
