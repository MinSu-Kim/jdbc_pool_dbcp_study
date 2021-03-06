package kr.or.dgit.jdbc_pool_dbcp.service;

import java.util.List;

import kr.or.dgit.jdbc_pool_dbcp.domain.Title;
import kr.or.dgit.jdbc_pool_dbcp.persistence.TitleDaoImpl;

public class TitleService {

	public List<Title> findTitleByAll() {
		return TitleDaoImpl.getInstance().selectTitleByAll();
	}
	
	public Title findTitleByCode(Title title) {
		return TitleDaoImpl.getInstance().selectTitleByCode(title);
	}
	
	public int registTitle(Title title) {
		return TitleDaoImpl.getInstance().insertTitle(title);
	}
	
	public int unRegisterTitle(String code) {
		return TitleDaoImpl.getInstance().deleteTitle(code);
	}
}
