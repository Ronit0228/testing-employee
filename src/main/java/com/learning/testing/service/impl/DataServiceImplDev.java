package com.learning.testing.service.impl;

import com.learning.testing.service.DataService;

//@Service
//@Profile("dev")
public class DataServiceImplDev implements DataService {
    @Override
    public String getData() {
        return "Development Data";
    }
}


