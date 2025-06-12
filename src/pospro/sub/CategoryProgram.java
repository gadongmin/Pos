package pospro.sub;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import pospro.dao.CategoryDAO;
import pospro.vo.CategoryVO;

public class CategoryProgram {

	// 필드

	// 생성자

	// 메소드
	public void categoryManagementMenu(Scanner sc) throws SQLException {

		CategoryDAO categoryDao = new CategoryDAO();

		while (true) {
			System.out.println("\n카테고리 -----------------------------------------------------------");
			System.out.println("      1. 등록                   2. 수정                   3. 삭제");
			System.out.println("--------------------------------------------------------------------");
			System.out.println("<카테고리>");

			List<CategoryVO> categories = categoryDao.getAllCategories();

			for (int i = 0; i < categories.size(); i++) {
				System.out.println(categories.get(i).getId() + " - 이모티콘(" + categories.get(i).getEmoji() + "), " + "이름("
						+ categories.get(i).getName() + "), 설명(" + categories.get(i).getDescription() + ")");
			}

			int sub;

			try {
				System.out.print("\n[카테고리 번호를 입력해주세요]        * 0번 상위메뉴\n카테고리 번호 : ");
				sub = sc.nextInt();

			} catch (InputMismatchException e) {
				System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
				sc.nextLine(); // 잘못된 입력 버리기

				continue; // 다시 입력받기
			}

			switch (sub) {
			case 1:
				System.out.println("\n등록 ..............................................................");
				System.out.println("위치 : 홈 > 카테고리 > 등록");
				sc.nextLine();
				System.out.print("1. 이모티콘 : ");
				String emoji = sc.nextLine();
				System.out.print("2. 이 름 : ");
				String name = sc.nextLine();
				System.out.print("3. 설 명 : ");
				String desc = sc.nextLine();
				categoryDao.categoryInsert(emoji, name, desc);
				System.out.println("<등록되었습니다.>");
				System.out.println("");

				break;

			case 2:
				System.out.println("\n수정 ..............................................................");
				System.out.println("위치 : 홈 > 카테고리 > 수정");

				int catId;

				while (true) {
					try {
						System.out.print("카테고리번호 : ");
						catId = sc.nextInt();
						sc.nextLine(); // 입력 버퍼 비우기

						break;

					} catch (InputMismatchException e) {
						System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
						sc.nextLine(); // 잘못된 입력 버리기
					}
				}

				System.out.print("1. 이모티콘 : ");
				String newEmoji = sc.nextLine();
				System.out.print("2. 이 름 : ");
				String newName = sc.nextLine();
				System.out.print("3. 설 명 : ");
				String newDesc = sc.nextLine();
				categoryDao.updateCategory(catId, newEmoji, newName, newDesc);
				System.out.println("<수정되었습니다.>");
				System.out.println("");
				break;

			case 3:
				System.out.println("\n삭제 ..............................................................");
				System.out.println("위치 : 홈 > 카테고리 > 삭제");

				int delId;

				while (true) {
					try {
						System.out.print("카테고리번호 : ");
						delId = sc.nextInt();
						sc.nextLine(); // 입력 버퍼 비우기

						break;

					} catch (InputMismatchException e) {
						System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
						sc.nextLine(); // 잘못된 입력 버리기
					}
				}

				categoryDao.deleteCategory(delId);
				System.out.println("<삭제되었습니다.>");
				System.out.println("");

				break;

			case 0:

				return;

			default:
				System.out.println("잘못된 입력입니다.");
			}
		}
	}

}
