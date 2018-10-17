package kr.or.dgit.jdbc_pool_study.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDriver;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class DBCPInit {
	private Properties prop;
	private ObjectPool<PoolableConnection> connectionPool;
	private PoolingDriver driver;

	public DBCPInit() {
		loadProperties("db.properties");
		initConnectionPool();
		registerConnectionPool();
	}

	private void initConnectionPool() {
		String jdbcUrl = prop.getProperty("jdbcUrl");
		String userName = prop.getProperty("dbUser");
		String userPwd = prop.getProperty("dbPwd");
		// 실제 커넥션을 생성할 ConnectionFactory를 생성
		ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(jdbcUrl, userName, userPwd);

		// 커넥션 Pool로 사용할 PoolableConnection을 생성
		// DBCP는 커넥션 Pool에 커넥션을 보관할 때 PoolableConnection을 사용
		// PoolableConnection는 내부적으로 실제 커넥션을 담고 있으며, 커넥션 풀 관리에 필요한 기능을 추가로 제공
		// 예를 들어 close()를 실행하면 실제 커넥션을 종료하지 않고 Pool에 커넥션을 반환
		PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, null);
		String validationQuery = prop.getProperty("validationQuery");
		// 커넥션이 유효한지 여부를 검사할 때 사용할 쿼리를 지정
		poolableConnectionFactory.setValidationQuery(validationQuery);

		GenericObjectPoolConfig<PoolableConnection> poolConfig = new GenericObjectPoolConfig<>(); // 커넥션 Pool의 설정 정보 생성
		poolConfig.setTimeBetweenEvictionRunsMillis(1000L * 60L * 5L); // 유휴 커넥션 검사 주기
		poolConfig.setTestWhileIdle(true); // Pool에 보관중인 커넥션이 유효한지 검사여부
		int minIdle = Integer.parseInt(prop.getProperty("minIdle", "5"));
		poolConfig.setMinIdle(minIdle); // 최소 커넥션 수
		int maxTotal = Integer.parseInt(prop.getProperty("maxTotal", "50"));
		poolConfig.setMaxTotal(maxTotal); // 최대 커넥션 수
		connectionPool = new GenericObjectPool<>(poolableConnectionFactory, poolConfig);
		poolableConnectionFactory.setPool(connectionPool); // PoolableConnectionFactory에 커넥션 Pool 연결
	}

	public void registerConnectionPool() {
		try {
			// 커넥션 Pool을 제공하는 JDBC드라이버 등록
			Class.forName(prop.getProperty("PoolingDriver"));
			driver = (PoolingDriver) DriverManager.getDriver(prop.getProperty("PoolingUrl"));
			// 커넥션 Pool 드라이버에 "company" 커넥션 Pool이름으로 커넥션 Pool 등록
			// ==> JDBC URL은 "jdbc:apache:commons:dbcp:company"가 됨.
			driver.registerPool(prop.getProperty("poolName"), connectionPool);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void shutdownDriver() throws Exception {
		PoolingDriver driver = (PoolingDriver) DriverManager.getDriver(prop.getProperty("PoolingUrl"));
		driver.closePool(prop.getProperty("poolName"));
	}

	public void loadProperties(String propPath) {
		prop = new Properties();
		try (InputStream is = ClassLoader.getSystemResourceAsStream(propPath)) {
			prop.loadFromXML(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
