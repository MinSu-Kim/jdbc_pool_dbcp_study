package kr.or.dgit.jdbc_pool_study.persistence;

import java.util.List;

import kr.or.dgit.jdbc_pool_study.domain.Department;

public interface DepartmentDao {
	List<Department> selectDepartmentByAll();
}
