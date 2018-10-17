package kr.or.dgit.jdbc_pool_dbcp.domain;

public class Department {
	private String deptNo;
	private String deptName;
	private int floor;

	public Department(String deptNo, String deptName, int floor) {
		super();
		this.deptNo = deptNo;
		this.deptName = deptName;
		this.floor = floor;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	@Override
	public String toString() {
		return "Department [deptNo=" + deptNo + ", deptName=" + deptName + ", floor=" + floor + "]";
	}

	
}
