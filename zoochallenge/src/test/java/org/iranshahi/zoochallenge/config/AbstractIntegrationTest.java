package org.iranshahi.zoochallenge.config;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

/**
 * Base class for all integration tests.
 * Starts MongoDB container ONCE per test run (not per test class).
 *
 * @author Reza Iranshahi
 * @since 10 Oct 2025
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public abstract class AbstractIntegrationTest {

    private static final MongoTestContainer mongo = MongoTestContainer.getInstance();

    @BeforeAll
    static void setup() {
        // در اینجا اطمینان حاصل می‌کنیم که mongo فقط یک بار استارت شود
        System.out.println("🔗 Using MongoDB container at: " + mongo.getReplicaSetUrl());
    }

    @DynamicPropertySource
    static void mongoProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongo::getReplicaSetUrl);
    }
}