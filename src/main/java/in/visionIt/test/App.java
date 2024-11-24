package in.visionIt.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import in.visionIt.bean.BookService;

public class App {

	public static void main(String[] args) throws Exception {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("Beans.xml");

		BookService service = ctx.getBean(BookService.class);
//		service.storeBookData();
		service.generateExcel();
	}
}
