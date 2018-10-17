package kr.or.dgit.jdbc_pool_dbcp.domain;

public class Title {
	private String code;
	private String name;

	public Title() {
		// TODO Auto-generated constructor stub
	}

	public Title(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Title [code=" + code + ", name=" + name + "]";
	}

}
