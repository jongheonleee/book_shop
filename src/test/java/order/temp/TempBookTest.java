package order.temp;


import com.fastcampus.ch4.dao.order.temp.TempBookDao;
import com.fastcampus.ch4.dto.order.temp.TempBookDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class TempBookTest {
    @Autowired
    TempBookDao tempBookDao;

    @Test
    public void selectTest () {
        String TEST_ISBN = "9788994492032";
        String TEST_TITLE = "자바의 정석";
        String FAIL_ISBN = "0000000000000";

        TempBookDto bookDto = tempBookDao.selectByIsbn(TEST_ISBN);
        assertNotNull(bookDto);
        assertEquals(TEST_TITLE, bookDto.getBook_title());

        TempBookDto failDto = tempBookDao.selectByIsbn(FAIL_ISBN);
        assertNull(failDto);
    }
}
