package in.visionIt.bean;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class BookDaoImpl implements BookDao {

	private JdbcTemplate jdbcTemplate;

	public BookDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void saveBook(Integer bid, String bname, Double bprice) {

		String sql = "insert into book_tbl values(?,?,?)";
		jdbcTemplate.update(sql, ps -> {
			ps.setInt(1, bid);
			ps.setString(2, bname);
			ps.setDouble(3, bprice);
		});
	}

	@Override
	public List<Object[]> getAllBooks() {

		String sql = "SELECT book_id, book_name, book_price FROM book_tbl";

		// RowMapper to map each row from ResultSet into Object[]
		RowMapper<Object[]> rowMapper = (rs, rowNum) -> {
			Integer bid = rs.getInt("book_id");
			String bookName = rs.getString("book_name");
			Double bookPrice = rs.getDouble("book_price");
			return new Object[] { bid, bookName, bookPrice };
		};

		// Execute the query and return the list of books as Object[] arrays
		return jdbcTemplate.query(sql, rowMapper);
	}

}
