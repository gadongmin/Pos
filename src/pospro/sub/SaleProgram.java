package pospro.sub;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import pospro.dao.CategoryDAO;
import pospro.dao.OrderDAO;
import pospro.vo.CategoryVO;

public class SaleProgram {
	public void showSalesMenu(Scanner sc) throws SQLException {
		CategoryDAO categoryDAO = new CategoryDAO();
		OrderDAO orderDAO = new OrderDAO();

		while (true) {
			System.out.println("\n매출 ---------------------------------------------------------------");
			System.out.println("      1. 일일                     2. 분야                ");
			System.out.println("--------------------------------------------------------------------");
			System.out.print("[매출 번호를 입력해주세요]        * 0번 상위메뉴\n매출 번호 : ");
			int sub = sc.nextInt();
			sc.nextLine();

			if (sub == 0)
				break;

			switch (sub) {
			case 1:
				System.out.println("\n일일 ..............................................................");
				System.out.println("위치 : 홈 > 매출 > 일일");
				int total = orderDAO.selectTotalSales();
				System.out.println("\n매출액 : " + total + "원");

				break;

			case 2:
				System.out.println("\n분야 ..............................................................");
				System.out.println("위치 : 홈 > 매출 > 분야");
				System.out.println("");
				List<CategoryVO> categories = categoryDAO.getAllCategories();

				for (CategoryVO c : categories) {
					int sales = orderDAO.selectSalesByCategory(c.getId());
					System.out.println(c.getName() + " : " + sales + "원");
				}

				break;

			default:
				System.out.println("잘못된 입력입니다.");
			}
		}
	}
}
