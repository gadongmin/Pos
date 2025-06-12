package pospro.sub;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import pospro.dao.CategoryDAO;
import pospro.dao.MenuDAO;
import pospro.vo.CategoryVO;
import pospro.vo.MenuVO;

public class MenuProgram {
	public void menuManagementMenu(Scanner sc) throws SQLException {
		MenuDAO menuDAO = new MenuDAO();
		CategoryDAO categoryDAO = new CategoryDAO();

		while (true) {
			System.out.println("\n메뉴 ---------------------------------------------------------------");
			System.out.println("      1. 등록                   2. 수정                   3. 삭제");
			System.out.println("--------------------------------------------------------------------");
			System.out.println("<메뉴현황>");
			List<MenuVO> menus = menuDAO.getAllMenus();

			for (MenuVO m : menus) {
				CategoryVO c = categoryDAO.getCategory(m.getCategoryId());
				System.out.println(m.getId() + " - 카테고리(" + m.getCategoryId() + "), 이름(" + m.getName() + "), 가격("
						+ m.getPrice() + ")");
			}

			System.out.print("\n[메뉴 번호를 입력해주세요]        * 0번 상위메뉴\n메뉴 번호 : ");

			int sub;

			try {
				sub = sc.nextInt();
				sc.nextLine();

			} catch (java.util.InputMismatchException e) {
				sc.nextLine(); // 버퍼 정리
				System.out.println("잘못된 입력입니다. 다시 입력해주세요.");

				continue;
			}

			if (sub == 0)
				break;

			switch (sub) {
			case 1:
				System.out.println("\n등록 ..............................................................");
				System.out.println("위치 : 홈 > 메뉴 > 등록");
				System.out.println("\n<카테고리 현황>");
				List<CategoryVO> categories = categoryDAO.getAllCategories();

				for (int i = 0; i < categories.size(); i++) {
					System.out.println(categories.get(i).getId() + " - 이모티콘(" + categories.get(i).getEmoji() + "), 이름("
							+ categories.get(i).getName() + "), 설명(" + categories.get(i).getDescription() + ")");
				}

				int catId;
				System.out.print("\n1. 카테고리 번호 : ");
				try {
					catId = sc.nextInt();
					sc.nextLine();

				} catch (java.util.InputMismatchException e) {
					sc.nextLine();
					System.out.println("잘못된 입력입니다. 다시 입력해주세요.");

					continue;
				}

				System.out.print("2. 이 름 : ");
				String name = sc.nextLine();

				int price;
				System.out.print("3. 가 격 : ");
				try {
					price = sc.nextInt();
					sc.nextLine();

				} catch (java.util.InputMismatchException e) {
					sc.nextLine();
					System.out.println("잘못된 입력입니다. 다시 입력해주세요.");

					continue;
				}

				menuDAO.addMenu(catId, name, price);

				System.out.println("<등록되었습니다.>");
				System.out.println("");

				break;

			case 2:
				System.out.println("\n수정 ..............................................................");
				System.out.println("위치 : 홈 > 메뉴 > 수정");

				int menuId;

				System.out.print("1. 메뉴번호 : ");
				try {
					menuId = sc.nextInt();
					sc.nextLine();

				} catch (java.util.InputMismatchException e) {
					sc.nextLine();
					System.out.println("잘못된 입력입니다. 다시 입력해주세요.");

					continue;
				}

				System.out.print("2. 이 름 : ");
				String newName = sc.nextLine();

				int newPrice;

				System.out.print("3. 가 격 : ");
				try {
					newPrice = sc.nextInt();
					sc.nextLine();

				} catch (java.util.InputMismatchException e) {
					sc.nextLine();
					System.out.println("잘못된 입력입니다. 다시 입력해주세요.");

					continue;
				}

				menuDAO.updateMenu(menuId, newName, newPrice);
				System.out.println("<수정되었습니다.>");
				System.out.println("");

				break;

			case 3:
				System.out.println("\n삭제 ..............................................................");
				System.out.println("위치 : 홈 > 메뉴 > 삭제");

				int delId;

				System.out.print("1. 메뉴번호 : ");
				try {
					delId = sc.nextInt();
					sc.nextLine();

				} catch (java.util.InputMismatchException e) {
					sc.nextLine();
					System.out.println("잘못된 입력입니다. 다시 입력해주세요.");

					continue;
				}

				menuDAO.deleteMenu(delId);
				System.out.println("<삭제되었습니다.>");
				System.out.println("");
				break;

			default:
				System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
			}
		}
	}
}
