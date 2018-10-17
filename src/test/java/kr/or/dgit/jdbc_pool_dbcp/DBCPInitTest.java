package kr.or.dgit.jdbc_pool_dbcp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.dbcp2.PoolingDriver;
import org.apache.commons.pool2.ObjectPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import kr.or.dgit.jdbc_pool_dbcp.jdbc.ConnectionProvider;
import kr.or.dgit.jdbc_pool_dbcp.jdbc.DBCPInit;

public class DBCPInitTest {
	static final Logger log = LogManager.getLogger();
	static DBCPInit dbcp;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		log.trace("setUpBeforeClass()");
		dbcp = new DBCPInit();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		log.trace("tearDownAfterClass()");
		dbcp.shutdownDriver();
	}

	@Before
	public void setUp() throws Exception {
		log.trace("setUp()");
		printDriverStats();
	}

	@After
	public void tearDown() throws Exception {
		log.trace("tearDown()");
		printDriverStats();
	}

	@Test
	public void testPool() throws SQLException {
		log.trace("testPool()");
		Connection[] connections = new Connection[10];
		for (int i = 0; i < 10; i++) {
			connections[i] = ConnectionProvider.getConnection();
			Assert.assertNotNull(connections[i]);
			printDriverStats();
		}

		for (int i = 0; i < 10; i++) {
			connections[i].close();
			printDriverStats();
		}

	}

	public static void printDriverStats() {
		PoolingDriver driver;
		try {
			driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
			ObjectPool<? extends Connection> connectionPool = driver.getConnectionPool("erp");

			log.trace(String.format("NumActive: %d", connectionPool.getNumActive()));
			log.trace(String.format("NumIdle: %d", connectionPool.getNumIdle()));
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
