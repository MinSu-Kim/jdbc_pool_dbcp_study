package kr.or.dgit.jdbc_pool_study.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.or.dgit.jdbc_pool_study.domain.Department;
import kr.or.dgit.jdbc_pool_study.jdbc.ConnectionProvider;

public class DepartmentDaoImpl implements DepartmentDao {
	private static final DepartmentDaoImpl instance = new DepartmentDaoImpl();

	public static DepartmentDaoImpl getInstance() {
		return instance;
	}

	private DepartmentDaoImpl() {
	}

	@Override
	public List<Department> selectDepartmentByAll() {
		String sql = "select deptno, deptname, floor from department";
		List<Department> list = new ArrayList<>();
		try (Connection con = ConnectionProvider.getConnection(); 
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				list.add(getDepartment(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	private Department getDepartment(ResultSet rs) throws SQLException {
		return new Department(rs.getString(1), rs.getString(2), rs.getInt(3));
	}

}
