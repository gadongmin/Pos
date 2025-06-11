package Pos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//카테고리 데이터베이스 접근을 담당하는 클래스
public class CategoryDAO {
	// 카테고리 등록
	public static void addCategory(String emoji, String name, String description) throws SQLException {
		// 이모티콘, 이름, 설명을 이용해 새 카테고리를 추가하는 쿼리
		String sql = "INSERT INTO category (emoji, name, description) "
				   + "VALUES (?, ?, ?)";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			// 파라미터 바인딩
			pstmt.setString(1, emoji);
			pstmt.setString(2, name);
			pstmt.setString(3, description);
			// 쿼리 실행
			pstmt.executeUpdate();
		}
	}

	// 카테고리 조회 (전체)
	public static List<CategoryVO> getAllCategories() throws SQLException {
		List<CategoryVO> list = new ArrayList<>();
		// 모든 카테고리 정보를 가져오는 쿼리
		String sql =  "SELECT *"
				   +  "FROM category";
		try (Connection conn = DBConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			// 결과가 있을 때마다 한 행씩 처리
			while (rs.next()) {
				// 카테고리 정보를 CategoryVO 객체로 만들어 리스트에 추가
				list.add(new CategoryVO(rs.getInt("id")
										,rs.getString("emoji")
										,rs.getString("name")
										,rs.getString("description")
										)
						);
			}
		}
		// 모든 카테고리 정보가 담긴 리스트 반환
		return list;
	}

	// 카테고리 조회 (단일)
	public static CategoryVO getCategory(int id) throws SQLException {
		// 카테고리 ID로 특정 카테고리 정보를 가져오는 쿼리
		String sql = "SELECT * "
				   + "FROM category "
				   + "WHERE id = ?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			// 파라미터 바인딩
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			// 결과가 있으면 CategoryVO 객체로 반환
			if (rs.next()) {
				return new CategoryVO(rs.getInt("id")
									 ,rs.getString("emoji")
									 ,rs.getString("name")
									 ,rs.getString("description")
									 );
			}
		}
		// 해당 ID의 카테고리가 없으면 null 반환
		return null;
	}

	// 카테고리 수정
	public static void updateCategory(int id, String emoji, String name, String description) throws SQLException {
		// 카테고리 ID로 이모티콘, 이름, 설명을 수정하는 쿼리
		String sql = "UPDATE category "
				   + "SET emoji=?, name=?, description=? "
				   + "WHERE id=?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			// 파라미터 바인딩
			pstmt.setString(1, emoji);		// 이모티콘
			pstmt.setString(2, name);		// 이름
			pstmt.setString(3, description);// 설명
			pstmt.setInt(4, id);			// 카테고리 ID
			// 쿼리 실행
			pstmt.executeUpdate();
		}
	}

	// 카테고리 삭제
	public static void deleteCategory(int id) throws SQLException {
		// 카테고리 ID로 카테고리를 삭제하는 쿼리
        // ※ 주의: 실제 코드에서는 공백이 누락되어 오류가 날 수 있으니 공백 추가 필요
		String sql = "DELETE "
			       + "FROM category"
			       + "WHERE id = ?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			// 파라미터 바인딩
			pstmt.setInt(1, id);
			// 쿼리 실행
			pstmt.executeUpdate();
		}
	}
}