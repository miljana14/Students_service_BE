package miljana.andric.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity implements miljana.andric.entities.Entity{

	private static final long serialVersionUID = 3767931844454868041L;
	
	@Column(name = "firstname")
	private String firstName;
	@Column(name = "lastname")
	private String lastName;
	@Column(name = "email")
	private String email;
	@Id
	@Column(name = "username")
	private String username;
	private String password;
	@Enumerated(EnumType.STRING)
	private RoleEnum role;
	@Column(name = "new_user", nullable = false, columnDefinition = "boolean default true")
	private boolean newUser = true;

	public UserEntity() {
		super();
	}

	public UserEntity(String firstName, String lastName, String email, String username, String password,
			RoleEnum role) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public UserEntity(String firstName, String lastName, String email, String username) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
	}
	
	public UserEntity(String firstName, String lastName, String email, String username, String password, RoleEnum role,
			boolean newUser) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
		this.role = role;
		this.newUser = newUser;
	}



	public boolean isNewUser() {
		return newUser;
	}

	public void setNewUser(boolean newUser) {
		this.newUser = newUser;
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

	public RoleEnum getRole() {
		return role;
	}


	public void setRole(RoleEnum role) {
		this.role = role;
	}

	

	@Override
	public String toString() {
		return "UserEntity [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", username="
				+ username + ", password=" + password + ", role=" + role + ", newUser=" + newUser + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, firstName, lastName, newUser, password, role, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserEntity other = (UserEntity) obj;
		return Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(lastName, other.lastName) && newUser == other.newUser
				&& Objects.equals(password, other.password) && role == other.role
				&& Objects.equals(username, other.username);
	}

	
	 
}
