package com.example;

import java.util.List;

public interface CustomerService {

    List<Customer> findAll();

    void update(Customer customer);

}
