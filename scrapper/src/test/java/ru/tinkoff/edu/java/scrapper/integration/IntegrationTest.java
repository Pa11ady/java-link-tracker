package ru.tinkoff.edu.java.scrapper.integration;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class IntegrationTest extends IntegrationEnvironment {

    @Test
    void runContainers() {
        assertThat(SQL_CONTAINER.isCreated(), equalTo(true));
    }
}
