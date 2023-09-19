package miljana.andric.dtos;

import java.util.Objects;

public class UserResponseDto implements Dto{
	
	private String firstname;
	private String lastname;
	private String username;
	private String email;
	
	public UserResponseDto(String firstname, String lastname, String email, String username) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.username = username;
		
	}
	public UserResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "UserResponseDto [firstname=" + firstname + ", lastname=" + lastname + ", username=" + username
				+ ", email=" + email + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(email, firstname, lastname, username);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserResponseDto other = (UserResponseDto) obj;
		return Objects.equals(email, other.email) && Objects.equals(firstname, other.firstname)
				&& Objects.equals(lastname, other.lastname) && Objects.equals(username, other.username);
	}
	

}
