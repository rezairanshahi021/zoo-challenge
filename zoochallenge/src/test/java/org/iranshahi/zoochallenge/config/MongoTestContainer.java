package org.iranshahi.zoochallenge.config;

import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * Singleton MongoDBContainer for all integration tests.
 * Ensures Mongo starts only once for the entire test suite.
 *
 * @author Reza iranshahi
 * @since 10 Oct 2025
 */
public class MongoTestContainer extends MongoDBContainer {

    private static final String IMAGE_VERSION = "mongo:7.0.6";
    private static MongoTestContainer container;

    private MongoTestContainer() {
        super(DockerImageName.parse(IMAGE_VERSION));
        withReuse(true);
    }

    public static synchronized MongoTestContainer getInstance() {
        if (container == null) {
            container = new MongoTestContainer();
            container.start();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("spring.data.mongodb.uri", getReplicaSetUrl());
    }

    @Override
    public void stop() {
        // Do nothing — JVM shutdown hook will handle it.
    }
}