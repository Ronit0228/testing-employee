package com.learning.testing.contollers;

import com.learning.testing.TestContainerConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestContainerConfiguration.class)
@AutoConfigureWebTestClient(timeout = "100000")
public class AbstractIntegrationTest {

    @Autowired
    WebTestClient webTestClient;

}
