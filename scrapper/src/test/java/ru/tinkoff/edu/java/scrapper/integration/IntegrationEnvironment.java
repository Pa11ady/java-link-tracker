package ru.tinkoff.edu.java.scrapper.integration;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.DirectoryResourceAccessor;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Testcontainers
abstract class IntegrationEnvironment {
    protected static final PostgreSQLContainer<?> SQL_CONTAINER;
    private static final Path ROOT_DIRECTORY = Path.of(".").toAbsolutePath().getParent().resolve("migrations/");

    static {
        SQL_CONTAINER = new PostgreSQLContainer<>("postgres:14");
        SQL_CONTAINER.start();
        start();
    }

    private static void start() {
        try (Connection connection = DriverManager.getConnection(SQL_CONTAINER.getJdbcUrl(), SQL_CONTAINER.getUsername(),
                SQL_CONTAINER.getPassword())) {
            Database db = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection
                    (connection));
            Liquibase liquibase = new Liquibase("master.xml", new DirectoryResourceAccessor(ROOT_DIRECTORY), db);
            liquibase.update(new Contexts(), new LabelExpression());
        } catch (SQLException | LiquibaseException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", SQL_CONTAINER::getPassword);
    }
}
