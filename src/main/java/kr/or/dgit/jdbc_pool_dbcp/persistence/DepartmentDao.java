package kr.or.dgit.jdbc_pool_dbcp.persistence;

import java.util.List;

import kr.or.dgit.jdbc_pool_dbcp.domain.Department;

public interface DepartmentDao {
	List<Department> selectDepartmentByAll();
}
