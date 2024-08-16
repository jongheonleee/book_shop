package connect;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class DBConnectTest {
    @Autowired
    DataSource dataSource;

    @Test
    public void springJdbcConnectionTest() throws Exception {

        String username = "root";
        String password = "12341234";

        // 데이터 베이스 연결을 얻는다.
        Connection conn = dataSource.getConnection(username, password);

        System.out.println("conn = " + conn);
        assertTrue(conn != null);
    }
}
