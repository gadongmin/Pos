package Pos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//메뉴 데이터베이스 접근을 담당하는 클래스
public class MenuDAO {
	// 메뉴 등록
	public static void addMenu(int categoryId, String name, int price) throws SQLException {
		String sql = "INSERT INTO menu (category_id, name, price)"
				   + "VALUES (?, ?, ?)";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, categoryId);	// 카테고리 ID 설정
			pstmt.setString(2, name);		// 메뉴 이름 설정
			pstmt.setInt(3, price);			// 가격 설정
			pstmt.executeUpdate();			// 데이터베이스에 등록
		}
	}

	// 메뉴 조회 (전체)
	public static List<MenuVO> getAllMenus() throws SQLException {
		List<MenuVO> list = new ArrayList<>();
		String sql = "SELECT *"
				   + "FROM menu";
		try (Connection conn = DBConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				// ResultSet에서 메뉴 정보를 읽어 MenuVO 객체로 변환 후 리스트에 추가
				list.add(new MenuVO(rs.getInt("id")
								   ,rs.getInt("category_id")
							       ,rs.getString("name")
							       ,rs.getInt("price")
							       )
						);
			}
		}
		return list;
	}

	// 메뉴 조회 (카테고리별)
	public static List<MenuVO> getMenusByCategory(int categoryId) throws SQLException {
		List<MenuVO> list = new ArrayList<>();
		String sql = "SELECT *"
				   + "FROM menu "
				   + "WHERE category_id = ?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, categoryId);			// 카테고리 ID 설정
			ResultSet rs = pstmt.executeQuery();	// 쿼리 실행
			while (rs.next()) {
				// ResultSet에서 메뉴 정보를 읽어 MenuVO 객체로 변환 후 리스트에 추가
				list.add(new MenuVO(rs.getInt("id")
						           ,rs.getInt("category_id")
						           ,rs.getString("name")
						           ,rs.getInt("price")
						           )
						);
			}
		}
		return list;
	}

	// 메뉴 조회 (단일)
	public static MenuVO getMenu(int id) throws SQLException {
		String sql = "SELECT *"
				   + "FROM menu"
				   + "WHERE id = ?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);					// 메뉴 ID 설정
			ResultSet rs = pstmt.executeQuery();	// 쿼리 실행
			if (rs.next()) {
				 // ResultSet에서 메뉴 정보를 읽어 MenuVO 객체로 변환
				return new MenuVO(rs.getInt("id")
								 ,rs.getInt("category_id")
								 ,rs.getString("name")
								 ,rs.getInt("price")
								 );
			}
		}
		// 해당 ID의 메뉴가 없으면 null 반환
		return null;
	}

	// 메뉴 수정
	public static void updateMenu(int id, String name, int price) throws SQLException {
		String sql = "UPDATE menu"
				   + "SET name=?, price=?"
				   + "WHERE id=?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, name);	// 새로운 이름 설정
			pstmt.setInt(2, price);		// 새로운 가격 설정
			pstmt.setInt(3, id);		// 수정할 메뉴 ID 설정
			pstmt.executeUpdate();		// 데이터베이스에 수정 반영
		}
	}

	// 메뉴 삭제
	public static void deleteMenu(int id) throws SQLException {
		String sql = "DELETE"
				   + "FROM menu"
				   + "WHERE id = ?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);		// 삭제할 메뉴 ID 설정
			pstmt.executeUpdate();		// 데이터베이스에서 삭제
		}
	}
}
