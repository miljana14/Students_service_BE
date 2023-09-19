package miljana.andric.dtos;

import java.util.Objects;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class UserRegisterDto implements Dto{
	
	@NotNull(message = "First name is required!")
	@Size(min = 3, message = " First name must have min 3 characters")
	private String firstName;
	@Size(min = 3, message = " Last name must have min 3 characters")
	@NotNull(message = "Last name is required!")
	private String lastName;
	@Email
	private String email;
	@NotNull(message = "Username is required!")
	private String username;
	@NotNull(message = "Password is required!")
	private String password;
	@NotNull(message = "Password is required!")
	private String repeatedPassword;
	
	
	public UserRegisterDto(
			@NotNull(message = "First name is required!") @Size(min = 3, message = " First name must have min 3 characters") String firstName,
			@Size(min = 3, message = " Last name must have min 3 characters") @NotNull(message = "Last name is required!") String lastName,
			@Email String email, @NotNull(message = "Username is required!") String username,
			@NotNull(message = "Password is required!") String password,
			@NotNull(message = "Password is required!") String repeatedPassword) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
		this.repeatedPassword = repeatedPassword;
	}
	public UserRegisterDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRepeatedPassword() {
		return repeatedPassword;
	}
	public void setRepeatedPassword(String repeatedPassword) {
		this.repeatedPassword = repeatedPassword;
	}
	@Override
	public String toString() {
		return "UserRegisterDto [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", username="
				+ username + ", password=" + password + ", repeatedPassword=" + repeatedPassword + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(email, firstName, lastName, password, repeatedPassword, username);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserRegisterDto other = (UserRegisterDto) obj;
		return Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(password, other.password)
				&& Objects.equals(repeatedPassword, other.repeatedPassword) && Objects.equals(username, other.username);
	}
	

}
