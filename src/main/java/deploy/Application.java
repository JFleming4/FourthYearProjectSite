package deploy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;


@Component
@SpringBootApplication
public class Application {
//    @Autowired
//    private DataSource dataSource;

    public static void main(String[] args){
        SpringApplication.run(Application.class);
    }

    // Example of how to use the db
//    @PostConstruct
//    public void myRealMainMethod() throws SQLException {
//        System.out.println("----------------------------------");
//        System.out.println("----------------------------------");
//        System.out.println("Starting main");
//        System.out.println("----------------------------------");
//        System.out.println("----------------------------------");
//        Statement stmt = dataSource.getConnection().createStatement();
//        stmt.executeUpdate("DROP TABLE IF EXISTS ticks");
//        stmt.executeUpdate("CREATE TABLE ticks (tick timestamp)");
//        stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
//        ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");
//        while (rs.next()) {
//            System.out.println("Read from DB: " + rs.getTimestamp("tick"));
//        }
//    }
}