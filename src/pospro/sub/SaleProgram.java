package pospro.sub;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import pospro.dao.CategoryDAO;
import pospro.dao.SaleDAO;
import pospro.vo.CategoryVO;

public class SaleProgram {

	CategoryDAO categoryDAO = new CategoryDAO();
	SaleDAO saleDAO = new SaleDAO();

	public void salesMenu(Scanner sc) throws SQLException {

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
					this.dailySales();
					break;
				
				case 2:
					this.categorySales();
					break;
	
				default:
					System.out.println("잘못된 입력입니다.");
			}
		}
	}

	// 일일 매출 조회
	private void dailySales() {
		System.out.println("\n일일 ..............................................................");
		System.out.println("위치 : 홈 > 매출 > 일일");
		int total = saleDAO.selectTotalSales();
		System.out.println("\n매출액 : " + total + "원");
	}

	// 분야별 매출 조회
	private void categorySales() throws SQLException {
		System.out.println("\n분야 ..............................................................");
		System.out.println("위치 : 홈 > 매출 > 분야");
		System.out.println("");
		List<CategoryVO> categories = categoryDAO.getAllCategories();

		for (CategoryVO c : categories) {
			int sales = saleDAO.selectSalesByCategory(c.getId());
			System.out.println(c.getName() + " : " + sales + "원");
		}
	}
}