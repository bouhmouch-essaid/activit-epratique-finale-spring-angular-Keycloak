package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class CustomerMapperTest {

    private CustomerMapper customerMapper;

    @BeforeEach
    public void setUp() {
        customerMapper = new CustomerMapperImpl();
    }
}
