package com.learning.testing.service.impl;

import com.learning.testing.service.DataService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

//@Service
//@Profile("dev")
public class DataServiceImplDev implements DataService {
    @Override
    public String getData() {
        return "Development Data";
    }
}


