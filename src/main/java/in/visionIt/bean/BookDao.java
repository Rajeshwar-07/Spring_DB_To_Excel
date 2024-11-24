package in.visionIt.bean;

import java.util.List;

public interface BookDao {

	public void saveBook(Integer bid, String bookName, Double bprice);

	public List<Object[]> getAllBooks();
}
