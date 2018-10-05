package kr.or.dgit.jdbc_pool_study;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDriver;

import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;


public class PoolingDriverExample {

    public static void main(String[] args) {

        System.out.println("Loading underlying JDBC driver.");
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Done.");

        System.out.println("Setting up driver.");
        try {
            setupDriver(args[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Done.");

        Connection conn = null;
        Statement stmt = null;
        ResultSet rset = null;

        try {
            System.out.println("Creating connection.");
            conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:example");
            System.out.println("Creating statement.");
            stmt = conn.createStatement();
            System.out.println("Executing statement.");
            rset = stmt.executeQuery(args[1]);
            System.out.println("Results:");
            int numcols = rset.getMetaData().getColumnCount();
            while(rset.next()) {
                for(int i=1;i<=numcols;i++) {
                    System.out.print("\t" + rset.getString(i));
                }
                System.out.println("");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rset != null) rset.close(); } catch(Exception e) { }
            try { if (stmt != null) stmt.close(); } catch(Exception e) { }
            try { if (conn != null) conn.close(); } catch(Exception e) { }
        }

        // Display some pool statistics
        try {
            printDriverStats();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // closes the pool
        try {
            shutdownDriver();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setupDriver(String connectURI) throws Exception {

        ConnectionFactory connectionFactory =
            new DriverManagerConnectionFactory(connectURI,null);

        PoolableConnectionFactory poolableConnectionFactory =
            new PoolableConnectionFactory(connectionFactory, null);

        ObjectPool<PoolableConnection> connectionPool =
            new GenericObjectPool<>(poolableConnectionFactory);
        
        poolableConnectionFactory.setPool(connectionPool);

        Class.forName("org.apache.commons.dbcp2.PoolingDriver");
        PoolingDriver driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");

        driver.registerPool("example",connectionPool);

    }

    public static void printDriverStats() throws Exception {
        PoolingDriver driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
        ObjectPool<? extends Connection> connectionPool = driver.getConnectionPool("example");

        System.out.println("NumActive: " + connectionPool.getNumActive());
        System.out.println("NumIdle: " + connectionPool.getNumIdle());
    }

    public static void shutdownDriver() throws Exception {
        PoolingDriver driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
        driver.closePool("example");
    }
}