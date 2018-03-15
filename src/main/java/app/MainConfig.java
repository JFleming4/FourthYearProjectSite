package app;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class MainConfig {
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")

    public BasicDataSource dataSource() throws URISyntaxException
    {
        String username;
        String password;
        String dbUrl;

        if(System.getenv("SYSC_DATABASE_URL") != null)
        {
           dbUrl = System.getenv("SYSC_DATABASE_URL");
           username = System.getenv("SYSC_DATABASE_USERNAME");
           password = System.getenv("SYSC_DATABASE_PASSWORD");
        }
        else {
            URI dbUri = new URI(System.getenv("DATABASE_URL"));
            username = dbUri.getUserInfo().split(":")[0];
            password = dbUri.getUserInfo().split(":")[1];
            dbUrl = "jdbc:postgress://" + dbUri.getHost() + dbUri.getPath() + ":" + dbUri.getPort() + dbUri.getPath();
        }

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(dbUrl);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);

        return basicDataSource;
    }

}
