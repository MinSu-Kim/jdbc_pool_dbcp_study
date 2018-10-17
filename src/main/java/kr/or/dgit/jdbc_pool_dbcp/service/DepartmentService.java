package kr.or.dgit.jdbc_pool_dbcp.service;

import java.util.List;

import kr.or.dgit.jdbc_pool_dbcp.domain.Department;
import kr.or.dgit.jdbc_pool_dbcp.persistence.DepartmentDaoImpl;

public class DepartmentService {

	public List<Department> findDepartmentByAll() {
		return DepartmentDaoImpl.getInstance().selectDepartmentByAll();
	}
}
