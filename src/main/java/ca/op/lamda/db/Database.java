package ca.op.lamda.db;

import ca.op.lamda.config.Configuration;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public final class Database {

    private Database() {
        throw new AssertionError();
    }

    private static final HikariDataSource dataSource;

    static {
        final Properties props = Configuration.INSTANCE.properties();

        final HikariConfig config = new HikariConfig();
        config.setDriverClassName(props.getProperty("datasource.driverClassName"));
        config.setJdbcUrl(props.getProperty("datasource.url"));
        config.setUsername(props.getProperty("datasource.username"));
        config.setPassword(props.getProperty("datasource.password"));
        config.setPoolName(props.getProperty("datasource.hikari.poolName"));
        config.setMaximumPoolSize(Integer.parseInt(props.getProperty("datasource.hikari.maximumPoolSize")));
        config.setConnectionTimeout(Long.parseLong(props.getProperty("datasource.hikari.connectionTimeout")));
        config.setIdleTimeout(Long.parseLong(props.getProperty("datasource.hikari.idleTimeout")));
        config.setMaxLifetime(Long.parseLong(props.getProperty("datasource.hikari.maxLifetime")));

        dataSource = new HikariDataSource(config);
    }

    public static Connection connection() throws SQLException {
        return dataSource.getConnection();
    }
}