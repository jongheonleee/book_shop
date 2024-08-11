package order.dao.fake;

import com.fastcampus.ch4.dto.order.temp.TempBookDto;

public interface TempBookService {
    TempBookDto getBookByIsbn (String isbn);
}
