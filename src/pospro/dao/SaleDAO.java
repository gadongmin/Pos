package pospro.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SaleDAO {
	// 필드
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/web_db";
	private String id = "web";
	private String pw = "web";

	// DB 연결 메소드
	private void connect() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("error: DB 연결 실패 - " + e.getMessage());
		}
	}

	// 자원 정리 메소드
	private void close() {
		try {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();

		} catch (SQLException e) {
			System.out.println("error: 자원 해제 실패 - " + e.getMessage());
		}
	}

	// 총 매출 조회 : 결제된 모든 주문의 매출 합계를 조회
	public int selectTotalSales() {

		int total = 0; // 결과값 초기화

		this.connect(); // DB 연결

		try {
			// SQL 쿼리 작성: orders 테이블과 menu 테이블을 조인하여 결제된 주문의 매출 합계 조회
			String query = " SELECT SUM(o.quantity * m.price) AS total " + " FROM orders o " + " JOIN menu m "
					+ " ON o.menu_id = m.menu_id " + " WHERE o.ispaid = TRUE ";
			pstmt = conn.prepareStatement(query);

			// 쿼리 실행 및 결과 받기
			rs = pstmt.executeQuery();

			// 결과가 있으면 매출 합계 저장
			if (rs.next()) {
				total = rs.getInt("total");
			}

		} catch (SQLException e) {
			System.out.println("error: " + e.getMessage());

		} finally {
			this.close();
		}

		return total; // 매출 합계 반환
	}

	// 카테고리별 매출 조회 : 특정 카테고리의 결제된 주문 매출 합계를 조회
	public int selectSalesByCategory(int categoryId) {

		int total = 0; // 결과값 초기화

		this.connect(); // DB 연결

		try {
			// SQL 쿼리 작성: orders 테이블과 menu 테이블을 조인하여 특정 카테고리의 결제된 주문 매출 합계 조회
			String query = " SELECT SUM(o.quantity * m.price) AS total " + " FROM orders o " + " JOIN menu m "
					+ " ON o.menu_id = m.menu_id " + " WHERE o.ispaid = TRUE " + " AND m.category_id = ? ";
			pstmt = conn.prepareStatement(query);

			// 파라미터 입력
			pstmt.setInt(1, categoryId);

			// 쿼리 실행 및 결과 받기
			rs = pstmt.executeQuery();

			// 결과가 있으면 매출 합계 저장
			if (rs.next()) {
				total = rs.getInt("total");
			}

		} catch (SQLException e) {
			System.out.println("error: " + e.getMessage());

		} finally {
			this.close();
		}

		return total;
	}
}
