package Pos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//주문/결제 데이터베이스 접근을 담당하는 클래스
public class OrderDAO {
	// 주문 등록
	public static void addOrder(int menuId, int quantity, int tableNo) throws SQLException {
		// 메뉴번호, 수량, 테이블 번호를 이용해 새 주문을 추가하는 쿼리
		String sql = "INSERT INTO orders (menu_id, quantity, table_no)"
				   + "VALUES (?, ?, ?)";
		try (Connection conn = DBConnection.getConnection(); 
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			// 파라미터 바인딩: 각 물음표에 값을 순서대로 넣음
			pstmt.setInt(1, menuId);	// 메뉴번호
			pstmt.setInt(2, quantity);	// 수량
			pstmt.setInt(3, tableNo);	// 테이블 번호
			pstmt.executeUpdate();		// 쿼리 실행
		}
	}

	// 주문 조회 (전체)
	public static List<OrderVO> getAllOrders() throws SQLException {
		List<OrderVO> list = new ArrayList<>();
		// 모든 주문정보를 불러오는 쿼리
		String sql = "SELECT *"
				   + "FROM orders";	
		try (Connection conn = DBConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			// 결과가 있을 때마다 한 행씩 처리
			while (rs.next()) {
				// 주문 정보를 orderVO 객체로 만들어서 리스트에 추가
				list.add(new OrderVO(rs.getInt("id")
									,rs.getInt("menu_id")
									,rs.getInt("quantity")
									,rs.getInt("table_no")
									,rs.getBoolean("is_paid")
									)
						);
			}
		}
		// 모든 주문 정보가 담긴 리스트 반환
		return list;	
	}

	// 주문 조회 (테이블별)
	public static List<OrderVO> getOrdersByTable(int tableNo) throws SQLException {
		List<OrderVO> list = new ArrayList<>();
		// 특정 테이블 번호에 해당하는 모든 주문을 가져오는 쿼리
		String sql = "SELECT *"
				   + "FROM orders "
				   + "WHERE table_no = ?"; 	// 테이블 번호로 필터링
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, tableNo);	// 테이블 바인딩
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(new OrderVO(rs.getInt("id")
									,rs.getInt("menu_id")
									,rs.getInt("quantity")
									,rs.getInt("table_no")
									,rs.getBoolean("is_paid")
									)
						);
			}
		}
		// 해당 테이블의 주문 리스트 반환
		return list;
	}

	// 주문 조회 (결제 여부별)
	public static List<OrderVO> getOrdersByPaid(boolean isPaid) throws SQLException {
		List<OrderVO> list = new ArrayList<>();
		// 결제 여부(is_paid)에 따라 주문을 가져오는 쿼리
		String sql = "SELECT *"
				   + "FROM orders"
				   + "WHERE is_paid = ?";	// 결제 여부로 필터링
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setBoolean(1, isPaid);	// 결제여부 바인딩
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(new OrderVO(rs.getInt("id")
									,rs.getInt("menu_id")
									,rs.getInt("quantity")
									,rs.getInt("table_no")
									,rs.getBoolean("is_paid")
									)
						);
			}
		}
		// 결제 여부에 따른 주문 리스트 반환
		return list;
	}

	// 주문 조회 (단일)
	public static OrderVO getOrder(int id) throws SQLException {
		// 주문번호로 특정 주문을 가져오는 쿼리
		String sql = "SELECT *"
				   + "FROM orders"	
				   + "WHERE id = ?";	// 주문번호로 필터링
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);	// 주문번호 바인딩
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return new OrderVO(rs.getInt("id")
								  ,rs.getInt("menu_id")
								  ,rs.getInt("quantity")
								  ,rs.getInt("table_no")
								  ,	rs.getBoolean("is_paid")
								  );
			}
		}
		return null;	// 주문이 없으면 null 반환
	}

	// 주문 수정 (수량, 테이블 등)
	public static void updateOrder(int id, int quantity, int tableNo) throws SQLException {
		// 주문번호로 수량과 테이블번호를 수정하는 쿼리
		String sql = "UPDATE orders"
				   + "SET quantity=?, table_no=?"
				   + "WHERE id=?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			// 테이블번호 바인딩
			pstmt.setInt(1, quantity);	// 수량
			pstmt.setInt(2, tableNo);	// 테이블번호
			pstmt.setInt(3, id);		// 주문번호
			// 쿼리 실행 및 데이터베이스 반영
			pstmt.executeUpdate();
		}
	}

	// 주문 삭제
	public static void deleteOrder(int id) throws SQLException {
		 // 주문번호로 주문을 삭제하는 쿼리
		String sql = "DELETE"
				   + "FROM orders"
				   + "WHERE id = ?";		// 주문번호로 주문을 삭제하는 쿼리
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			// 주문번호 바인딩
			pstmt.setInt(1, id);
			// 쿼리 실행 및 데이터베이스 반영
			pstmt.executeUpdate();
		}
	}

	// 주문 결제 (매출 처리)
	public static void payOrder(int orderId) throws SQLException {
		// 주문번호로 주문을 결제(삭제)하는 쿼리
		String sql = "DELETE"
				   + "FROM orders"
				   + "WHERE id = ?";		// 주문번호로 주문을 결제(삭제)하는 쿼리
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			// 주문번호 바인딩
			pstmt.setInt(1, orderId);
			// 쿼리 실행 및 데이터베이스 반영
			pstmt.executeUpdate();
		}
	}

	// 테이블 전체 결제(주문 삭제 방식)
	public static void payTable(int tableNo) throws SQLException {
		// 테이블번호로 해당 테이블의 모든 주문을 결제(삭제)하는 쿼리
		String sql = "DELETE"
				   + "FROM orders"
				   + "WHERE table_no = ?";	// 테이블번호로 해당 테이블의 모든 주문을 결제(삭제)하는 쿼리
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			// 테이블번호 바인딩
			pstmt.setInt(1, tableNo);
			// 쿼리 실행 및 데이터베이스 반영
			pstmt.executeUpdate();
		}
	}

	// 매출 조회 (결제된 주문만)
	public static int getTotalSales() throws SQLException {
		// 결제된 주문의 총 매출액을 계산하는 쿼리
		String sql = "SELECT SUM(quantity * (SELECT price FROM menu WHERE id = menu_id)) as total"
				   + "FROM orders"
				   + "WHERE is_paid = TRUE";
		try (Connection conn = DBConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			if (rs.next()) {
				// 총 매출액 반환
				return rs.getInt("total");
			}
		}
		return 0;	// 매출이 없으면 0 반환
	}

	// 카테고리별 매출 조회
	public static int getSalesByCategory(int categoryId) throws SQLException {
		// 특정 카테고리의 결제된 주문의 총 매출액을 계산하는 쿼리
		String sql = "SELECT SUM(o.quantity * m.price) as total"
				   + "FROM orders o JOIN menu m ON o.menu_id = m.id"
				   + "WHERE o.is_paid = TRUE AND m.category_id = ?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			// 카테고리번호 바인딩
			pstmt.setInt(1, categoryId);
			// 쿼리 실행 및 데이터베이스 반영
			ResultSet rs = pstmt.executeQuery();	
			if (rs.next()) {
				return rs.getInt("total");	// 해당 카테고리의 매출액 반환
			}
		}
		// 매출이 없으면 0 반환
		return 0;	
	}
}