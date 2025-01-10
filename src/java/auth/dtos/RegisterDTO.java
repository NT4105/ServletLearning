package auth.dtos;

public class RegisterDTO {
	private String username;
	private String password;
	private int role;

	public RegisterDTO(String username, String password, int role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public int getRole() {
		return role;
	}
}