package pospro.sub;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import pospro.dao.MenuDAO;
import pospro.dao.OrderDAO;
import pospro.vo.MenuVO;
import pospro.vo.OrderVO;

public class OrderProgram {

	OrderDAO orderDAO = new OrderDAO();
	MenuDAO menuDAO = new MenuDAO();

	public void orderPaymentMenu(Scanner sc) throws SQLException {

		while (true) { // 주문/결제 메뉴 출력
			System.out.println("\n주문 / 결제 --------------------------------------------------------");
			System.out.println("      1. 등록                   2. 삭제                   3. 결제");
			System.out.println("--------------------------------------------------------------------");

			// 현재 주문 현황 출력
			printOrderList();

			System.out.print("\n[주문 / 결제 번호를 입력해주세요]        * 0번 상위메뉴\n주문 / 결제 번호 : ");
			int sub = sc.nextInt();

			if (sub == 0)
				break;
			sc.nextLine();

			switch (sub) {
				case 1:
					registerOrder(sc);
					break;
				case 2:
					deleteOrder(sc);
					break;
				case 3:
					paymentMenu(sc);
					break;
				default:
					System.out.println("잘못된 입력입니다.");
			}
		}
	}

	// 현재 주문 현황 출력
	private void printOrderList() throws SQLException {
		System.out.println("<주문현황>");
		List<OrderVO> orders = orderDAO.selectAllOrders();

		for (OrderVO o : orders) {
			System.out.println(o.getId() + " - 메뉴번호(" + o.getMenuName() + ") / 메뉴수량(" + o.getQuantity() + ") / 테이블번호("
					+ o.getTableNo() + ")");
		}
	}

	// 주문 등록
	private void registerOrder(Scanner sc) throws SQLException {
		System.out.println("\n등록 ..............................................................");
		System.out.println("위치 : 홈 > 주문 / 결제 > 등록");

		// 현재 등록된 메뉴 목록 출력
		printMenuList();

		System.out.print("1. 메뉴번호 : ");
		int menuId = sc.nextInt();
		System.out.print("2. 메뉴수량 : ");
		int quantity = sc.nextInt();
		System.out.print("3. 테이블번호 : ");
		int tableNo = sc.nextInt();
		sc.nextLine();

		orderDAO.addOrder(menuId, quantity, tableNo);
		System.out.println("<주문되었습니다.>");
	}

	// 메뉴 목록 출력
	private void printMenuList() throws SQLException {
		List<MenuVO> menus = menuDAO.getAllMenus();
		System.out.println("\n<메뉴>");
		for (MenuVO m : menus) {
			System.out.println(m.getId() + " - " + m.getName() + " (" + m.getPrice() + "원)");
		}
		System.out.println("");
	}

	// 주문 삭제
	private void deleteOrder(Scanner sc) throws SQLException {
		System.out.println("\n삭제 ..............................................................");
		System.out.println("위치 : 홈 > 주문 / 결제 > 삭제");
		System.out.println("");

		System.out.print("1. 주문번호 : ");
		int delId = sc.nextInt();
		sc.nextLine();

		orderDAO.deleteOrder(delId);
		System.out.println("<주문 취소되었습니다.>");
	}

	// 결제 메뉴
	private void paymentMenu(Scanner sc) throws SQLException {
		System.out.println("\n결제 ................................................................");
		System.out.println("      1. 전체결제                    2. 개별결제");
		System.out.println(".....................................................................");
		System.out.print("[결제 번호를 입력해주세요]        * 0번 상위메뉴\n위치 : 홈 > 주문 / 결제 > 결제\n결제 번호 : ");
		int pay = sc.nextInt();

		if (pay == 0)
			return;

		if (pay == 1) {
			payByTable(sc);
		} else if (pay == 2) {
			payByOrderId(sc);
		} else {
			System.out.println("<잘못된 입력입니다.>");
		}
	}

	// 테이블별 결제
	private void payByTable(Scanner sc) throws SQLException {
		System.out.print("1. 테이블번호 : ");
		int table = sc.nextInt();
		int result = orderDAO.payTable(table);

		if (result > 0) {
			System.out.println("<결제되었습니다.>");
		} else {
			System.out.println("<결제할 주문이 존재하지 않습니다.>");
		}
	}

	// 주문번호별 결제
	private void payByOrderId(Scanner sc) throws SQLException {
		System.out.print("1. 주문번호 : ");
		int orderId = sc.nextInt();
		int result = orderDAO.payOrder(orderId);

		if (result > 0) {
			System.out.println("<결제되었습니다.>");
		} else {
			System.out.println("<존재하지 않는 주문번호입니다.>");
		}
	}
}

