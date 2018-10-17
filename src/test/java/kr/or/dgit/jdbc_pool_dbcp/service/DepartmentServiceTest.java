package kr.or.dgit.jdbc_pool_dbcp.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import kr.or.dgit.jdbc_pool_dbcp.domain.Department;
import kr.or.dgit.jdbc_pool_dbcp.jdbc.DBCPInit;
import kr.or.dgit.jdbc_pool_dbcp.service.DepartmentService;

public class DepartmentServiceTest {
	static final Logger log = LogManager.getLogger();
	
	static DBCPInit dbcp;
	static DepartmentService service;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dbcp = new DBCPInit();
		service = new DepartmentService();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		service = null;
		dbcp.shutdownDriver();
	}

	@Test
	public void testDepartmentByAll() throws SQLException {
       List<Department> list = service.findDepartmentByAll(); 
       Assert.assertNotNull(list);
       for(Department dept : list) {
    	   log.trace(dept);
       }
       
	}

}
