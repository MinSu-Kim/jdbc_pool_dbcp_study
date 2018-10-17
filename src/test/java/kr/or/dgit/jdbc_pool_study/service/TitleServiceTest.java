package kr.or.dgit.jdbc_pool_study.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import kr.or.dgit.jdbc_pool_study.domain.Title;
import kr.or.dgit.jdbc_pool_study.jdbc.DBCPInit;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TitleServiceTest {
	static final Logger log = LogManager.getLogger();
	
	static DBCPInit dbcp;
	static TitleService service;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dbcp = new DBCPInit();
		service = new TitleService();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		service = null;
		dbcp.shutdownDriver();
	}

	@Test
	public void testTitleByAll() throws SQLException {
		List<Title> list = service.findTitleByAll();
		Assert.assertNotNull(list);
		for (Title t : list) {
			log.trace(t);
		}

	}

	@Test
	public void testTitleByCode() throws SQLException {
		Title searchTitle = new Title();
		searchTitle.setCode("T001");
		Title title = service.findTitleByCode(searchTitle);
		log.trace(title);
		Assert.assertNotNull(title);
	}
	
	@Test
	public void testRegisterTitle() throws SQLException {
		Title insertTitle = new Title("T005", "인턴");
		int res = service.registTitle(insertTitle);
		log.trace("result : " + res);
		Assert.assertEquals(1, res);
	}
	
	@Test
	public void testUnRegisterTitle() throws SQLException {
		int res = service.unRegisterTitle("T005");
		log.trace("result : " + res);
		Assert.assertEquals(1, res);
	}
}
