package kr.or.dgit.jdbc_pool_study.service;

import java.util.List;

import kr.or.dgit.jdbc_pool_study.domain.Department;
import kr.or.dgit.jdbc_pool_study.persistence.DepartmentDaoImpl;

public class DepartmentService {

	public List<Department> findDepartmentByAll() {
		return DepartmentDaoImpl.getInstance().selectDepartmentByAll();
	}
}
