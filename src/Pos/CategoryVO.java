package Pos;

//카테고리 정보 저장
public class CategoryVO {
	private int id;
	private String emoji;
	private String name;
	private String description;

	// 생성자
	public CategoryVO() {
	}

	public CategoryVO(int id, String emoji, String name, String description) {
		this.id = id;
		this.emoji = emoji;
		this.name = name;
		this.description = description;
	}

	// getter/setter
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmoji() {
		return emoji;
	}

	public void setEmoji(String emoji) {
		this.emoji = emoji;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}



