package kr.or.dgit.jdbc_pool_dbcp.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.or.dgit.jdbc_pool_dbcp.domain.Title;
import kr.or.dgit.jdbc_pool_dbcp.jdbc.ConnectionProvider;
import kr.or.dgit.jdbc_pool_dbcp.jdbc.JdbcUtil;

public class TitleDaoImpl implements TitleDao {
	private static final TitleDaoImpl instance = new TitleDaoImpl();

	public static TitleDaoImpl getInstance() {
		return instance;
	}

	private TitleDaoImpl() {
	}

	@Override
	public List<Title> selectTitleByAll() {
		String sql = "select code, name from title";
		List<Title> list = new ArrayList<>();
		try (Connection con = ConnectionProvider.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					list.add(getTitle(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Title selectTitleByCode(Title title) {
		String sql = "select code, name from title where code=?";
		Title res = null;
		try (Connection con = ConnectionProvider.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, title.getCode());
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					res = getTitle(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	private Title getTitle(ResultSet rs) throws SQLException {
		return new Title(rs.getString(1), rs.getString(2));
	}

	@Override
	public int insertTitle(Title title) {
		String sql = "insert into title values(?, ?)";

		int res = -1;
		Connection con = null;
		try {
			con = ConnectionProvider.getConnection();
			try (PreparedStatement pstmt = con.prepareStatement(sql)) {
				con.setAutoCommit(false);
				pstmt.setString(1, title.getCode());
				pstmt.setString(2, title.getName());
				res = pstmt.executeUpdate();
				con.commit();
				con.setAutoCommit(true);
			} catch (SQLException e) {
				JdbcUtil.rollback(con);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		} finally {
			JdbcUtil.close(con);
		}
		return res;
	}

	@Override
	public int deleteTitle(String code) {
		String sql = "delete from title where code = ?";
		int res = -1;
		try (Connection con = ConnectionProvider.getConnection(); 
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, code);
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

}
